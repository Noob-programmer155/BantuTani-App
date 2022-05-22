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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TipsNTrickService extends AnimationServiceUtils {
    protected TipsNTrickRepo tipsNTrickRepo;
    protected UserRepo userRepo;
    protected PlantsCareRepo plantsCareRepo;
    protected PlantsPlantingRepo plantsPlantingRepo;
    protected StorageConfig storageConfig;
    public TipsNTrickService(TipsNTrickRepo tipsNTrickRepo, UserRepo userRepo,
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
    public TipsNTrick addTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        TipsNTrick tipsNTrick = new TipsNTrick.Builder().description(tipsNTrickDTO.getDescription()).
                animation(tipsNTrickDTO.getAnimation()).
                typeActivity(TypeActivity.getFromLabel(tipsNTrickDTO.getTypeActivity())).
                typeStep(TypeStep.getFromLabel(tipsNTrickDTO.getTypeStep())).
                name(tipsNTrickDTO.getName()).build();
        User user = userRepo.findById(tipsNTrickDTO.getAuthorTipsNTrick()).get();
        user.getTipsNTricks().add(tipsNTrick);
        tipsNTrick.setAuthorTipsNTrick(user);
        return tipsNTrickRepo.save(tipsNTrick);
    }

    @Transactional
    public void updateTipsNTrick(TipsNTrickDTO tipsNTrickDTO) {
        TipsNTrick tipsNTrick = tipsNTrickRepo.findById(tipsNTrickDTO.getId()).get();
        if (tipsNTrickDTO.getDescription() != null)
            tipsNTrick.setDescription(tipsNTrickDTO.getDescription());
        if (tipsNTrickDTO.getAnimation() != null)
            tipsNTrick.setAnimation(tipsNTrickDTO.getAnimation());
        if (tipsNTrickDTO.getName() != null)
            tipsNTrick.setName(tipsNTrickDTO.getName());
        if (tipsNTrickDTO.getTypeActivity() != null)
            tipsNTrick.setTypeActivity(TypeActivity.getFromLabel(tipsNTrickDTO.getTypeActivity()));
        if (tipsNTrickDTO.getTypeStep() != null)
            tipsNTrick.setTypeStep(TypeStep.getFromLabel(tipsNTrickDTO.getTypeStep()));
        tipsNTrickRepo.save(tipsNTrick);
    }

    @Transactional
    public void deleteTipsNTrick(Long id) {
        tipsNTrickRepo.deleteById(id);
    }
}
