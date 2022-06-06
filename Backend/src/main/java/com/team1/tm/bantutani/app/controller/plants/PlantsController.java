package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.*;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantsResponseMinDTO;
import com.team1.tm.bantutani.app.dto.response.StringResponse;
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

    @GetMapping(value = "/public/plants/v1/care/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Care Image Plants", description = "get image plants care from plants service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return plantsService.getPlantsCareImage(name);
    }
    @GetMapping(value = "/public/plants/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation", description = "get animation from plants service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return plantsService.getAnimationData(name);
    }
    @GetMapping(value = "/public/plants/v1/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Image Plants", description = "get plants image from plants service")
    public byte[] getPlantsMedia(@PathVariable String name) {
        return plantsService.getPlantsImage(name);
    }
    @GetMapping(value = "/public/plants/v1/planting/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Planting Image", description = "get planting image from plants service")
    public byte[] getPlantsPlantingMedia(@PathVariable String name) {
        return plantsService.getPlantingPlantsImage(name);
    }

    @GetMapping("/public/plants/v1/data/all")
    @Tag(name = "Get Plants Data All", description = "get all plants data with minimum information")
    public List<PlantsResponseMinDTO> getData(@RequestParam int page, @RequestParam int size) {
        return plantsService.getAllDataPlants(page, size);
    }

    @GetMapping("/public/plants/v1/data/{mount}/search")
    @Tag(name = "Get Search Plants", description = "get plants dynamic search data")
    public List<String> getDataSearch(@RequestParam(value = "q") String name, @PathVariable int mount) {
        return plantsService.getSearchPlants(name, mount);
    }

    @GetMapping("/public/plants/v1/data/search/get")
    @Tag(name = "Get Plants Data Search", description = "get data from dynamic search")
    public List<PlantsResponseMinDTO> getData(@RequestParam(value = "q") String name,
                                              @RequestParam int page, @RequestParam int size) {
        return plantsService.getSearchPlants(name, page, size);
    }

    @GetMapping("/public/plants/v1/data/{id}")
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
    public StringResponse addData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.addPlants(plantsDTO);
        return new StringResponse.Builder().status("success").message("Success add new plants").build();
    }

    @PostMapping("/plants/v1/care/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Care Plants", description = "adding new plants care in specific plants")
    public StringResponse addData(@ModelAttribute PlantsCareDTO plantsCareDTO){
        String name = plantsService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlants());
        return new StringResponse.Builder().status("success").message("Success add new plants care for plants "+name).build();
    }

    @PostMapping("/plants/v1/care/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Care", description = "adding new tips & trick in specific plants care")
    public StringResponse addData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO,@PathVariable Long id) {
        Long ids = plantsService.addTipsNTrickCare(tipsNTrickDTO, id);
        return new StringResponse.Builder().status("success").message("Success add new tips & trick in plants care id "+ids).build();
    }

    @PostMapping("/plants/v1/planting/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Planting", description = "adding new plants planting in specific plants")
    public StringResponse addData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO){
        String name = plantsService.addPlantsPlanting(plantsPlantingDTO);
        return new StringResponse.Builder().status("success").message("Success add new plants planting for plants "+name).build();
    }

    @PostMapping("/plants/v1/planting/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Planting", description = "adding new tips & trick in specific plants planting")
    public StringResponse addDataTipsPlanting(@ModelAttribute TipsNTrickDTO tipsNTrickDTO,@PathVariable Long id) {
        plantsService.addTipsNTrickPlanting(tipsNTrickDTO, id);
        return new StringResponse.Builder().status("success").message("Success add tips & trick in plants planting with id "+id).build();
    }

    @PostMapping(value = "/plants/v1/data/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Image Plants", description = "Adding new Plants image (not replace another images)")
    public StringResponse addData(@RequestParam(value = "image") MultipartFile image, @RequestParam Long id) {
        String name = plantsService.updateImage(image, null, id, false);
        return new StringResponse.Builder().status("success").message("Success add new image for plants "+name).build();
    }

    @PutMapping("/plants/v1/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants", description = "modify plants main data")
    public StringResponse modifyData(@ModelAttribute PlantsDTO plantsDTO) {
        plantsService.updatePlants(plantsDTO);
        return new StringResponse.Builder().status("success").message("Success update plants with id "+plantsDTO.getId()).build();
    }

    @PutMapping("/plants/v1/data/cost/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Cost Plants", description = "modify plant cost manually in all parameter or with no date value")
    public StringResponse modifyData(@RequestParam Long plantId,
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
        return new StringResponse.Builder().status("success").message("Success update plants cost with plants id "+plantId).build();
    }

    @PutMapping("/plants/v1/planting/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plant Planting Plants", description = "modify plant planting in specific plant")
    public StringResponse modifyData(@ModelAttribute PlantsPlantingDTO plantsPlantingDTO) throws IOException {
        String name = plantsService.updatePlantsPlanting(plantsPlantingDTO);
        return new StringResponse.Builder().status("success").message("Success update plants planting in plants "+name).build();
    }

    @PutMapping("/plants/v1/tipsntrick/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Tips & Trick Plants", description = "modify tips & trick in plants care or plants planting in specific plant")
    public StringResponse modifyData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) {
        plantsService.updateTipsNTrick(tipsNTrickDTO);
        return new StringResponse.Builder().status("success").message("Success update tips & trick with id "+tipsNTrickDTO.getId()).build();
    }

    @PutMapping("/plants/v1/care/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Care", description = "modify plants care in specific plant")
    public StringResponse modifyData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        String name = plantsService.updatePlantsCare(plantsCareDTO);
        return new StringResponse.Builder().status("success").message("Success update plants care in plants "+name).build();
    }

    @DeleteMapping("/plants/v1/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants", description = "delete plants")
    public StringResponse deleteData(@RequestParam Long id) {
        String name = plantsService.deletePlants(id);
        return new StringResponse.Builder().status("success").message("Success delete plants "+name).build();
    }

    @DeleteMapping("/plants/v1/data/image/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Image", description = "delete plants image in specific plants")
    public StringResponse deleteData(@RequestParam String imageName, @RequestParam Long id) {
        String name = plantsService.updateImage(null, imageName, id, true);
        return new StringResponse.Builder().status("success").message("Success delete plants image for plants "+name).build();
    }

    @DeleteMapping("/plants/v1/care/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Care Plants", description = "delete plants care in specific plants")
    public StringResponse deleteDataCare(@RequestParam Long id) {
        plantsService.deletePlantsCare(id);
        return new StringResponse.Builder().status("success").message("Success delete plants care with id "+id).build();
    }

    @DeleteMapping("/plants/v1/planting/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Planting", description = "delete plants planting in specific plants")
    public StringResponse deleteDataPlanting(@RequestParam Long id) {
        plantsService.deletePlantsPlanting(id);
        return new StringResponse.Builder().status("success").message("Success delete plants planting with id "+id).build();
    }

    @DeleteMapping("/plants/v1/tipsntrick/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Tips & Trick Plants", description = "delete tips & trick in specific plants care or plants planting")
    public StringResponse deleteDataPlantingTipsNTrick(@RequestParam Long id) {
        plantsService.deleteTipsNTrick(id);
        return new StringResponse.Builder().status("success").message("Success delete tips & trick with id "+id).build();
    }

    @DeleteMapping("/plants/v1/type/data/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Type Plants", description = "delete type of plants")
    public StringResponse deleteTypePlants(@RequestParam String type) {
        plantsService.deletePlantsTypeImpl(type);
        return new StringResponse.Builder().status("success").message("Success delete type plants with name type "+type).build();
    }
}
