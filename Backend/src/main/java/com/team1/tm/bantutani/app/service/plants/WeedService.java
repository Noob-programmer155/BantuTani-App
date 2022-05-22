package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsCareResponseDTO;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.plants.*;
import com.team1.tm.bantutani.app.repository.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeedService extends PlantsCareService{
    private PlantTypeWeedRepo plantTypeWeedRepo;
    public WeedService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo, PlantsCareRepo plantsCareRepo,
                       PlantsPlantingRepo plantsPlantingRepo, StorageConfig storageConfig,
                       AnimationRepo animationRepo, PlantsDiseaseRepo plantsDiseaseRepo,
                       PlantsPestRepo plantsPestRepo, PlantsWeedsRepo plantsWeedsRepo,
                       PlantsRepo plantsRepo, PlantTypeWeedRepo plantTypeWeedRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo, plantsDiseaseRepo, plantsPestRepo, plantsWeedsRepo, plantsRepo);
        this.plantTypeWeedRepo = plantTypeWeedRepo;
    }

    @Cacheable(value = "plantsWeedImageCache", key = "#name")
    public byte[] getPlantsWeedImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.WEEDS);
    }

    @Cacheable(value = "plantsAllWeedCache")
    public List<PlantAttributeResponseMinDTO> getPlantsWeed(Long id, int page, int size) {
        return plantsWeedsRepo.findAllByPlantsId(id, PageRequest.of(page, size)).map(item -> convertPlantWeedToMinDTO(item)).
                getContent();
    }

    @Cacheable(value = "plantsWeedByIdCache", key = "#id")
    public PlantAttributeResponseDTO getPlantWeed(Long id) {
        return plantsWeedsRepo.findById(id).map(item -> convertPlantWeedToDTO(item)).get();
    }

    public PlantAttributeResponseDTO convertPlantWeedToDTO(PlantsWeeds plantsWeeds) {
        return new PlantAttributeResponseDTO.Builder().id(plantsWeeds.getId()).
                author(plantsWeeds.getAuthorPlantsWeeds().getUsername()).
                description(plantsWeeds.getDescription()).
                otherNames(plantsWeeds.getOtherNames()).name(plantsWeeds.getName()).
                type(plantsWeeds.getPlantTypeWeed().getType()).
                image(plantsWeeds.getImages()).
                care(getPlantsCare(plantsWeeds.getPlantsCares())).build();
    }

    public PlantAttributeResponseMinDTO convertPlantWeedToMinDTO(PlantsWeeds plantsPest) {
        return new PlantAttributeResponseMinDTO.Builder().id(plantsPest.getId()).
                name(plantsPest.getName()).
                type(plantsPest.getPlantTypeWeed().getType()).
                image(plantsPest.getImages().get(0)).build();
    }

    public List<PlantsCareResponseDTO> getPlantsCare(List<PlantsCare> plantsCare) {
        return plantsCare.stream().map(item -> convertPlantsCareToDTO(item)).
                collect(Collectors.toList());
    }
    @CacheEvict(value = {"plantsWeedByIdCache"}, allEntries = true)
    @Override
    public void updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        super.updatePlantsCare(plantsCareDTO);
    }

    @CacheEvict(value = {"plantsWeedByIdCache"}, allEntries = true)
    @Override
    public void deletePlantsCare(Long id) {
        super.deletePlantsCare(id);
    }

    @CacheEvict(value = {"plantsWeedByIdCache"}, allEntries = true)
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        super.updateTipsNTrick(tipsNTrickDTO);
    }
    @CacheEvict(value = {"plantsWeedByIdCache"}, allEntries = true)
    public void deleteTipsNTrick(Long id) {
        super.deleteTipsNTrick(id);
    }

    @Transactional
    @Override
    @CacheEvict(value = "userDataCache", key = "#plantAttributeDTO.getAuthorPlantsAttribute", condition = "#plantAttributeDTO.getAuthorPlantsAttribute!=null")
    public void addDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantTypeWeed plantTypeWeed = plantTypeWeedRepo.findById(plantAttributeDTO.getAttributePlantsType()).get();
        User user = userRepo.findById(plantAttributeDTO.getAuthorPlantsAttribute()).get();
        Plants plants = plantsRepo.findById(plantAttributeDTO.getPlants()).get();
        PlantsWeeds plantsWeeds = new PlantsWeeds.Builder().plantTypeWeed(plantTypeWeed).
                description(plantAttributeDTO.getDescription()).author(user).
                addOtherNames(plantAttributeDTO.getOtherNames()).name(plantAttributeDTO.getName()).build();
        plantTypeWeed.getPlantsWeeds().add(plantsWeeds);
        user.getWeeds().add(plantsWeeds);
        plants.addWeeds(plantsWeeds);
        if (plantAttributeDTO.getImages() != null){
            plantAttributeDTO.getImages().forEach(item -> {
                plantsWeeds.getImages().add(storageConfig.addMedia(item,"weedImages", StorageConfig.SubDir.WEEDS));
            });
        }
        plantsWeedsRepo.save(plantsWeeds);
    }

    @Transactional
    @CacheEvict(value = {"plantsWeedByIdCache"}, allEntries = true)
    public void addPlantsCare(PlantsCareDTO plantsCareDTO, Long id) {
        PlantsWeeds plantsWeeds = plantsWeedsRepo.findById(id).get();
        PlantsCare plantsCare = super.addPlantsCare(plantsCareDTO);
        plantsWeeds.getPlantsCares().add(plantsCare);
        plantsCare.setPlantsWeedsCare(plantsWeeds);
        plantsWeedsRepo.save(plantsWeeds);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"plantsAllWeedCache","plantsWeedByIdCache","userDataCache"}, allEntries = true)
    public void updateDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantsWeeds plantsWeeds = plantsWeedsRepo.findById(plantAttributeDTO.getId()).get();
        if (plantAttributeDTO.getAttributePlantsType() != null) {
            plantsWeeds.getPlantTypeWeed().getPlantsWeeds().remove(plantsWeeds);
            PlantTypeWeed plantTypeWeed = plantTypeWeedRepo.findById(plantAttributeDTO.getAttributePlantsType()).get();
            plantTypeWeed.getPlantsWeeds().add(plantsWeeds);
            plantsWeeds.setPlantTypeWeed(plantTypeWeed);
        }
        if (plantAttributeDTO.getDescription() != null)
            plantsWeeds.setDescription(plantAttributeDTO.getDescription());
        if (plantAttributeDTO.getName() != null)
            plantsWeeds.setName(plantAttributeDTO.getName());
        if (plantAttributeDTO.getOtherNames() != null)
            plantsWeeds.setOtherNames(plantAttributeDTO.getOtherNames());
        plantsWeedsRepo.save(plantsWeeds);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllWeedCache"}, allEntries = true),
            @CacheEvict(value = {"plantsWeedByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete) {
        PlantsWeeds plantsWeeds = plantsWeedsRepo.findById(id).get();
        if (delete) {
            deleteImage(filename, plantsWeeds);
        } else {
            plantsWeeds.getImages().add(storageConfig.addMedia(file,"weedImages", StorageConfig.SubDir.WEEDS));
        }
        plantsWeedsRepo.save(plantsWeeds);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllWeedCache","userDataCache"}, allEntries = true),
            @CacheEvict(value = {"plantsWeedByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void deleteDataAttribute(Long id) {
        PlantsWeeds plantsWeeds = plantsWeedsRepo.findById(id).get();
        plantsWeeds.getImages().forEach(item -> {
            deleteImage(item, StorageConfig.SubDir.WEEDS, "failed delete weed, something wrong with file images, please try again");
        });
        plantsWeeds.getPlantsCares().forEach(item -> {
            if (item.getImage() != null)
                deleteImage(item.getImage(), StorageConfig.SubDir.CARE, "failed delete plants care, something wrong with file images, please try again");
        });
        plantsWeeds.getPlantsCares().clear();
        new LinkedHashSet<>(plantsWeeds.getPlants()).forEach(item -> {
            item.removeWeeds(plantsWeeds);
        });
        PlantsWeeds plantsWeeds1 = plantsWeedsRepo.save(plantsWeeds);
        plantsWeedsRepo.delete(plantsWeeds1);
    }

    @CacheEvict(value = "plantsWeedImageCache", key = "#filename")
    private void deleteImage(String filename, PlantsWeeds plantsWeeds) {
        if (storageConfig.deleteMedia(filename, StorageConfig.SubDir.WEEDS))
            plantsWeeds.getImages().remove(filename);
    }

    @CacheEvict(value = "plantsWeedImageCache", key = "#filename")
    private void deleteImage(String filename, StorageConfig.SubDir subDir, String error_message) {
        if (!storageConfig.deleteMedia(filename, subDir))
            throw new RuntimeException(error_message);
    }
}
