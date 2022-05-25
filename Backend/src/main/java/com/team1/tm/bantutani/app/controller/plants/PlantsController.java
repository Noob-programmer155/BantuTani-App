package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.*;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseMinDTO;
import com.team1.tm.bantutani.app.service.plants.PlantsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
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

    @GetMapping(value = "/plants/v1/care/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Care Image Plants", description = "get image plants care from plants service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return plantsService.getPlantsCareImage(name);
    }
    @GetMapping(value = "/plants/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation", description = "get animation from plants service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return plantsService.getAnimationData(name);
    }
    @GetMapping(value = "/plants/v1/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Image Plants", description = "get plants image from plants service")
    public byte[] getPlantsMedia(@PathVariable String name) {
        return plantsService.getPlantsImage(name);
    }
    @GetMapping(value = "/plants/v1/planting/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
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

    @GetMapping("/plants/v1/type/data/{mount}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Get Type Plants", description = "get all plants type data suggestion with specific mount to displayed (static)")
    public List<String> getData(@RequestParam String type, @PathVariable int mount) {
        return plantsService.getPlantTypeImpl(type, mount);
    }

    @PostMapping("/plants/v1/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plant", description = "adding new plants")
    public String addData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.addPlants(plantsDTO);
        return "success";
    }

    @PostMapping("/plants/v1/care/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Care Plants", description = "adding new plants care in specific plants")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO){
        plantsService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlants());
        return "success";
    }

    @PostMapping("/plants/v1/care/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Care", description = "adding new tips & trick in specific plants care")
    public String addData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO,@PathVariable Long id) {
        plantsService.addTipsNTrickCare(tipsNTrickDTO, id);
        return "success";
    }

    @PostMapping("/plants/v1/planting/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Planting", description = "adding new plants planting in specific plants")
    public String addData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO){
        plantsService.addPlantsPlanting(plantsPlantingDTO);
        return "success";
    }

    @PostMapping("/plants/v1/planting/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Planting", description = "adding new tips & trick in specific plants planting")
    public String addDataTipsPlanting(@ModelAttribute TipsNTrickDTO tipsNTrickDTO,@PathVariable Long id) {
        plantsService.addTipsNTrickPlanting(tipsNTrickDTO, id);
        return "success";
    }

    @PostMapping(value = "/plants/v1/data/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Image Plants", description = "Adding new Plants image (not replace another images)")
    public String addData(@RequestParam(value = "image") MultipartFile image, @RequestParam Long id) {
        plantsService.updateImage(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/v1/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants", description = "modify plants main data")
    public String modifyData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.updatePlants(plantsDTO);
        return "success";
    }

    @PutMapping("/plants/v1/data/cost/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Cost Plants", description = "modify plant cost manually in all parameter or with no date value")
    public String modifyData(@RequestParam Long plantId,
                             @RequestParam int regionCost,
                             @RequestParam int stableCost,
                             @RequestParam int maxCost,
                             @RequestParam int minCost,
                             @RequestParam(required = false) Date dateUpdate) throws ParseException {
        if (dateUpdate != null){
            plantsService.updateCost(plantId, regionCost, stableCost, maxCost, minCost, dateUpdate);
        } else {
            plantsService.updateCost(plantId, regionCost, stableCost, maxCost, minCost);
        }
        return "success";
    }

    @PutMapping("/plants/v1/planting/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plant Planting Plants", description = "modify plant planting in specific plant")
    public String modifyData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO) {
        plantsService.updatePlantsPlanting(plantsPlantingDTO);
        return "success";
    }

    @PutMapping("/plants/v1/tipsntrick/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Tips & Trick Plants", description = "modify tips & trick in plants care or plants planting in specific plant")
    public String modifyData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) {
        plantsService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @PutMapping("/plants/v1/care/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Care", description = "modify plants care in specific plant")
    public String modifyData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        plantsService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @DeleteMapping("/plants/v1/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants", description = "delete plants")
    public String deleteData(@RequestParam Long id) {
        plantsService.deletePlants(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/data/image/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Image", description = "delete plants image in specific plants")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        plantsService.updateImage(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/v1/care/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Care Plants", description = "delete plants care in specific plants")
    public String deleteDataCare(@RequestParam Long id) {
        plantsService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/planting/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Planting", description = "delete plants planting in specific plants")
    public String deleteDataPlanting(@RequestParam Long id) {
        plantsService.deletePlantsPlanting(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/tipsntrick/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Tips & Trick Plants", description = "delete tips & trick in specific plants care or plants planting")
    public String deleteDataPlantingTipsNTrick(@RequestParam Long id) {
        plantsService.deleteTipsNTrick(id);
        return "success";
    }

    @DeleteMapping("/plants/v1/type/data/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Type Plants", description = "delete type of plants")
    public String deleteTypePlants(@RequestParam String type) {
        plantsService.deletePlantsTypeImpl(type);
        return "success";
    }
}
