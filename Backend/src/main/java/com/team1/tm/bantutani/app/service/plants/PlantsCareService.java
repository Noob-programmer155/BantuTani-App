package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.PlantsPlantingDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsCareResponseDTO;
import com.team1.tm.bantutani.app.dto.response.TipsNTrickResponseDTO;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.dataAttr.CareType;
import com.team1.tm.bantutani.app.repository.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PlantsCareService extends TipsNTrickService {
    protected PlantsDiseaseRepo plantsDiseaseRepo;
    protected PlantsPestRepo plantsPestRepo;
    protected PlantsWeedsRepo plantsWeedsRepo;
    protected PlantsRepo plantsRepo;
    public PlantsCareService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo,
                             PlantsCareRepo plantsCareRepo, PlantsPlantingRepo plantsPlantingRepo,
                             StorageConfig storageConfig, AnimationRepo animationRepo,
                             PlantsDiseaseRepo plantsDiseaseRepo, PlantsPestRepo plantsPestRepo,
                             PlantsWeedsRepo plantsWeedsRepo, PlantsRepo plantsRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo);
        this.plantsDiseaseRepo = plantsDiseaseRepo;
        this.plantsPestRepo = plantsPestRepo;
        this.plantsWeedsRepo = plantsWeedsRepo;
        this.plantsRepo = plantsRepo;
    }

    public abstract void addDataAttribute(PlantAttributeDTO plantAttributeDTO);
    public abstract String updateDataAttribute(PlantAttributeDTO plantAttributeDTO);
    public abstract String updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete);
    public abstract String deleteDataAttribute(Long id);

    public TipsNTrickResponseDTO convertTipsNTrickToDTO(TipsNTrick item) {
        return new TipsNTrickResponseDTO.Builder().id(item.getId()).
                        activity(item.getTypeActivity().getLabel()).
                        author(item.getAuthorTipsNTrick().getUsername()).animation(item.getAnimation()).
                        step(item.getTypeStep().getLabel()).
                        description(item.getDescription()).name(item.getName()).build();
    }

    public PlantsCareResponseDTO convertPlantsCareToDTO(PlantsCare item) {
        return new PlantsCareResponseDTO.Builder().careType(item.getCareType().getLabel()).
                animation(item.getAnimation()).author(item.getAuthorPlantsCare().getUsername()).
                description(item.getDescription()).id(item.getId()).image(item.getImage()).
                video(item.getVideo()).step(item.getStep()).
                listTipsNTrick(item.getTipsNTricks().stream().map(it -> convertTipsNTrickToDTO(it)).
                        collect(Collectors.toList())).build();
    }

    @Cacheable(value = "plantsCareImageCache", key = "#name")
    public byte[] getPlantsCareImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.CARE);
    }

    @Transactional
    public PlantsCare addPlantsCare(PlantsCareDTO plantsCareDTO) {
        User user = userRepo.findById(plantsCareDTO.getAuthorPlantsCare()).get();
        PlantsCare plantsCare = new PlantsCare.Builder().careType(CareType.getFromLabel(plantsCareDTO.getCareType())).
                author(user).step(plantsCareDTO.getStep()).
                description(plantsCareDTO.getDescription()).build();
        user.getCare().add(plantsCare);
        if (plantsCareDTO.getAnimation() != null)
            plantsCare.setAnimation(plantsCareDTO.getAnimation());
        if (plantsCareDTO.getImage() != null)
            plantsCare.setImage(storageConfig.addMedia(plantsCareDTO.getImage(),"plantsCareImages", StorageConfig.SubDir.CARE));
        if (plantsCareDTO.getVideo() != null)
            plantsCare.setVideo(plantsCareDTO.getVideo());
        if (plantsCareDTO.getPlants() != null) {
            Plants plants = plantsRepo.findById(plantsCareDTO.getPlants()).get();
            plants.getCares().add(plantsCare);
            plantsCare.setCaringPlants(plants);
        }
        return plantsCareRepo.save(plantsCare);
    }

    @Transactional
    @CacheEvict(value = "plantsCareImageCache", key = "#plantsCareDTO.getImage")
    public String updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        PlantsCare plantsCare = plantsCareRepo.findById(plantsCareDTO.getId()).get();
        if (plantsCareDTO.getStep() != null)
            plantsCare.setStep(plantsCareDTO.getStep());
        if (plantsCareDTO.getCareType() != null)
            plantsCare.setCareType(CareType.getFromLabel(plantsCareDTO.getCareType()));
        if (plantsCareDTO.getDescription() != null)
            plantsCare.setDescription(plantsCareDTO.getDescription());
        if (plantsCareDTO.getAnimation() != null) {
            plantsCare.setAnimation(plantsCareDTO.getAnimation());
            plantsCare.setVideo(null);
        }
        if (plantsCareDTO.getImage() != null) {
            if (plantsCare.getImage() != null)
                storageConfig.updateMedia(plantsCareDTO.getImage(), plantsCare.getImage(), StorageConfig.SubDir.CARE);
            else {
                plantsCare.setAnimation(null);
                plantsCare.setImage(storageConfig.addMedia(plantsCareDTO.getImage(),"plantsCareImages", StorageConfig.SubDir.CARE));
                plantsCare.setVideo(null);
            }
        }
        if (plantsCareDTO.getVideo() != null) {
            plantsCare.setVideo(plantsCareDTO.getVideo());
            plantsCare.setAnimation(null);
        }
        if (plantsCare.getCaringPlants() != null)
            plantsCare.getCaringPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsDiseaseCare() != null)
            plantsCare.getPlantsDiseaseCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsWeedsCare() != null)
            plantsCare.getPlantsWeedsCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsPestCare() != null)
            plantsCare.getPlantsPestCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        PlantsCare plantsCare1 = plantsCareRepo.save(plantsCare);
        return (plantsCare.getCaringPlants() != null)?plantsCare1.getCaringPlants().getName():
                (plantsCare.getPlantsDiseaseCare() != null)?String.valueOf(plantsCare1.getPlantsDiseaseCare().getId()):
                        (plantsCare.getPlantsPestCare() != null)?String.valueOf(plantsCare1.getPlantsPestCare().getId()):
                                String.valueOf(plantsCare1.getPlantsWeedsCare().getId());
    }

    @Transactional
    @CacheEvict(value = {"plantsCareImageCache"}, allEntries = true)
    public void deletePlantsCare(Long id) {
        PlantsCare plantsCare = plantsCareRepo.findById(id).get();
        if (plantsCare.getImage() != null)
            if(!storageConfig.deleteMedia(plantsCare.getImage(), StorageConfig.SubDir.CARE))
                throw new RuntimeException("failed delete plants care, something wrong with file images, please try again");
        plantsCare.getTipsNTricks().clear();
        if (plantsCare.getCaringPlants() != null)
            plantsCare.getCaringPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsDiseaseCare() != null)
            plantsCare.getPlantsDiseaseCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsWeedsCare() != null)
            plantsCare.getPlantsWeedsCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        if (plantsCare.getPlantsPestCare() != null)
            plantsCare.getPlantsPestCare().setDateUpdate(new Date(new java.util.Date().getTime()));
        PlantsCare plantsCare1 = plantsCareRepo.save(plantsCare);
        plantsCareRepo.delete(plantsCare1);
    }
}