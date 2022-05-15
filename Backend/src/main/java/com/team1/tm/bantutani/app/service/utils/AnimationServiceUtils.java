package com.team1.tm.bantutani.app.service.utils;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.model.Animation;
import com.team1.tm.bantutani.app.repository.AnimationRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Cacheable(value = "allAnimationTipsNTrick")
    public List<String> getAnimations(int page, int size) {
        return animationRepo.findAll(PageRequest.of(page, size)).map(item -> item.getFilename()).getContent();
    }

    @Transactional
    @CacheEvict(value = {"allAnimationTipsNTrick","animationDataTipsNTrick"})
    public void addAnimation(MultipartFile file) throws IOException {
        Animation animation = new Animation();
        animation.setFilename(storageConfig.addMedia(file, "animation", StorageConfig.SubDir.ANIMATION));
        animationRepo.save(animation);
    }

    @Transactional
    @CacheEvict(value = {"allAnimationTipsNTrick","animationDataTipsNTrick"})
    public void deleteAnimation(Long id) {
        Animation animation = animationRepo.findById(id).get();
        if (storageConfig.deleteMedia(animation.getFilename(), StorageConfig.SubDir.ANIMATION))
            animationRepo.delete(animation);
    }
}
