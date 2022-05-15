package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.model.Plants;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.dataAttr.CareType;
import com.team1.tm.bantutani.app.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PlantsCareService extends TipsNTrikService{
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

    @Transactional
    public PlantsCare addPlantsCare(PlantsCareDTO plantsCareDTO) {
        PlantsCare plantsCare = new PlantsCare.Builder().careType(CareType.valueOf(plantsCareDTO.getCareType())).
                author(userRepo.findById(plantsCareDTO.getAuthorPlantsCare()).get()).
                description(plantsCareDTO.getDescription()).build();
        if (plantsCareDTO.getTipsNTrickDTOList() != null) {
            List<TipsNTrick> tipsNTrickList = new LinkedList<>();
            plantsCareDTO.getTipsNTrickDTOList().forEach(item -> {
                TipsNTrick tipsNTrick = addTipsNTrick(item, plantsCareDTO.getAuthorPlantsCare());
                tipsNTrick.setPlantsCareTips(plantsCare);
                tipsNTrickList.add(tipsNTrick);
            });
            plantsCare.getTipsNTricks().addAll(tipsNTrickList);
        }
        if (plantsCareDTO.getPlantsDiseaseCare() != null)
            plantsCare.setPlantsDiseaseCare(plantsDiseaseRepo.findById(plantsCareDTO.getPlantsDiseaseCare()).get());
        if (plantsCareDTO.getPlantsPestCare() != null)
            plantsCare.setPlantsPestCare(plantsPestRepo.findById(plantsCareDTO.getPlantsPestCare()).get());
        if (plantsCareDTO.getPlantsWeedsCare() != null)
            plantsCare.setPlantsWeedsCare(plantsWeedsRepo.findById(plantsCareDTO.getPlantsWeedsCare()).get());
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
    public void updatePlantsCare(PlantsCareDTO plantsCareDTO) throws IOException {
        PlantsCare plantsCare = plantsCareRepo.findById(plantsCareDTO.getId()).get();
        if (plantsCareDTO.getCareType() != null)
            plantsCare.setCareType(CareType.valueOf(plantsCareDTO.getCareType()));
        if (plantsCareDTO.getDescription() != null)
            plantsCare.setDescription(plantsCareDTO.getDescription());
        if (plantsCareDTO.getAnimation() != null) {
            plantsCare.setAnimation(plantsCareDTO.getAnimation());
            plantsCare.setImage(null);
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
            plantsCare.setImage(null);
        }
        plantsCareRepo.save(plantsCare);
    }

    @Transactional
    public void deletePlantsCare(Long id) {
        PlantsCare plantsCare = plantsCareRepo.findById(id).get();
        plantsCare.getTipsNTricks().clear();
        PlantsCare plantsCare1 = plantsCareRepo.save(plantsCare);
        plantsCareRepo.delete(plantsCare1);
    }
}
