package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.dto.response.StringResponse;
import com.team1.tm.bantutani.app.service.plants.WeedService;
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
public class WeedController {
    private WeedService weedService;

    public WeedController(WeedService weedService) {
        this.weedService = weedService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/public/plants/weed/v1/care/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Care Image Weed", description = "get plants care image from weed service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return weedService.getPlantsCareImage(name);
    }

    @GetMapping(value = "/public/plants/weed/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation Weed", description = "get animation from weed service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return weedService.getAnimationData(name);
    }

    @GetMapping(value = "/public/plants/weed/v1/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Weed Image", description = "get plants weed image")
    public byte[] getPlantsWeedMedia(@PathVariable String name) {
        return weedService.getPlantsWeedImage(name);
    }

    @GetMapping("/public/plants/weed/v1/data/get/{plantId}")
    @Tag(name = "Get All Plants Weed", description = "get all plants weed data with pagination that have relation to specific plant with minimum information")
    public List<PlantAttributeResponseMinDTO> getDatas(@PathVariable Long plantId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return weedService.getPlantsWeed(plantId, page, size);
    }

    @GetMapping("/public/plants/weed/v1/data/self/{id}")
    @Tag(name = "Get Plants Weed", description = "get plants weed data with detailed information")
    public PlantAttributeResponseDTO getData(@PathVariable Long id) {
        return weedService.getPlantWeed(id);
    }

//    @GetMapping("/public/plants/weed/v1/data/detection")
//    @Tag(name = "Get Plants Weed", description = "get plants weed data with detailed information")
//    public List<PlantAttributeResponseDetectionDTO> getDataDetection(@RequestParam List<Long> id) {
//        return pestService.getPlantWeedDetection(id);
//    }

    @GetMapping("/plants/weed/v1/type/data/{mount}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Get Type Weed", description = "get type weed data suggestion with specific mount to displayed")
    public List<String> getTypeWeed(@RequestParam String type, @PathVariable int mount) {
        return weedService.getPlantsTypeWeed(type, mount);
    }

    @PostMapping("/plants/weed/v1/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Weed", description = "adding new plants weed")
    public StringResponse addData(@ModelAttribute PlantAttributeDTO plantAttributeDTO) {
        weedService.addDataAttribute(plantAttributeDTO);
        return new StringResponse.Builder().status("success").message("Success add new plants weed").build();
    }

    @PostMapping("/plants/weed/v1/care/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Care Weed", description = "adding new plants care in specific plants weed")
    public StringResponse addData(@ModelAttribute PlantsCareDTO plantsCareDTO) {
        String name = weedService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlantsWeedsCare());
        return new StringResponse.Builder().status("success").message("Success add new plants care in plants weed "+name).build();
    }

    @PostMapping("/plants/weed/v1/care/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Care Weed", description = "adding new tips & trick in specific plants care weed")
    public StringResponse addData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO, @PathVariable Long id) {
        weedService.addTipsNTrickCare(tipsNTrickDTO, id);
        return new StringResponse.Builder().status("success").message("Success add new tips & trick in plants care with id "+id).build();
    }

    @PostMapping(value = "/plants/weed/v1/data/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Weed Image", description = "adding image to plants weed (not replace another images)")
    public StringResponse addData(@RequestParam(value = "image") MultipartFile image, @RequestParam Long id) {
        String name = weedService.updateImageDataAttribute(image, null, id, false);
        return new StringResponse.Builder().status("success").message("Success add new image in plants weed "+name).build();
    }

    @PutMapping("/plants/weed/v1/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Weed", description = "modify plants weed main data")
    public StringResponse updateData(@ModelAttribute PlantAttributeDTO attributeDTO) {
        String name = weedService.updateDataAttribute(attributeDTO);
        return new StringResponse.Builder().status("success").message("Success update plants weed "+name).build();
    }

    @PutMapping("/plants/weed/v1/care/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Care Weed", description = "modify plants care in specific plants weed")
    public StringResponse updateData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        String name = weedService.updatePlantsCare(plantsCareDTO);
        return new StringResponse.Builder().status("success").message("Success update plants care in plants weed "+name).build();
    }

    @PutMapping("/plants/weed/v1/tipsntrick/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Tips & Trick Weed", description = "modify tips & trick specific plants care in plants weed")
    public StringResponse updateData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) throws IOException {
        weedService.updateTipsNTrick(tipsNTrickDTO);
        return new StringResponse.Builder().status("success").message("Success update tips & trick in plants care weed with id "+tipsNTrickDTO.getPlantsCareTips()).build();
    }

    @DeleteMapping("/plants/weed/v1/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Weed", description = "delete plants weed")
    public StringResponse deleteData(@RequestParam Long id) {
        String name = weedService.deleteDataAttribute(id);
        return new StringResponse.Builder().status("success").message("Success delete plants weed "+name).build();
    }

    @DeleteMapping("/plants/weed/v1/data/image/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Weed Image", description = "delete plants weed image")
    public StringResponse deleteData(@RequestParam String imageName, @RequestParam Long id) {
        String name = weedService.updateImageDataAttribute(null, imageName, id, true);
        return new StringResponse.Builder().status("success").message("Success delete image in plants weed "+name).build();
    }

    @DeleteMapping("/plants/weed/v1/care/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Care Weed", description = "delete plants care in specific plants weed")
    public StringResponse deleteDataCare(@RequestParam Long id) {
        weedService.deletePlantsCare(id);
        return new StringResponse.Builder().status("success").message("Success delete plants care with id "+id).build();
    }

    @DeleteMapping("/plants/weed/v1/tipsntrick/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Tips & Trick Weed", description = "delete tips & trick specific plants care in plants weed")
    public StringResponse deleteDataTipsNTrick(@RequestParam Long id) {
        weedService.deleteTipsNTrick(id);
        return new StringResponse.Builder().status("success").message("Success delete tips & trick with id "+id).build();
    }

    @DeleteMapping("/plants/weed/v1/type/data/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Type Weed", description = "delete type of weed")
    public StringResponse deleteTypeWeed(@RequestParam String type) {
        weedService.deleteTypeWeed(type);
        return new StringResponse.Builder().status("success").message("Success delete type plants weed "+type).build();
    }
}
