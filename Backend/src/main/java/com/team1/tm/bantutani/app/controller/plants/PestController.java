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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(value = "/public/plants/pest/v1/care/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Care Image Pest", description = "get plants care image from pest service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return pestService.getPlantsCareImage(name);
    }

    @GetMapping(value = "/public/plants/pest/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation Pest", description = "get animation from pest service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return pestService.getAnimationData(name);
    }

    @GetMapping(value = "/public/plants/pest/v1/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Pest Image", description = "get plants Pest image")
    public byte[] getPlantsWeedMedia(@PathVariable String name) {
        return pestService.getPlantsPestImage(name);
    }

    @GetMapping("/public/plants/pest/v1/data/get/{plantId}")
    @Tag(name = "Get All Plants Pest", description = "get all plants pest data with pagination that have relation to specific plant with minimum information")
    public List<PlantAttributeResponseMinDTO> getDatas(@PathVariable Long plantId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return pestService.getPlantsPest(plantId, page, size);
    }

    @GetMapping("/public/plants/pest/v1/data/self/{id}")
    @Tag(name = "Get Plants Pest", description = "get plants pest data with detailed information")
    public PlantAttributeResponseDTO getData(@PathVariable Long id) {
        return pestService.getPlantPest(id);
    }

    @GetMapping("/plants/pest/v1/type/data/{mount}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Get Type Pest", description = "get type pest data suggestion with specific mount to displayed (static)")
    public List<String> getTypePest(@RequestParam String type, @PathVariable int mount) {
        return pestService.getPlantsTypePest(type, mount);
    }

    @PostMapping("/plants/pest/v1/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Pest", description = "adding new data plants pest")
    public String addData(@ModelAttribute PlantAttributeDTO plantAttributeDTO) {
        pestService.addDataAttribute(plantAttributeDTO);
        return "success";
    }

    @PostMapping("/plants/pest/v1/care/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Care Pest", description = "adding new plants care in specific plants pest")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO) {
        pestService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlantsPestCare());
        return "success";
    }

    @PostMapping("/plants/pest/v1/care/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Care Pest", description = "adding new tips & trick in specific plants care pest")
    public String addData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO, @PathVariable Long id) {
        pestService.addTipsNTrickCare(tipsNTrickDTO, id);
        return "success";
    }

    @PostMapping(value = "/plants/pest/v1/data/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Pest Image", description = "adding image to plants pest (not replace another images)")
    public String addData(@RequestParam(value = "image") MultipartFile image, @RequestParam Long id) {
        pestService.updateImageDataAttribute(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/pest/v1/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Pest", description = "modify plants pest main data")
    public String updateData(@ModelAttribute PlantAttributeDTO attributeDTO) {
        pestService.updateDataAttribute(attributeDTO);
        return "success";
    }

    @PutMapping("/plants/pest/v1/care/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Care Pest", description = "modify plants care in specific plants pest")
    public String updateData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        pestService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @PutMapping("/plants/pest/v1/tipsntrick/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Tips & Trick Pest", description = "modify tips & trick specific plants care in plants pest")
    public String updateData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) throws IOException {
        pestService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Pest", description = "delete plants pest")
    public String deleteData(@RequestParam Long id) {
        pestService.deleteDataAttribute(id);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/data/image/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Pest Image", description = "delete plants pest image")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        pestService.updateImageDataAttribute(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/care/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Care Pest", description = "delete plants care in plants pest")
    public String deleteDataCare(@RequestParam Long id) {
        pestService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/tipsntrick/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Tips & Trick Pest", description = "delete tips & trick specific plants care in plants pest")
    public String deleteDataTipsNTrick(@RequestParam Long id) {
        pestService.deleteTipsNTrick(id);
        return "success";
    }

    @DeleteMapping("/plants/pest/v1/type/data/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Type Pest", description = "delete type of plants pest")
    public String deleteTypePest(@RequestParam String type) {
        pestService.deletePlantsTypePest(type);
        return "success";
    }
}
