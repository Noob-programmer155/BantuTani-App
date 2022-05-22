package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.service.plants.WeedService;
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
public class WeedController {
    private WeedService weedService;

    public WeedController(WeedService weedService) {
        this.weedService = weedService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/plants/weed/v1/care/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Care Image", description = "get plants care image from weed service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return weedService.getPlantsCareImage(name);
    }

    @GetMapping(value = "/plants/weed/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation", description = "get animation from weed service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return weedService.getAnimationData(name);
    }

    @GetMapping(value = "/plants/weed/v1/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Weed Image", description = "get plants weed image")
    public byte[] getPlantsWeedMedia(@PathVariable String name) {
        return weedService.getPlantsWeedImage(name);
    }

    @GetMapping("/plants/weed/v1/data/{plantId}")
    @Tag(name = "Get All Plants Weed", description = "get all plants weed data with pagination that have relation to specific plant with minimum information")
    public List<PlantAttributeResponseMinDTO> getDatas(@PathVariable Long plantId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return weedService.getPlantsWeed(plantId, page, size);
    }

    @GetMapping("/plants/weed/v1/data/self/{id}")
    @Tag(name = "Get Plants Weed", description = "get plants weed data with detailed information")
    public PlantAttributeResponseDTO getData(@PathVariable Long id) {
        return weedService.getPlantWeed(id);
    }

    @PostMapping("/plants/weed/v1/data/add")
    @Tag(name = "Add Plants Weed", description = "adding new plants weed")
    public String addData(@ModelAttribute PlantAttributeDTO plantAttributeDTO) {
        weedService.addDataAttribute(plantAttributeDTO);
        return "success";
    }

    @PostMapping("/plants/weed/v1/care/data/add")
    @Tag(name = "Add Plants Care", description = "adding new plants care in specific plants weed")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO) {
        weedService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlantsWeedsCare());
        return "success";
    }

    @PostMapping("/plants/weed/v1/data/image/add")
    @Tag(name = "Add Plants Weed Image", description = "adding image to plants weed (not replace another images)")
    public String addData(@RequestParam MultipartFile image, @RequestParam Long id) {
        weedService.updateImageDataAttribute(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/weed/v1/data/modify")
    @Tag(name = "Modify Plants Weed", description = "modify plants weed main data")
    public String updateData(@ModelAttribute PlantAttributeDTO attributeDTO) {
        weedService.updateDataAttribute(attributeDTO);
        return "success";
    }

    @PutMapping("/plants/weed/v1/care/data/modify")
    @Tag(name = "Modify Plants Care", description = "modify plants care in specific plants weed")
    public String updateData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        weedService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @PutMapping("/plants/weed/v1/tipsntrick/data/modify")
    @Tag(name = "Modify Tips & Trick", description = "modify tips & trick specific plants care in plants weed")
    public String updateData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) throws IOException {
        weedService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @DeleteMapping("/plants/weed/v1/data/delete")
    @Tag(name = "Delete Plants Weed", description = "delete plants weed")
    public String deleteData(@RequestParam Long id) {
        weedService.deleteDataAttribute(id);
        return "success";
    }

    @DeleteMapping("/plants/weed/v1/data/image/delete")
    @Tag(name = "Delete Plants Weed Image", description = "delete plants weed image")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        weedService.updateImageDataAttribute(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/weed/v1/care/data/delete")
    @Tag(name = "Delete Plants Care", description = "delete plants care in specific plants weed")
    public String deleteDataCare(@RequestParam Long id) {
        weedService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/weed/v1/tipsntrick/data/delete")
    @Tag(name = "Delete Tips & Trick", description = "delete tips & trick specific plants care in plants weed")
    public String deleteDataTipsNTrick(@RequestParam Long id) {
        weedService.deleteTipsNTrick(id);
        return "success";
    }
}
