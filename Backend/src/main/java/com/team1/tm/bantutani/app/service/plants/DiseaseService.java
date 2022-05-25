package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsCareResponseDTO;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.plants.PlantTypeDisease;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.PlantsDisease;
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
public class DiseaseService extends PlantsCareService{
    private PlantTypeDiseaseRepo plantTypeDiseaseRepo;
    public DiseaseService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo, PlantsCareRepo plantsCareRepo,
                          PlantsPlantingRepo plantsPlantingRepo, StorageConfig storageConfig,
                          AnimationRepo animationRepo, PlantsDiseaseRepo plantsDiseaseRepo,
                          PlantsPestRepo plantsPestRepo, PlantsWeedsRepo plantsWeedsRepo,
                          PlantsRepo plantsRepo, PlantTypeDiseaseRepo plantTypeDiseaseRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo, plantsDiseaseRepo, plantsPestRepo, plantsWeedsRepo, plantsRepo);
        this.plantTypeDiseaseRepo = plantTypeDiseaseRepo;
    }

    @Cacheable(value = "plantsDiseaseImageCache", key = "#name")
    public byte[] getPlantsDiseaseImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.DISEASE);
    }

    @Cacheable(value = "plantsAllDiseaseCache", key = "#id")
    public List<PlantAttributeResponseMinDTO> getPlantsDisease(Long id, int page, int size) {
        return plantsDiseaseRepo.findAllByPlantsId(id, PageRequest.of(page, size)).stream().map(item -> convertPlantDiseaseToMinDTO(item)).
                collect(Collectors.toList());
    }
    
    @Cacheable(value = "plantsDiseaseByIdCache", key = "#id")
    public PlantAttributeResponseDTO getPlantDisease(Long id) {
        return plantsDiseaseRepo.findById(id).map(item -> convertPlantDiseaseToDTO(item)).get();
    }

    @Cacheable(value = "plantsTypeDiseaseCache")
    public List<String> getPlantsTypeDisease(String type, int size) {
        return plantTypeDiseaseRepo.findAllByTypeContaining(type, PageRequest.of(0, size)).map(item -> item.getType()).getContent();
    }

    public PlantAttributeResponseDTO convertPlantDiseaseToDTO(PlantsDisease plantsDisease) {
        return new PlantAttributeResponseDTO.Builder().id(plantsDisease.getId()).
                author(plantsDisease.getAuthorPlantsAttribute().getUsername()).
                description(plantsDisease.getDescription()).
                otherNames(plantsDisease.getOtherNames()).name(plantsDisease.getName()).
                type(plantsDisease.getPlantTypeDisease().getType()).
                image(plantsDisease.getImages()).
                care(getPlantsCare(plantsDisease.getPlantsCares())).build();
    }

    public PlantAttributeResponseMinDTO convertPlantDiseaseToMinDTO(PlantsDisease plantsDisease) {
        PlantAttributeResponseMinDTO responseMinDTO = new PlantAttributeResponseMinDTO.Builder().id(plantsDisease.getId()).
                name(plantsDisease.getName()).
                type(plantsDisease.getPlantTypeDisease().getType()).build();
        if(!plantsDisease.getImages().isEmpty())
            responseMinDTO.setImage(plantsDisease.getImages().get(0));
        return responseMinDTO;
    }

    private List<PlantsCareResponseDTO> getPlantsCare(List<PlantsCare> plantsCares) {
        return plantsCares.stream().map(item -> convertPlantsCareToDTO(item)).
                collect(Collectors.toList());
    }

    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    @Override
    public void updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        super.updatePlantsCare(plantsCareDTO);
    }
    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    @Override
    public void deletePlantsCare(Long id) {
        super.deletePlantsCare(id);
    }
    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    @Override
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        super.updateTipsNTrick(tipsNTrickDTO);
    }
    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    @Override
    public void deleteTipsNTrick(Long id) {
        super.deleteTipsNTrick(id);
    }

    @Transactional
    @Override
    @CacheEvict(value = "userDataCache", key = "#plantAttributeDTO.getAuthorPlantsAttribute", condition = "#plantAttributeDTO.getAuthorPlantsAttribute!=null")
    public void addDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantTypeDisease plantTypeDisease = null;
        if(plantTypeDiseaseRepo.existsByType(plantAttributeDTO.getAttributePlantsType()))
            plantTypeDisease = plantTypeDiseaseRepo.findByType(plantAttributeDTO.getAttributePlantsType()).get();
        else {
            PlantTypeDisease plantTypeDisease1 = new PlantTypeDisease();
            plantTypeDisease1.setType(plantAttributeDTO.getAttributePlantsType());
            plantTypeDisease = plantTypeDiseaseRepo.save(plantTypeDisease1);
        }
        User user = userRepo.findById(plantAttributeDTO.getAuthorPlantsAttribute()).get();
        Plants plants = plantsRepo.findById(plantAttributeDTO.getPlants()).get();
        PlantsDisease plantsDisease = new PlantsDisease.Builder().plantTypeDisease(plantTypeDisease).
                description(plantAttributeDTO.getDescription()).author(user).
                addOtherNames(plantAttributeDTO.getOtherNames()).name(plantAttributeDTO.getName()).build();
        plantTypeDisease.getPlantsDiseases().add(plantsDisease);
        user.getDiseases().add(plantsDisease);
        plants.addDisease(plantsDisease);
        if (plantAttributeDTO.getImages() != null){
            plantAttributeDTO.getImages().forEach(item -> {
                plantsDisease.getImages().add(storageConfig.addMedia(item,"diseaseImages", StorageConfig.SubDir.DISEASE));
            });
        }
        plantsDiseaseRepo.save(plantsDisease);
    }

    @Transactional
    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    public void addPlantsCare(PlantsCareDTO plantsCareDTO, Long id) {
        PlantsDisease plantsDisease = plantsDiseaseRepo.findById(id).get();
        PlantsCare plantsCare = super.addPlantsCare(plantsCareDTO);
        plantsDisease.getPlantsCares().add(plantsCare);
        plantsCare.setPlantsDiseaseCare(plantsDisease);
        plantsDiseaseRepo.save(plantsDisease);
    }

    @Transactional
    @CacheEvict(value = {"plantsDiseaseByIdCache"}, allEntries = true)
    public void addTipsNTrickCare(TipsNTrickDTO tipsNTrickDTO, Long idCare) {
        PlantsCare plantsCare = plantsCareRepo.findById(idCare).get();
        TipsNTrick tipsNTrick = super.addTipsNTrick(tipsNTrickDTO);
        plantsCare.getTipsNTricks().add(tipsNTrick);
        tipsNTrick.setPlantsCareTips(plantsCare);
        plantsCareRepo.save(plantsCare);
    }

    @Transactional
    @Override
    @CacheEvict(value = {"plantsAllDiseaseCache","plantsDiseaseByIdCache","userDataCache"}, allEntries = true)
    public void updateDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        PlantsDisease plantsDisease = plantsDiseaseRepo.findById(plantAttributeDTO.getId()).get();
        if (plantAttributeDTO.getAttributePlantsType() != null) {
            plantsDisease.getPlantTypeDisease().getPlantsDiseases().remove(plantsDisease);
            PlantTypeDisease plantTypeDisease = null;
            if(plantTypeDiseaseRepo.existsByType(plantAttributeDTO.getAttributePlantsType()))
                plantTypeDisease = plantTypeDiseaseRepo.findByType(plantAttributeDTO.getAttributePlantsType()).get();
            else {
                PlantTypeDisease plantTypeDisease1 = new PlantTypeDisease();
                plantTypeDisease1.setType(plantAttributeDTO.getAttributePlantsType());
                plantTypeDisease = plantTypeDiseaseRepo.save(plantTypeDisease1);
            }
            plantTypeDisease.getPlantsDiseases().add(plantsDisease);
            plantsDisease.setPlantTypeDisease(plantTypeDisease);
        }
        if (plantAttributeDTO.getDescription() != null)
            plantsDisease.setDescription(plantAttributeDTO.getDescription());
        if (plantAttributeDTO.getName() != null)
            plantsDisease.setName(plantAttributeDTO.getName());
        if (plantAttributeDTO.getOtherNames() != null)
            plantsDisease.setOtherNames(plantAttributeDTO.getOtherNames());
        plantsDiseaseRepo.save(plantsDisease);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllDiseaseCache"}, allEntries = true),
            @CacheEvict(value = {"plantsDiseaseByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete) {
        PlantsDisease plantsDisease = plantsDiseaseRepo.findById(id).get();
        if (delete) {
            deleteImage(filename, plantsDisease);
        } else {
            plantsDisease.getImages().add(storageConfig.addMedia(file,"diseaseImages", StorageConfig.SubDir.DISEASE));
        }
        plantsDiseaseRepo.save(plantsDisease);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = {"plantsAllDiseaseCache","userDataCache"}, allEntries = true),
            @CacheEvict(value = {"plantsDiseaseByIdCache"}, key = "#id", condition = "#id!=null")
    })
    public void deleteDataAttribute(Long id) {
        PlantsDisease plantsDisease = plantsDiseaseRepo.findById(id).get();
        plantsDisease.getImages().forEach(item -> {
            deleteImage(item, StorageConfig.SubDir.DISEASE, "failed delete disease, something wrong with file images, please try again");
        });
        plantsDisease.getPlantsCares().forEach(item -> {
            if (item.getImage() != null)
                deleteImage(item.getImage(), StorageConfig.SubDir.CARE, "failed delete plants care, something wrong with file images, please try again");
        });
        plantsDisease.getPlantsCares().clear();
        new LinkedHashSet<>(plantsDisease.getPlants()).forEach(item -> {
            item.removeDisease(plantsDisease);
        });
        PlantsDisease plantsDisease1 = plantsDiseaseRepo.save(plantsDisease);
        plantsDiseaseRepo.delete(plantsDisease1);
    }

    @CacheEvict(value = "plantsDiseaseImageCache", key = "#filename")
    private void deleteImage(String filename, PlantsDisease plantsDisease) {
        if (storageConfig.deleteMedia(filename, StorageConfig.SubDir.DISEASE))
            plantsDisease.getImages().remove(filename);
    }

    @CacheEvict(value = "plantsDiseaseImageCache", key = "#filename")
    private void deleteImage(String filename, StorageConfig.SubDir subDir, String error_message) {
        if (!storageConfig.deleteMedia(filename, subDir))
            throw new RuntimeException(error_message);
    }

    @Transactional
    @CacheEvict(value = "plantsTypeDiseaseCache", allEntries = true)
    public void deleteTypeDisease(String type) {
        plantTypeDiseaseRepo.deleteByType(type);
    }
}
