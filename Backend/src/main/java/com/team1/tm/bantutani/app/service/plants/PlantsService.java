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

    @Cacheable(value = "plantsSearch", key = "#name")
    public List<String> getSearchPlants(String name, int size) {
        return plantsRepo.findDistinctByNameContaining(name, PageRequest.of(0,size, Sort.by("name"))).stream().map(item -> item.getName()).collect(Collectors.toList());
    }

    @Cacheable(value = "getPlantsSearch")
    public List<PlantsResponseMinDTO> getSearchPlants(String name, int page, int size) {
        return plantsRepo.findAllDistinctByNameContaining(name, PageRequest.of(page,size, Sort.by("name"))).
                map(item -> convertPlantsToMinDTO(item)).getContent();
    }

    @Cacheable(value = "plantsAllCache", key = "{#page,#size}")
    public List<PlantsResponseMinDTO> getAllDataPlants(int page, int size) {
        Page<PlantsResponseMin> plants = plantsRepo.findDistinctProjectedBy(PageRequest.of(page,size,Sort.by(Sort.Direction.ASC, "name")));
        return plants.map(item -> convertPlantsToMinDTO(item)).getContent();
    }

    @Cacheable(value = "plantsCache", key = "#id")
    private PlantsResponseDTO getPlants(Long id) {
        PlantsResponse plants = plantsRepo.findPlantsById(id);
        return convertPlantsToDTO(plants);
    }

    @Cacheable(value = "plantsTypeImplCache")
    public List<String> getPlantTypeImpl(String type, int size) {
        return plantTypeImplRepo.findAllByTypeContaining(type, PageRequest.of(0, size)).
                map(item -> item.getType()).getContent();
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
                dateUpdate(plants.getDateUpdate()).build();
    }

    public PlantsResponseMinDTO convertPlantsToMinDTO(PlantsResponseMin plantsResponseMin) {
        PlantsResponseMinDTO responseMinDTO = new PlantsResponseMinDTO.Builder().id(plantsResponseMin.getId()).name(plantsResponseMin.getName()).
                type(plantsResponseMin.getPlantTypeImpl().getType()).date(plantsResponseMin.getDateUpdate()).build();
        if(!plantsResponseMin.getImage().isEmpty()){
            responseMinDTO.setImage(plantsResponseMin.getImage().get(0));
        }
        return responseMinDTO;
    }

    public PlantsResponseDTO convertCostPlantsToDTO(CostPlant costPlant) {
        return new PlantsResponseDTO.Builder().date(costPlant.getDateUpdateCost()).maxCost(costPlant.getMaxCost()).
                minCost(costPlant.getMinCost()).regionCost(costPlant.getRegionCost()).
                stableCost(costPlant.getStableCost()).build();
    }

    public List<PlantsCareResponseDTO> getPlantsCare(List<PlantsCare> plantsCares) {
        return plantsCares.stream().map(item -> convertPlantsCareToDTO(item)).
                sorted((item1,item2) -> item1.getStep().compareTo(item2.getStep())).
                collect(Collectors.toList());
    }

    public List<PlantsPlantingResponseDTO> getPlantsPlanting(List<PlantsPlanting> plantsPlantings) {
        return plantsPlantings.stream().map(item -> {
            return new PlantsPlantingResponseDTO.Builder().id(item.getId()).animation(item.getAnimation()).
                    author(item.getAuthorPlantsPlanting().getUsername()).video(item.getVideo()).
                    image(item.getImage()).description(item.getDescription()).step(item.getStep()).
                    title(item.getTitle()).tipsNTricks(getTipsNTrick(item.getTipsNTricks())).build();
        }).sorted((item1, item2) -> {
            int i = item1.getTitle().compareTo(item2.getTitle());
            if(i == 0)
                return item1.getStep().compareTo(item2.getStep());
            else
                return i;
        }).collect(Collectors.toList());
    }

    public List<TipsNTrickResponseDTO> getTipsNTrick(List<TipsNTrick> tipsNTricks) {
        return tipsNTricks.stream().map(item -> {
            return super.convertTipsNTrickToDTO(item);
        }).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = {"getPlantsSearch","listAllPlantsType","listCommodityCache"}, allEntries = true)
    public void addPlants(PlantsDTO plantsDTO) {
        PlantTypeImpl plantType = null;
        if(plantTypeImplRepo.existsByType(plantsDTO.getPlantTypeImpl()))
            plantType = plantTypeImplRepo.findByType(plantsDTO.getPlantTypeImpl()).get();
        else {
            PlantTypeImpl plantType1 = new PlantTypeImpl();
            plantType1.setType(plantsDTO.getPlantTypeImpl());
            plantType = plantTypeImplRepo.save(plantType1);
        }
        Plants plants = new Plants.Builder().characteristic(plantsDTO.getCharacteristic()).
                name(plantsDTO.getName()).otherNames(plantsDTO.getOtherNames()).
                plantType(plantType).shortDescription(plantsDTO.getShortDescription())
                .dateUpdate(new Date(new java.util.Date().getTime())).build();
        plantType.getPlants().add(plants);

        CostPlant costPlant = new CostPlant.Builder().dateUpdateCost(plantsDTO.getDateUpdateCost()).
                maxCost(plantsDTO.getMaxCost()).minCost(plantsDTO.getMinCost()).
                stableCost(plantsDTO.getStableCost()).regionCost(plantsDTO.getRegionCost()).build();
        CostPlant costPlant1 = costPlantsRepo.save(costPlant);
        costPlant1.setPlants(plants);
        plants.setPlantsCost(costPlant1);

        if (plantsDTO.getImage() != null && !plantsDTO.getImage().isEmpty()) {
            plantsDTO.getImage().forEach(item -> {
                plants.getImage().add(storageConfig.addMedia(item, "plantImages", StorageConfig.SubDir.PLANTS));
            });
        }
        plantsRepo.save(plants);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"userDataCache"}, key = "#plantsCareDTO.getAuthorPlantsCare", condition = "#plantsCareDTO.getAuthorPlantsCare!=null"),
    })
    public String addPlantsCare(PlantsCareDTO plantsCareDTO, Long id) {
        Plants plants = plantsRepo.findById(id).get();
        PlantsCare plantsCare = super.addPlantsCare(plantsCareDTO);
        plants.getCares().add(plantsCare);
        plantsCare.setCaringPlants(plants);
        plants.setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsRepo.save(plants);
        return plants.getName();
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public Long addTipsNTrickCare(TipsNTrickDTO tipsNTrickDTO, Long idCare) {
        PlantsCare plantsCare = plantsCareRepo.findById(idCare).get();
        TipsNTrick tipsNTrick = super.addTipsNTrick(tipsNTrickDTO);
        plantsCare.getTipsNTricks().add(tipsNTrick);
        tipsNTrick.setPlantsCareTips(plantsCare);
        if (plantsCare.getCaringPlants() != null)
            plantsCare.getCaringPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsCareRepo.save(plantsCare);
        return plantsCare.getId();
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public void addTipsNTrickPlanting(TipsNTrickDTO tipsNTrickDTO, Long idPlanting) {
        PlantsPlanting planting = plantsPlantingRepo.findById(idPlanting).get();
        TipsNTrick tipsNTrick = super.addTipsNTrick(tipsNTrickDTO);
        planting.getTipsNTricks().add(tipsNTrick);
        tipsNTrick.setPlantsPlantingTips(planting);
        planting.getPlantingPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsPlantingRepo.save(planting);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#plantsPlantingDTO.getPlantingPlants", condition = "#plantsPlantingDTO.getPlantingPlants!=null"),
            @CacheEvict(value = {"userDataCache"}, key = "#plantsPlantingDTO.getAuthorPlantsPlanting", condition = "#plantsPlantingDTO.getAuthorPlantsPlanting!=null")
    })
    public String addPlantsPlanting(PlantsPlantingDTO plantsPlantingDTO) {
        Plants plants = plantsRepo.findById(plantsPlantingDTO.getPlantingPlants()).get();
        plants.setDateUpdate(new Date(new java.util.Date().getTime()));
        User user = userRepo.findById(plantsPlantingDTO.getAuthorPlantsPlanting()).get();
        PlantsPlanting planting  = new PlantsPlanting.Builder().description(plantsPlantingDTO.getDescription()).
                plants(plants).author(user).step(plantsPlantingDTO.getStep()).title(plantsPlantingDTO.getTitle()).
                build();
        user.getPlantings().add(planting);
        if (plantsPlantingDTO.getAnimation() != null)
            planting.setAnimation(plantsPlantingDTO.getAnimation());
        if (plantsPlantingDTO.getVideo() != null)
            planting.setVideo(plantsPlantingDTO.getVideo());
        if (plantsPlantingDTO.getImage() != null)
            planting.setImage(storageConfig.addMedia(plantsPlantingDTO.getImage(), "plantingImages", StorageConfig.SubDir.PLANTING));
        plantsPlantingRepo.save(planting);
        return plants.getName();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#plantsDTO.getId", condition = "#plantsDTO.getId!=null"),
            @CacheEvict(value = "listAllPlantsType", condition = "#plantsDTO.getPlantTypeImpl", allEntries = true),
            @CacheEvict(value = {"plantsAllCache","getPlantsSearch"}, allEntries = true),
            @CacheEvict(value = {"userDataCache","plantsSearch"}, allEntries = true, condition = "#plantsDTO.getName!=null")
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
            PlantTypeImpl plantType = null;
            if(plantTypeImplRepo.existsByType(plantsDTO.getPlantTypeImpl()))
                plantType = plantTypeImplRepo.findByType(plantsDTO.getPlantTypeImpl()).get();
            else {
                PlantTypeImpl plantType1 = new PlantTypeImpl();
                plantType1.setType(plantsDTO.getPlantTypeImpl());
                plantType = plantTypeImplRepo.save(plantType1);
            }
            plantType.getPlants().add(plants);
            plants.setPlantTypeImpl(plantType);
        }
        if (plantsDTO.getShortDescription() != null)
            plants.setShortDescription(plantsDTO.getShortDescription());
        plants.setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsRepo.save(plants);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id", condition = "#id!=null"),
            @CacheEvict(value = {"plantsAllCache","getPlantsSearch"}, allEntries = true)
    })
    public String updateImage(MultipartFile file, String filename, Long id, boolean delete) {
        Plants plants = plantsRepo.findById(id).get();
        if (delete) {
            deleteImage(filename, plants);
        } else {
            plants.getImage().add(storageConfig.addMedia(file, "plantImages", StorageConfig.SubDir.PLANTS));
        }
        plants.setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsRepo.save(plants);
        return plants.getName();
    }

    @CacheEvict(value = "plantsImageCache", key = "#filename")
    private void deleteImage(String filename, Plants plants) {
        if (storageConfig.deleteMedia(filename, StorageConfig.SubDir.PLANTS))
            plants.getImage().remove(filename);
    }

    // id pake plants id
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "listCommodityCache", allEntries = true),
            @CacheEvict(value = {"costPlantsCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateCost(Long id, int regionCost, int stableCost, int maxCost, int minCost, Date dateUpdate) {
        CostPlant costPlant = costPlantsRepo.findByPlantsId(id).get();
        costPlant.setPreviousCost(costPlant.getRegionCost());
        costPlant.setDateUpdateCost(dateUpdate);
        costPlant.setMaxCost(maxCost);
        costPlant.setMinCost(minCost);
        costPlant.setRegionCost(regionCost);
        costPlant.setStableCost(stableCost);
        costPlantsRepo.save(costPlant);
    }

    // id pake plants id
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "listCommodityCache", allEntries = true),
            @CacheEvict(value = {"costPlantsCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateCost(Long id, int regionCost, int stableCost, int maxCost, int minCost) {
        CostPlant costPlant = costPlantsRepo.findByPlantsId(id).get();
        costPlant.setPreviousCost(costPlant.getRegionCost());
        costPlant.setDateUpdateCost(new Date(new java.util.Date().getTime()));
        costPlant.setMaxCost(maxCost);
        costPlant.setMinCost(minCost);
        costPlant.setRegionCost(regionCost);
        costPlant.setStableCost(stableCost);
        costPlantsRepo.save(costPlant);
    }

    @Transactional
    @Scheduled(fixedRate = 86400000)
    @Caching(evict = {
            @CacheEvict(value = "listCommodityCache", allEntries = true),
            @CacheEvict(value = {"costPlantsCache"}, key = "#id", condition = "#id!=null")
    })
    public void updateAllCostAutomatic() {
        // using scrapper
    }

    @Override
    public void addDataAttribute(PlantAttributeDTO plantAttributeDTO) {}

    @Override
    public String updateDataAttribute(PlantAttributeDTO plantAttributeDTO) {
        return null;
    }

    @Override
    public String updateImageDataAttribute(MultipartFile file, String filename, Long id, boolean delete) {
        return null;
    }

    @Override
    public String deleteDataAttribute(Long id) {
        return null;
    }

    @Transactional
    @CacheEvict(value = {"plantsCache","userDataCache"}, allEntries = true)
    public String updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        return super.updatePlantsCare(plantsCareDTO);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache","userDataCache"}, allEntries = true)
    public String updatePlantsPlanting(PlantsPlantingDTO plantsPlantingDTO) throws IOException {
        PlantsPlanting planting = plantsPlantingRepo.findById(plantsPlantingDTO.getId()).get();
        if (plantsPlantingDTO.getStep() != null)
            planting.setStep(plantsPlantingDTO.getStep());
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
            if (planting.getImage() != null)
                storageConfig.updateMedia(plantsPlantingDTO.getImage(), planting.getImage(), StorageConfig.SubDir.CARE);
            else{
                planting.setAnimation(null);
                planting.setVideo(null);
                planting.setImage(storageConfig.addMedia(plantsPlantingDTO.getImage(), "plantingImages", StorageConfig.SubDir.PLANTING));
            }
        }
        planting.getPlantingPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        plantsPlantingRepo.save(planting);
        return planting.getPlantingPlants().getName();
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        super.updateTipsNTrick(tipsNTrickDTO);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = {"plantsCache"}, key = "#id"),
            @CacheEvict(value = {"plantsImageCache","listCommodityCache","plantingPlantsImageCache","plantsSearch","listAllPlantsType",
                    "plantsAllCache","costPlantsCache","userDataCache","getPlantsSearch"}, allEntries = true)
    })
    public String deletePlants(Long id) {
        Plants plants = plantsRepo.findById(id).get();
        String name = plants.getName();
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
        return name;
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
        plantsPlanting.getPlantingPlants().setDateUpdate(new Date(new java.util.Date().getTime()));
        PlantsPlanting planting = plantsPlantingRepo.save(plantsPlanting);
        plantsPlantingRepo.delete(planting);
    }

    @Transactional
    @CacheEvict(value = {"plantsCache"}, allEntries = true)
    public void deleteTipsNTrick(Long id) {
        super.deleteTipsNTrick(id);
    }

    @Transactional
    @CacheEvict(value = {"plantsTypeImplCache","listAllPlantsType","listCommodityCache"}, allEntries = true)
    public void deletePlantsTypeImpl(String type) {
        plantTypeImplRepo.deleteByType(type);
    }
}
