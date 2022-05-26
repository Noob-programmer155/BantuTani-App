package com.team1.tm.bantutani.app.controller.plants;

import com.team1.tm.bantutani.app.dto.PlantAttributeDTO;
import com.team1.tm.bantutani.app.dto.PlantsCareDTO;
import com.team1.tm.bantutani.app.dto.TipsNTrickDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseDTO;
import com.team1.tm.bantutani.app.dto.response.PlantAttributeResponseMinDTO;
import com.team1.tm.bantutani.app.service.plants.DiseaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class DiseaseController {
    private DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping(value = "/public/plants/disease/v1/care/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Care Image Disease", description = "get plants care image from disease service")
    public byte[] getPlantsCareMedia(@PathVariable String name) {
        return diseaseService.getPlantsCareImage(name);
    }

    @GetMapping(value = "/public/plants/disease/v1/animation/{name}", produces = MediaType.IMAGE_GIF_VALUE)
    @Tag(name = "Get Animation Disease", description = "get animation from disease service")
    public byte[] getAnimationMedia(@PathVariable String name) {
        return diseaseService.getAnimationData(name);
    }

    @GetMapping(value = "/public/plants/disease/v1/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @Tag(name = "Get Plants Disease Image", description = "get plants disease image")
    public byte[] getPlantsDiseaseMedia(@PathVariable String name) { return diseaseService.getPlantsDiseaseImage(name); }

    @GetMapping("/public/plants/disease/v1/data/get/{plantId}")
    @Tag(name = "Get All Plants Disease", description = "get all plants disease data with pagination that have relation to specific plant with minimum information")
    public List<PlantAttributeResponseMinDTO> getDatas(@PathVariable Long plantId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return diseaseService.getPlantsDisease(plantId, page, size);
    }

    @GetMapping("/public/plants/disease/v1/data/self/{id}")
    @Tag(name = "Get Plants Disease", description = "get plants disease with detailed information")
    public PlantAttributeResponseDTO getData(@PathVariable Long id) {
        return diseaseService.getPlantDisease(id);
    }

    @GetMapping("/plants/disease/v1/type/data/{mount}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Get Type Disease", description = "get type disease suggestion with specific mount to displayed")
    public List<String> getTypeDisease(@RequestParam String type, @PathVariable int mount) {
        return  diseaseService.getPlantsTypeDisease(type, mount);
    }

    @PostMapping("/plants/disease/v1/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Disease", description = "adding new plants disease")
    public String addData(@ModelAttribute PlantAttributeDTO plantAttributeDTO) {
        diseaseService.addDataAttribute(plantAttributeDTO);
        return "success";
    }

    @PostMapping("/plants/disease/v1/care/data/add")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Care Disease", description = "adding new plants care in specific plants disease")
    public String addData(@ModelAttribute PlantsCareDTO plantsCareDTO) {
        diseaseService.addPlantsCare(plantsCareDTO, plantsCareDTO.getPlantsDiseaseCare());
        return "success";
    }

    @PostMapping("/plants/disease/v1/care/data/tipsntrick/add/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Tips & Trick Plants Care Disease", description = "adding new tips & trick in specific plants care disease")
    public String addData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO, @PathVariable Long id) {
        diseaseService.addTipsNTrickCare(tipsNTrickDTO, id);
        return "success";
    }

    @PostMapping(value = "/plants/disease/v1/data/image/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Add Plants Disease Image", description = "adding plants to disease image (not replace other image)")
    public String addData(@RequestParam(value = "image") MultipartFile image, @RequestParam Long id) {
        diseaseService.updateImageDataAttribute(image, null, id, false);
        return "success";
    }

    @PutMapping("/plants/disease/v1/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Disease", description = "modify plants disease main data")
    public String updateData(@ModelAttribute PlantAttributeDTO attributeDTO) {
        diseaseService.updateDataAttribute(attributeDTO);
        return "success";
    }

    @PutMapping("/plants/disease/v1/care/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Plants Care Disease", description = "modify plants care in specific plants disease")
    public String updateData(@ModelAttribute PlantsCareDTO plantsCareDTO) throws IOException {
        diseaseService.updatePlantsCare(plantsCareDTO);
        return "success";
    }

    @PutMapping("/plants/disease/v1/tipsntrick/data/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Modify Tips & Trick Disease", description = "modify tips & trick specific plants care in plants disease")
    public String updateData(@ModelAttribute TipsNTrickDTO tipsNTrickDTO) throws IOException {
        diseaseService.updateTipsNTrick(tipsNTrickDTO);
        return "success";
    }

    @DeleteMapping("/plants/disease/v1/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Disease", description = "delete plants disease")
    public String deleteData(@RequestParam Long id) {
        diseaseService.deleteDataAttribute(id);
        return "success";
    }

    @DeleteMapping("/plants/disease/v1/data/image/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Disease Image", description = "delete plants disease image")
    public String deleteData(@RequestParam String imageName, @RequestParam Long id) {
        diseaseService.updateImageDataAttribute(null, imageName, id, true);
        return "success";
    }

    @DeleteMapping("/plants/disease/v1/care/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Plants Care Disease", description = "delete plants care in specific plants disease")
    public String deleteDataCare(@RequestParam Long id) {
        diseaseService.deletePlantsCare(id);
        return "success";
    }

    @DeleteMapping("/plants/disease/v1/tipsntrick/data/delete")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPERTS')")
    @Tag(name = "Delete Tips & Trick Disease", description = "delete tips & trick specific plants care in plants disease")
    public String deleteDataTipsNTrick(@RequestParam Long id) {
        diseaseService.deleteTipsNTrick(id);
        return "success";
    }

    @DeleteMapping("/plants/disease/v1/type/data/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Type Disease", description = "delete type of disease")
    public String deleteTypeDisease(@RequestParam String type) {
        diseaseService.deleteTypeDisease(type);
        return "success";
    }
}
