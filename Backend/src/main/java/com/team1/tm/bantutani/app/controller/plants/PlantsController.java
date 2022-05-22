package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.*;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseMinDTO;
import com.team1.tm.bantutani.app.service.plants.PlantsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class PlantsController  {
    private PlantsService plantsService;

    public PlantsController(PlantsService plantsService) {
        this.plantsService = plantsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/plants/v1/care/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Care Image", description = "get image plants care from plants service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return plantsService.getPlantsCareImage(name);
    }
    @GetMapping(value = "/plants/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation", description = "get animation from plants service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return plantsService.getAnimationData(name);
    }
    @GetMapping(value = "/plants/v1/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Image", description = "get plants image from plants service")
    public byte[] getPlantsMedia(@PathVariable String name) {
        return plantsService.getPlantsImage(name);
    }
    @GetMapping(value = "/plants/v1/planting/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Planting Image", description = "get planting image from plants service")
    public byte[] getPlantsPlantingMedia(@PathVariable String name) {
        return plantsService.getPlantingPlantsImage(name);
    }

    @GetMapping("/plants/v1/data/all")
    @Tag(name = "Get Plants Data All", description = "get all plants data with minimum information")
    public List<PlantsResponseMinDTO> getData(@RequestParam int page, @RequestParam int size) {
        return plantsService.getAllDataPlants(page, size);
    }

    @GetMapping("/plants/v1/data/{id}")
    @Tag(name = "Get Plants", description = "get details information about plant")
    public PlantsResponseDTO getData(@PathVariable Long id) {
        return plantsService.getDataPlants(id);
    }

    @PostMapping("/plants/v1/data/add")
    @Tag(name = "Add Plant", description = "adding new plants")
    public String addData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.addPlants(plantsDTO);
        return "success";
    }

    @PostMapping("/plants/v1/care/data/add")
    @Tag(name = "Add Plants Care", description = "adding new plants care in specific plants")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO){
        plantsService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlants());
        return "success";
    }

    @PostMapping("/plants/v1/planting/data/add")
    @Tag(name = "Add Plants Planting", description = "adding new plants planting in specific plants")
    public String addData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO){
        plantsService.addPlantsPlanting(plantsPlantingDTO);
        return "success";
    }

    @PostMapping("/plants/v1/data/image/add")
    @Tag(name = "Add Image Plants", description = "Adding new Plants image (not replace another images)")
    public String addData(@RequestParam MultipartFile image, @RequestParam Long id) {
        plantsService.updateImage(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/v1/data/modify")
    @Tag(name = "Modify Plants", description = "modify plants main data")
    public String modifyData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.updatePlants(plantsDTO);
        return "success";
    }

    @PutMapping("/plants/v1/data/cost/modify/{date}")
    @Tag(name = "Modify Cost Plants", description = "modify plant cost manually in all parameter or with no date value")
    public String modifyData(@RequestParam Long plantId,
                             @RequestParam int regionCost,
                             @RequestParam int stableCost,
                             @RequestParam int maxCost,
                             @RequestParam int minCost,
                             @PathVariable Date dateUpdate) {
        if (dateUpdate != null){
            plantsService.updateCost(plantId, regionCost, stableCost, maxCost, minCost, dateUpdate);
        } else {
            plantsService.updateCost(plantId, regionCost, stableCost, maxCost, minCost);
        }
        return "success";
    }

    @PutMapping("/plants/v1/planting/data/modify")
    @Tag(name = "Modify Plant Planting", description = "modify plant planting in specific plant")
    public String modifyData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO) {
        plantsService.updatePlantsPlanting(plantsPlantingDTO);
        return "success";
    }

    @PutMapping("/plants/v1/tipsntrick/data/modify")
    @Tag(name = "Modify Tips & Trick", description = "modify tips & trick in plants care or plants planting in specific plant")
    public String modifyData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) {
        plantsService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @PutMapping("/plants/v1/care/data/modify")
    @Tag(name = "Modify Plants Care", description = "modify plants care in specific plant")
    public String modifyData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        plantsService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @DeleteMapping("/plants/v1/data/delete")
    @Tag(name = "Delete Plants", description = "delete plants")
    public String deleteData(@RequestParam Long id) {
        plantsService.deletePlants(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/data/image/delete")
    @Tag(name = "Delete Plants Image", description = "delete plants image in specific plants")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        plantsService.updateImage(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/v1/care/data/delete")
    @Tag(name = "Delete Plants Care", description = "delete plants care in specific plants")
    public String deleteDataCare(@RequestParam Long id) {
        plantsService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/planting/data/delete")
    @Tag(name = "Delete Plants Planting", description = "delete plants planting in specific plants")
    public String deleteDataPlanting(@RequestParam Long id) {
        plantsService.deletePlantsPlanting(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/tipsntrick/data/delete")
    @Tag(name = "Delete Tips & Trick", description = "delete tips & trick in specific plants care or plants planting")
    public String deleteDataPlantingTipsNTrick(@RequestParam Long id) {
        plantsService.deleteTipsNTrick(id);
        return "success";
    }
}
