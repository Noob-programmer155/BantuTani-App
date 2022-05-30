package com.team1.tm.bantutani.app.service;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.dto.response.CommodityResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantTypeResponseDTO;
import com.team1.tm.bantutani.app.model.plants.PlantTypeImpl;
import com.team1.tm.bantutani.app.repository.CostPlantsRepo;
import com.team1.tm.bantutani.app.repository.PlantTypeImplRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommodityService {
    private CostPlantsRepo costPlantsRepo;
    private StorageConfig storageConfig;
    private PlantTypeImplRepo plantTypeImplRepo;

    @Value("${filenotfoundicon}")
    private String fileNotFound;

    public CommodityService(CostPlantsRepo costPlantsRepo, PlantTypeImplRepo plantTypeImplRepo, StorageConfig storageConfig) {
        this.costPlantsRepo = costPlantsRepo;
        this.storageConfig = storageConfig;
        this.plantTypeImplRepo = plantTypeImplRepo;
    }

    @Cacheable(value = "commodityIconCache")
    public byte[] getDataImage(String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.ICON);
    }

    @Cacheable(value = "listCommodityCache")
    public List<CommodityResponseDTO> getCommodityList(int page, int size) {
        return costPlantsRepo.findAll(PageRequest.of(page, size)).map(item -> {
            String filename = item.getPlants().getPlantTypeImpl().getType()+".png";
            if(!storageConfig.checkFileExist(filename, StorageConfig.SubDir.ICON))
                filename = fileNotFound;
            Boolean status = null;
            if(item.getRegionCost() > ((item.getPreviousCost()!=null)?item.getPreviousCost():item.getRegionCost()))
               status = true;
            else {
                if(item.getRegionCost() < ((item.getPreviousCost()!=null)?item.getPreviousCost():item.getRegionCost()))
                    status = false;
            }
            return new CommodityResponseDTO.Builder().icon(filename).currentCost(item.getRegionCost()).
                    previousCost(item.getPreviousCost()).name(item.getPlants().getName()).
                    isIncrease(status).
                    build();
        }).getContent();
    }

    @Cacheable(value = "listAllPlantsType")
    public List<PlantTypeResponseDTO> getPlantsType() {
        return plantTypeImplRepo.findAll(Sort.by(Sort.Direction.ASC,"type")).stream().map(item ->
                new PlantTypeResponseDTO.Builder().type(item.getType()).id(item.getId()).
                        isIconAlready(item.getIcon()).build()).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = {"listAllPlantsType","listCommodityCache"},allEntries = true)
    public String addIcon(MultipartFile file, Long id) {
        PlantTypeImpl plantType = plantTypeImplRepo.findById(id).get();
        storageConfig.addMediaWithName(file, plantType.getType(), StorageConfig.SubDir.ICON);
        plantType.setIcon(true);
        plantTypeImplRepo.save(plantType);
        return plantType.getType();
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "commodityIconCache", key = "#filename", condition = "#filename!=null"),
            @CacheEvict(value = {"listAllPlantsType","listCommodityCache"}, allEntries = true)
    })
    public void deleteIcon(String filename, Long id) {
        PlantTypeImpl plantType = plantTypeImplRepo.findById(id).get();
        if(storageConfig.deleteMedia(filename, StorageConfig.SubDir.ICON))
            plantType.setIcon(false);
        plantTypeImplRepo.save(plantType);
    }
}
