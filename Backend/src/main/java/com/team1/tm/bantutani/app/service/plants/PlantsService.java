package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlantsService extends PlantsCareService{
    public PlantsService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo, PlantsCareRepo plantsCareRepo,
                         PlantsPlantingRepo plantsPlantingRepo, StorageConfig storageConfig, AnimationRepo animationRepo,
                         PlantsDiseaseRepo plantsDiseaseRepo, PlantsPestRepo plantsPestRepo,
                         PlantsWeedsRepo plantsWeedsRepo, PlantsRepo plantsRepo) {
        super(tipsNTrickRepo, userRepo, plantsCareRepo, plantsPlantingRepo, storageConfig, animationRepo, plantsDiseaseRepo, plantsPestRepo, plantsWeedsRepo, plantsRepo);
    }


    @Transactional
    public void addPlants(PlantsDTO plantsDTO) {
        
    }
}
