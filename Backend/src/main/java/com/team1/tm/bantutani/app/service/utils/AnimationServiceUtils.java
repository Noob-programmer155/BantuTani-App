package com.team1.tm.bantutani.app.service.utils;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.model.Animation;
import com.team1.tm.bantutani.app.model.other.AnimationType;
import com.team1.tm.bantutani.app.repository.AnimationRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AnimationServiceUtils {
    private StorageConfig storageConfig;
    private AnimationRepo animationRepo;

    public AnimationServiceUtils(StorageConfig storageConfig, AnimationRepo animationRepo) {
        this.storageConfig = storageConfig;
        this.animationRepo = animationRepo;
    }

    @Cacheable(value = "animationDataTipsNTrick")
    public byte[] getAnimationData(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.ANIMATION);
    }

    public List<AnimationType> getAnimationType() {
        return Arrays.asList(AnimationType.PLANTSCARE,AnimationType.PLANTSPLANTING,AnimationType.TIPSNTRICK);
    }

    @Cacheable(value = "allAnimationTipsNTrick")
    public List<String> getAnimations(int page, int size, String type) {
        return animationRepo.findAllByAnimationType(AnimationType.valueOf(type),PageRequest.of(page, size)).map(item -> item.getFilename()).getContent();
    }

    @Transactional
    public void addAnimation(MultipartFile file, String type) throws IOException {
        Animation animation = new Animation();
        animation.setAnimationType(AnimationType.valueOf(type));
        animation.setFilename(storageConfig.addMedia(file, "animation", StorageConfig.SubDir.ANIMATION));
        animationRepo.save(animation);
    }

    @Transactional
    @CacheEvict(value = {"allAnimationTipsNTrick","animationDataTipsNTrick"}, allEntries = true)
    public void deleteAnimation(String filename) {
        Animation animation = animationRepo.findByFilename(filename).get();
        if (storageConfig.deleteMedia(animation.getFilename(), StorageConfig.SubDir.ANIMATION))
            animationRepo.delete(animation);
    }
}
