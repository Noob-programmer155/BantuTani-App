package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.service.plants.PestService;
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
public class PestController {
    private PestService pestService;

    public PestController(PestService pestService) {
        this.pestService = pestService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/plants/pest/v1/care/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Care Image", description = "get plants care image from pest service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return pestService.getPlantsCareImage(name);
    }

    @GetMapping(value = "/plants/pest/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation", description = "get animation from pest service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return pestService.getAnimationData(name);
    }

    @GetMapping(value = "/plants/pest/v1/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Plants Weed Image", description = "get plants weed image")
    public byte[] getPlantsWeedMedia(@PathVariable String name) {
        return pestService.getPlantsPestImage(name);
    }

    @GetMapping("/plants/pest/v1/data/{plantId}")
    @Tag(name = "Get All Plants Pest", description = "get all plants pest data with pagination that have relation to specific plant with minimum information")
    public List<PlantAttributeResponseMinDTO> getDatas(@PathVariable Long plantId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return pestService.getPlantsPest(plantId, page, size);
    }

    @GetMapping("/plants/pest/v1/data/self/{id}")
    @Tag(name = "Get Plants Pest", description = "get plants pest data with detailed information")
    public PlantAttributeResponseDTO getData(@PathVariable Long id) {
        return pestService.getPlantPest(id);
    }

    @PostMapping("/plants/pest/v1/data/add")
    @Tag(name = "Add Plants Pest", description = "adding new data plants pest")
    public String addData(@ModelAttribute PlantAttributeDTO plantAttributeDTO) {
        pestService.addDataAttribute(plantAttributeDTO);
        return "success";
    }

    @PostMapping("/plants/pest/v1/care/data/add")
    @Tag(name = "Add Plants Care", description = "adding new plants care in specific plants pest")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO) {
        pestService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlantsWeedsCare());
        return "success";
    }

    @PostMapping("/plants/pest/v1/data/image/add")
    @Tag(name = "Add Plants Pest Image", description = "adding image to plants pest (not replace another images)")
    public String addData(@RequestParam MultipartFile image, @RequestParam Long id) {
        pestService.updateImageDataAttribute(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/pest/v1/data/modify")
    @Tag(name = "Modify Plants Pest", description = "modify plants pest main data")
    public String updateData(@ModelAttribute PlantAttributeDTO attributeDTO) {
        pestService.updateDataAttribute(attributeDTO);
        return "success";
    }

    @PutMapping("/plants/pest/v1/care/data/modify")
    @Tag(name = "Modify Plants Care", description = "modify plants care in specific plants pest")
    public String updateData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        pestService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @PutMapping("/plants/pest/v1/tipsntrick/data/modify")
    @Tag(name = "Modify Tips & Trick", description = "modify tips & trick specific plants care in plants pest")
    public String updateData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) throws IOException {
        pestService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/data/delete")
    @Tag(name = "Delete Plants Pest", description = "delete plants pest")
    public String deleteData(@RequestParam Long id) {
        pestService.deleteDataAttribute(id);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/data/image/delete")
    @Tag(name = "Delete Plants Pest Image", description = "delete plants pest image")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        pestService.updateImageDataAttribute(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/care/data/delete")
    @Tag(name = "Delete Plants Care", description = "delete plants care in plants pest")
    public String deleteDataCare(@RequestParam Long id) {
        pestService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/tipsntrick/data/delete")
    @Tag(name = "Delete Tips & Trick", description = "delete tips & trick specific plants care in plants pest")
    public String deleteDataTipsNTrick(@RequestParam Long id) {
        pestService.deleteTipsNTrick(id);
        return "success";
    }
}
