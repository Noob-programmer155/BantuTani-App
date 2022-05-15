package com.team1.tm.bantutani.app.service.plants;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.model.TipsNTrick;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.other.TypeActivity;
import com.team1.tm.bantutani.app.model.other.TypeStep;
import com.team1.tm.bantutani.app.model.plants.PlantsCare;
import com.team1.tm.bantutani.app.model.plants.PlantsPlanting;
import com.team1.tm.bantutani.app.repository.*;
import com.team1.tm.bantutani.app.service.utils.AnimationServiceUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TipsNTrikService extends AnimationServiceUtils {
    protected TipsNTrickRepo tipsNTrickRepo;
    protected UserRepo userRepo;
    protected PlantsCareRepo plantsCareRepo;
    protected PlantsPlantingRepo plantsPlantingRepo;
    protected StorageConfig storageConfig;
    public TipsNTrikService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo,
                            PlantsCareRepo plantsCareRepo, PlantsPlantingRepo plantsPlantingRepo,
                            StorageConfig storageConfig, AnimationRepo animationRepo) {
        super(storageConfig, animationRepo);
        this.tipsNTrickRepo = tipsNTrickRepo;
        this.userRepo = userRepo;
        this.plantsCareRepo = plantsCareRepo;
        this.plantsPlantingRepo = plantsPlantingRepo;
        this.storageConfig = storageConfig;
    }

    @Transactional
    public TipsNTrick addTipsNTrick(TipsNTrickDTO tipsNTrickDTO, Long id) {
        TipsNTrick tipsNTrick = new TipsNTrick.Builder().description(tipsNTrickDTO.getDescription()).
                animation(tipsNTrickDTO.getAnimation()).
                typeActivity(TypeActivity.valueOf(tipsNTrickDTO.getTypeActivity())).
                typeStep(TypeStep.valueOf(tipsNTrickDTO.getTypeStep())).
                name(tipsNTrickDTO.getName()).build();
        User user = userRepo.findById(id).get();
        user.getTipsNTricks().add(tipsNTrick);
        tipsNTrick.setAuthorTipsNTrick(user);
        if(tipsNTrickDTO.getPlantsCareTips() != null) {
            PlantsCare plantsCare = plantsCareRepo.findById(tipsNTrickDTO.getPlantsCareTips()).get();
            plantsCare.getTipsNTricks().add(tipsNTrick);
            tipsNTrick.setPlantsCareTips(plantsCare);
        }
        if(tipsNTrickDTO.getPlantsPlantingTips() != null){
            PlantsPlanting plantsPlanting = plantsPlantingRepo.findById(tipsNTrickDTO.getPlantsPlantingTips()).get();
            plantsPlanting.getTipsNTricks().add(tipsNTrick);
            tipsNTrick.setPlantsPlantingTips(plantsPlanting);
        }
        return tipsNTrickRepo.save(tipsNTrick);
    }

    @Transactional
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO, Long id) {
        TipsNTrick tipsNTrick = tipsNTrickRepo.findById(id).get();
        if (tipsNTrickDTO.getDescription() != null)
            tipsNTrick.setDescription(tipsNTrickDTO.getDescription());
        if (tipsNTrickDTO.getAnimation() != null)
            tipsNTrick.setAnimation(tipsNTrickDTO.getAnimation());
        if (tipsNTrickDTO.getName() != null)
            tipsNTrick.setName(tipsNTrickDTO.getName());
        if (tipsNTrickDTO.getTypeActivity() != null)
            tipsNTrick.setTypeActivity(TypeActivity.valueOf(tipsNTrickDTO.getTypeActivity()));
        if (tipsNTrickDTO.getTypeStep() != null)
            tipsNTrick.setTypeStep(TypeStep.valueOf(tipsNTrickDTO.getTypeStep()));
        tipsNTrickRepo.save(tipsNTrick);
    }

    @Transactional
    public void deleteTipsNTrick(Long id) {
        tipsNTrickRepo.deleteById(id);
    }
}
