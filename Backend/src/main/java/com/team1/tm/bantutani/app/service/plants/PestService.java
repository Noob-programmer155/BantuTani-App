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
import com.team1.tm.bantutani.app.model.plants.PlantTypePest;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.PlantsPest;
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
public class PestService extends PlantsCareService{

    private PlantTypePestRepo plantTypePestRepo;
    public PestService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo, PlantsCareRepo plantsCareRepo,
                       PlantsPlantingRepo plantsPlantingRepo, StorageConfig storageConfig,
                       AnimationRepo animationRepo, PlantsDiseaseRepo plantsDiseaseRepo,
                       PlantsWeedsRepo plantsWeedsRepo, PlantsPestRepo plantsPestRepo,
                       PlantsRepo plantsRepo, PlantTypePestRepo plantTypePestRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo, plantsDiseaseRepo, plantsPestRepo, plantsWeedsRepo, plantsRepo);
        this.plantTypePestRepo = plantTypePestRepo;
    }

    @Cacheable(value = "plantsPestImageCache", key = "#name")
    public byte[] getPlantsPestImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.PEST);
    }

    @Cacheable(value = "plantsAllPestCache")
    public List<PlantAttributeResponseMinDTO> getPlantsPest(Long id, int page, int size) {
        return plantsPestRepo.findAllByPlantsId(id, PageRequest.of(page, size)).map(item -> convertPlantPestToMinDTO(item)).
                getContent();
    }

    @Cacheable(value = "plantsPestByIdCache", key = "#id")
    public PlantAttributeResponseDTO getPlantPest(Long id) {
        return plantsPestRepo.findById(id).map(item -> convertPlantPestToDTO(item)).get();
    }

    public PlantAttributeResponseDTO convertPlantPestToDTO(PlantsPest plantsPest) {
        return new PlantAttributeResponseDTO.Builder().id(plantsPest.getId()).
                author(plantsPest.getAuthorPlantsPest().getUsername()).
                description(plantsPest.getDescription()).
                otherNames(plantsPest.getOtherNames()).name(plantsPest.getName()).
                type(plantsPest.getPlantTypePest().getType()).
                image(plantsPest.getImages()).
                care(getPlantsCare(plantsPest.getPlantsCares())).build();
    }

    public PlantAttributeResponseMinDTO convertPlantPestToMinDTO(PlantsPest plantsPest) {
        return new PlantAttributeResponseMinDTO.Builder().id(plantsPest.getId()).
                name(plantsPest.getName()).
                type(plantsPest.getPlantTypePest().getType()).
                image(plantsPest.getImages().get(0)).build();
    }

    public List<PlantsCareResponseDTO> getPlantsCare(List<PlantsCare> plantsCares) {
        return plantsCares.stream().map(item -> convertPlantsCareToDTO(item)).
                collect(Collectors.toList());
    }
    @CacheEvict(value = {"plantsPestByIdCache"}, allEntries = true)
    @Override
    public void updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        super.updatePlantsCare(plantsCareDTO);
    }
    @CacheEvict(value = {"plantsPestByIdCache"}, allEntries = true)
    @Override
    public void deletePlantsCare(Long id) {
        super.deletePlantsCare(id);
    }

    @CacheEvict(value = {"plantsPestByIdCache"}, allEntries = true)
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        super.updateTipsNTrick(tipsNTrickDTO);
    }
    @CacheEvict(value = {"plantsPestByIdCache"}, allEntries = true)
    public void deleteTipsNTrick(Long id) {
        super.deleteTipsNTrick(id);
    }

    @Transactional
    @Override
    @CacheEvict(value = "userDataCache", key = "#plantAttributeDTO.getAuthorPlantsAttribute", condition = "#plantAttributeDTO.getAuthorPlantsAttribute!=null")
    public void addDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantTypePest plantTypePest = plantTypePestRepo.findById(plantAttributeDTO.getAttributePlantsType()).get();
        User user = userRepo.findById(plantAttributeDTO.getAuthorPlantsAttribute()).get();
        Plants plants = plantsRepo.findById(plantAttributeDTO.getPlants()).get();
        PlantsPest plantsPest = new PlantsPest.Builder().plantTypePest(plantTypePest).
                description(plantAttributeDTO.getDescription()).author(user).
                addOtherNames(plantAttributeDTO.getOtherNames()).name(plantAttributeDTO.getName()).build();
        plantTypePest.getPlantsPests().add(plantsPest);
        user.getPests().add(plantsPest);
        plants.addPest(plantsPest);
        if (plantAttributeDTO.getImages() != null){
            plantAttributeDTO.getImages().forEach(item -> {
                plantsPest.getImages().add(storageConfig.addMedia(item,"pestImages", StorageConfig.SubDir.PEST));
            });
        }
        plantsPestRepo.save(plantsPest);
    }

    @Transactional
    @CacheEvict(value = {"plantsPestByIdCache"}, allEntries = true)
    public void addPlantsCare(PlantsCareDTO plantsCareDTO, Long id) {
        PlantsPest plantsPest = plantsPestRepo.findById(id).get();
        PlantsCare plantsCare = super.addPlantsCare(plantsCareDTO);
        plantsPest.getPlantsCares().add(plantsCare);
        plantsCare.setPlantsPestCare(plantsPest);
        plantsPestRepo.save(plantsPest);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"plantsAllPestCache","plantsPestByIdCache","userDataCache"}, allEntries = true)
    public void updateDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantsPest plantsPest = plantsPestRepo.findById(plantAttributeDTO.getId()).get();
        if (plantAttributeDTO.getAttributePlantsType() != null) {
            plantsPest.getPlantTypePest().getPlantsPests().remove(plantsPest);
            PlantTypePest plantTypePest = plantTypePestRepo.findById(plantAttributeDTO.getAttributePlantsType()).get();
            plantTypePest.getPlantsPests().add(plantsPest);
            plantsPest.setPlantTypePest(plantTypePest);
        }
        if (plantAttributeDTO.getDescription() != null)
            plantsPest.setDescription(plantAttributeDTO.getDescription());
        if (plantAttributeDTO.getName() != null)
            plantsPest.setName(plantAttributeDTO.getName());
        if (plantAttributeDTO.getOtherNames() != null)
            plantsPest.setOtherNames(plantAttributeDTO.getOtherNames());
        plantsPestRepo.save(plantsPest);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllPestCache"}, allEntries = true),
            @CacheEvict(value = {"plantsPestByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete) {
        PlantsPest plantsPest = plantsPestRepo.findById(id).get();
        if (delete) {
            deleteImage(filename, plantsPest);
        } else {
            plantsPest.getImages().add(storageConfig.addMedia(file,"pestImages", StorageConfig.SubDir.PEST));
        }
        plantsPestRepo.save(plantsPest);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllPestCache","userDataCache"}, allEntries = true),
            @CacheEvict(value = {"plantsPestByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void deleteDataAttribute(Long id) {
        PlantsPest plantsPest = plantsPestRepo.findById(id).get();
        plantsPest.getImages().forEach(item -> {
            deleteImage(item, StorageConfig.SubDir.PEST, "failed delete pest, something wrong with file images, please try again");
        });
        plantsPest.getPlantsCares().forEach(item -> {
            if (item.getImage() != null)
                deleteImage(item.getImage(), StorageConfig.SubDir.CARE, "failed delete plants care, something wrong with file images, please try again");
        });
        plantsPest.getPlantsCares().clear();
        new LinkedHashSet<>(plantsPest.getPlants()).forEach(item -> {
            item.removePest(plantsPest);
        });
        PlantsPest plantsPest1 = plantsPestRepo.save(plantsPest);
        plantsPestRepo.delete(plantsPest1);
    }

    @CacheEvict(value = "plantsPestImageCache", key = "#filename")
    private void deleteImage(String filename, PlantsPest plantsPest) {
        if (storageConfig.deleteMedia(filename, StorageConfig.SubDir.PEST))
            plantsPest.getImages().remove(filename);
    }

    @CacheEvict(value = "plantsPestImageCache", key = "#filename")
    private void deleteImage(String filename, StorageConfig.SubDir subDir, String error_message) {
        if (!storageConfig.deleteMedia(filename, subDir))
            throw new RuntimeException(error_message);
    }
}
