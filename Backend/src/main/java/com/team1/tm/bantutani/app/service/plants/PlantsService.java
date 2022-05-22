package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.*;
import com.team1.tm.bantutani.app.dto.response.*;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.plants.*;
import com.team1.tm.bantutani.app.repository.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantsService extends PlantsCareService{
    private PlantTypeImplRepo plantTypeImplRepo;
    private CostPlantsRepo costPlantsRepo;

    public PlantsService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo,
                         PlantsCareRepo plantsCareRepo, PlantsPlantingRepo plantsPlantingRepo,
                         StorageConfig storageConfig, AnimationRepo animationRepo,
                         PlantsDiseaseRepo plantsDiseaseRepo, PlantsPestRepo plantsPestRepo,
                         PlantsWeedsRepo plantsWeedsRepo, PlantsRepo plantsRepo,
                         PlantTypeImplRepo plantTypeImplRepo, CostPlantsRepo costPlantsRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo, plantsDiseaseRepo, plantsPestRepo, plantsWeedsRepo, plantsRepo);
        this.plantTypeImplRepo = plantTypeImplRepo;
        this.costPlantsRepo = costPlantsRepo;
    }

    @Cacheable(value = "plantsImageCache", key = "#name")
    public byte[] getPlantsImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.PLANTS);
    }

    @Cacheable(value = "plantingPlantsImageCache", key = "#name")
    public byte[] getPlantingPlantsImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.PLANTING);
    }

    public PlantsResponseDTO getDataPlants(Long id) {
        PlantsResponseDTO plants = getPlants(id);
        return bindPlantsWithCost(plants, getCostPlants(plants.getId()));
    }

    @Cacheable(value = "plantsAllCache", key = "{#page,#size}")
    public List<PlantsResponseMinDTO> getAllDataPlants(int page, int size) {
        Page<PlantsResponseMin> plants = plantsRepo.findAllPlants(PageRequest.of(page,size,Sort.by(Sort.Direction.ASC, "name")));
        return plants.map(item -> convertPlantsToMinDTO(item)).getContent();
    }

    @Cacheable(value = "plantsCache", key = "#id")
    private PlantsResponseDTO getPlants(Long id) {
        PlantsResponse plants = plantsRepo.findPlantsById(id);
        return convertPlantsToDTO(plants);
    }

    @Cacheable(value = "costPlantsCache", key = "#id")
    private PlantsResponseDTO getCostPlants(Long id){
        return convertCostPlantsToDTO(costPlantsRepo.findByPlantsId(id).get());
    }

    public PlantsResponseDTO bindPlantsWithCost(PlantsResponseDTO plants, PlantsResponseDTO cost) {
        plants.setDateUpdateCost(cost.getDateUpdateCost());
        plants.setMaxCost(cost.getMaxCost());
        plants.setMinCost(cost.getMinCost());
        plants.setRegionCost(cost.getRegionCost());
        plants.setStableCost(cost.getStableCost());
        return plants;
    }

    public PlantsResponseDTO convertPlantsToDTO(PlantsResponse plants) {
        return new PlantsResponseDTO.Builder().planting(getPlantsPlanting(plants.getPlanting())).
                id(plants.getId()).cares(getPlantsCare(plants.getCares())).name(plants.getName()).
                otherNames(plants.getOtherNames()).characteristic(plants.getCharacteristic()).image(plants.getImage()).
                type(plants.getPlantTypeImpl().getType()).shortDescription(plants.getShortDescription()).
                build();
    }

    public PlantsResponseMinDTO convertPlantsToMinDTO(PlantsResponseMin plantsResponseMin) {
        return new PlantsResponseMinDTO.Builder().id(plantsResponseMin.getId()).name(plantsResponseMin.getName()).
                type(plantsResponseMin.getPlantTypeImpl().getType()).image(plantsResponseMin.getImage().get(0)).build();
    }

    public PlantsResponseDTO convertCostPlantsToDTO(CostPlant costPlant) {
        return new PlantsResponseDTO.Builder().date(costPlant.getDateUpdateCost()).maxCost(costPlant.getMaxCost()).
                minCost(costPlant.getMinCost()).regionCost(costPlant.getRegionCost()).
                stableCost(costPlant.getStableCost()).build();
    }

    public List<PlantsCareResponseDTO> getPlantsCare(List<PlantsCare> plantsCares) {
        return plantsCares.stream().map(item -> convertPlantsCareToDTO(item)).
                collect(Collectors.toList());
    }

    public List<PlantsPlantingResponseDTO> getPlantsPlanting(List<PlantsPlanting> plantsPlantings) {
        return plantsPlantings.stream().map(item -> {
            return new PlantsPlantingResponseDTO.Builder().id(item.getId()).animation(item.getAnimation()).
                    author(item.getAuthorPlantsPlanting().getUsername()).video(item.getVideo()).
                    image(item.getImage()).description(item.getDescription()).
                    tipsNTricks(getTipsNTrick(item.getTipsNTricks())).build();
        }).collect(Collectors.toList());
    }

    public List<TipsNTrickResponseDTO> getTipsNTrick(List<TipsNTrick> tipsNTricks) {
        return tipsNTricks.stream().map(item -> {
            return super.convertTipsNTrickToDTO(item);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void addPlants(PlantsDTO plantsDTO) {
        PlantTypeImpl plantType = plantTypeImplRepo.findById(plantsDTO.getPlantTypeImpl()).get();
        Plants plants = new Plants.Builder().characteristic(plantsDTO.getCharacteristic()).
                name(plantsDTO.getName()).otherNames(plantsDTO.getOtherNames()).
                plantType(plantType).shortDescription(plantsDTO.getShortDescription()).build();
        plantType.getPlants().add(plants);

        CostPlant costPlant = new CostPlant.Builder().dateUpdateCost(plantsDTO.getDateUpdateCost()).
                maxCost(plantsDTO.getMaxCost()).minCost(plantsDTO.getMinCost()).
                stableCost(plantsDTO.getStableCost()).regionCost(plantsDTO.getRegionCost()).build();
        CostPlant costPlant1 = costPlantsRepo.save(costPlant);
        plants.setPlantsCost(costPlant1);

        if (plantsDTO.getImage() != null) {
            plantsDTO.getImage().forEach(item -> {
                plants.getImage().add(storageConfig.addMedia(item, "plantImages", StorageConfig.SubDir.PLANTS));
            });
        }
        plantsRepo.save(plants);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"userDataCache"}, key = "#plantsCareDTO.getAuthorPlantsCare", condition = "#plantsCareDTO.getAuthorPlantsCare!=null")
    })
    public void addPlantsCare(PlantsCareDTO plantsCareDTO, Long id) {
        Plants plants = plantsRepo.findById(id).get();
        PlantsCare plantsCare = super.addPlantsCare(plantsCareDTO);
        plants.getCares().add(plantsCare);
        plantsCare.setCaringPlants(plants);
        plantsRepo.save(plants);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#plantsPlantingDTO.getPlantingPlants", condition = "#plantsPlantingDTO.getPlantingPlants!=null"),
            @CacheEvict(value = {"userDataCache"}, key = "#plantsPlantingDTO.getAuthorPlantsPlanting", condition = "#plantsPlantingDTO.getAuthorPlantsPlanting!=null")
    })
    public void addPlantsPlanting(PlantsPlantingDTO plantsPlantingDTO) {
        Plants plants = plantsRepo.findById(plantsPlantingDTO.getPlantingPlants()).get();
        User user = userRepo.findById(plantsPlantingDTO.getAuthorPlantsPlanting()).get();
        PlantsPlanting planting  = new PlantsPlanting.Builder().description(plantsPlantingDTO.getDescription()).
                plants(plants).author(user).build();
        user.getPlantings().add(planting);
        if (plantsPlantingDTO.getAnimation() != null)
            planting.setAnimation(plantsPlantingDTO.getAnimation());
        if (plantsPlantingDTO.getVideo() != null)
            planting.setVideo(plantsPlantingDTO.getVideo());
        if (plantsPlantingDTO.getImage() != null)
            planting.setImage(storageConfig.addMedia(plantsPlantingDTO.getImage(), "plantingImages", StorageConfig.SubDir.PLANTING));
        if (plantsPlantingDTO.getTipsNTricks() != null) {
            plantsPlantingDTO.getTipsNTricks().forEach(item -> {
                TipsNTrick tipsNTrick = super.addTipsNTrick(item);
                planting.getTipsNTricks().add(tipsNTrick);
                tipsNTrick.setPlantsPlantingTips(planting);
            });
        }
        plantsPlantingRepo.save(planting);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#plantsDTO.getId", condition = "#plantsDTO.getId!=null"),
            @CacheEvict(value = {"plantsAllCache"}, allEntries = true),
            @CacheEvict(value = "userDataCache", condition = "#plantsDTO.getName!=null")
    })
    public void updatePlants(PlantsDTO plantsDTO) {
        Plants plants = plantsRepo.findById(plantsDTO.getId()).get();
        if (plantsDTO.getCharacteristic() != null)
            plants.setCharacteristic(plantsDTO.getCharacteristic());
        if (plantsDTO.getName() != null)
            plants.setName(plantsDTO.getName());
        if (plantsDTO.getOtherNames() != null)
            plants.setOtherNames(plantsDTO.getOtherNames());
        if (plantsDTO.getPlantTypeImpl() != null) {
            plants.getPlantTypeImpl().getPlants().remove(plants);
            PlantTypeImpl plantType = plantTypeImplRepo.findById(plantsDTO.getPlantTypeImpl()).get();
            plantType.getPlants().add(plants);
            plants.setPlantTypeImpl(plantType);
        }
        if (plantsDTO.getShortDescription() != null)
            plants.setShortDescription(plantsDTO.getShortDescription());
        plantsRepo.save(plants);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"plantsAllCache"}, allEntries = true)
    })
    public void updateImage(MultipartFile file, String filename, Long id, boolean delete) {
        Plants plants = plantsRepo.findById(id).get();
        if (delete) {
            deleteImage(filename, plants);
        } else {
            plants.getImage().add(storageConfig.addMedia(file, "plantImages", StorageConfig.SubDir.PLANTS));
        }
        plantsRepo.save(plants);
    }

    @CacheEvict(value = "plantsImageCache", key = "#filename")
    private void deleteImage(String filename, Plants plants) {
        if (storageConfig.deleteMedia(filename, StorageConfig.SubDir.PLANTS))
            plants.getImage().remove(filename);
    }

    // id pake plants id
    @Transactional
    @CacheEvict(value = {"costPlantsCache"}, key = "#id", condition = "#id!=null")
    public void updateCost(Long id, int regionCost, int stableCost, int maxCost, int minCost, Date dateUpdate) {
        CostPlant costPlant = costPlantsRepo.findByPlantsId(id).get();
        costPlant.setDateUpdateCost(dateUpdate);
        costPlant.setMaxCost(maxCost);
        costPlant.setMinCost(minCost);
        costPlant.setRegionCost(regionCost);
        costPlant.setStableCost(stableCost);
        costPlantsRepo.save(costPlant);
    }

    // id pake plants id
    @Transactional
    @CacheEvict(value = {"costPlantsCache"}, key = "#id", condition = "#id!=null")
    public void updateCost(Long id, int regionCost, int stableCost, int maxCost, int minCost) {
        CostPlant costPlant = costPlantsRepo.findByPlantsId(id).get();
        costPlant.setDateUpdateCost(new Date(new java.util.Date().getTime()));
        costPlant.setMaxCost(maxCost);
        costPlant.setMinCost(minCost);
        costPlant.setRegionCost(regionCost);
        costPlant.setStableCost(stableCost);
        costPlantsRepo.save(costPlant);
    }

    @Transactional
    @Scheduled(fixedRate = 86400000)
    @CacheEvict(value = {"costPlantsCache"})
    public void updateAllCostAutomatic() {
        // using scrapper
    }

    @Override
    public void addDataAttribute(PlantAttributeDTO plantAttributeDTO) {

    }

    @Override
    public void updateDataAttribute(PlantAttributeDTO plantAttributeDTO) {

    }

    @Override
    public void updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete) {

    }

    @Override
    public void deleteDataAttribute(Long id) {

    }

    @Transactional
    @CacheEvict(value = {"plantsCache","userDataCache"}, allEntries = true)
    public void updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        super.updatePlantsCare(plantsCareDTO);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache","userDataCache"}, allEntries = true)
    public void updatePlantsPlanting(PlantsPlantingDTO plantsPlantingDTO) {
        PlantsPlanting planting = plantsPlantingRepo.findById(plantsPlantingDTO.getId()).get();
        if (plantsPlantingDTO.getDescription() != null)
            planting.setDescription(plantsPlantingDTO.getDescription());
        if (plantsPlantingDTO.getAnimation() != null) {
            planting.setAnimation(plantsPlantingDTO.getAnimation());
            planting.setVideo(null);
        }
        if (plantsPlantingDTO.getVideo() != null) {
            planting.setAnimation(null);
            planting.setVideo(plantsPlantingDTO.getVideo());
        }
        if (plantsPlantingDTO.getImage() != null) {
            planting.setAnimation(null);
            planting.setVideo(null);
            planting.setImage(storageConfig.addMedia(plantsPlantingDTO.getImage(), "plantingImages", StorageConfig.SubDir.PLANTING));
        }
        plantsPlantingRepo.save(planting);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        super.updateTipsNTrick(tipsNTrickDTO);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id"),
            @CacheEvict(value = {"plantsImageCache","plantingPlantsImageCache",
                    "plantsAllCache","costPlantsCache","userDataCache"}, allEntries = true)
    })
    public void deletePlants(Long id) {
        Plants plants = plantsRepo.findById(id).get();
        plants.getImage().forEach(item -> {
            if (!storageConfig.deleteMedia(item, StorageConfig.SubDir.PLANTS))
                throw new RuntimeException("failed delete plants, something wrong with file images, please try again");
        });
        plants.getCares().forEach(item -> {
            if (item.getImage() != null)
                if(!storageConfig.deleteMedia(item.getImage(), StorageConfig.SubDir.CARE))
                    throw new RuntimeException("failed delete plants, something wrong with file images, please try again");
        });
        plants.getCares().clear();
        plants.getPlanting().forEach(item -> {
            if (item.getImage() != null)
                if(!storageConfig.deleteMedia(item.getImage(), StorageConfig.SubDir.PLANTING))
                    throw new RuntimeException("failed delete plants, something wrong with file images, please try again");
        });
        plants.getPlanting().clear();
        Plants plants1 = plantsRepo.save(plants);
        plantsRepo.delete(plants1);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache","userDataCache"}, allEntries = true)
    public void deletePlantsCare(Long id) {
        super.deletePlantsCare(id);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache","plantingPlantsImageCache","userDataCache"}, allEntries = true)
    public void deletePlantsPlanting(Long id) {
        PlantsPlanting plantsPlanting = plantsPlantingRepo.findById(id).get();
        if (plantsPlanting.getImage() != null)
            if(!storageConfig.deleteMedia(plantsPlanting.getImage(), StorageConfig.SubDir.PLANTING))
                throw new RuntimeException("failed delete plants planting, something wrong with file images, please try again");
        plantsPlanting.getTipsNTricks().clear();
        PlantsPlanting planting = plantsPlantingRepo.save(plantsPlanting);
        plantsPlantingRepo.delete(planting);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public void deleteTipsNTrick(Long id) {
        super.deleteTipsNTrick(id);
    }
}
