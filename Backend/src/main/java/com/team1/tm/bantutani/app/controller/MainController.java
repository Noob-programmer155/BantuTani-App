package com.team1.tm.bantutani.app.controller;

import com.team1.tm.bantutani.app.configuration.StorageConfig;
import com.team1.tm.bantutani.app.configuration.security.token.TokenManager;
import com.team1.tm.bantutani.app.dto.*;
import com.team1.tm.bantutani.app.dto.response.*;
import com.team1.tm.bantutani.app.model.Avatar;
import com.team1.tm.bantutani.app.model.User;
import com.team1.tm.bantutani.app.model.other.AnimationType;
import com.team1.tm.bantutani.app.model.other.Status;
import com.team1.tm.bantutani.app.repository.AnimationRepo;
import com.team1.tm.bantutani.app.repository.AvatarRepo;
import com.team1.tm.bantutani.app.repository.UserRepo;
import com.team1.tm.bantutani.app.service.CommodityService;
import com.team1.tm.bantutani.app.service.utils.AnimationServiceUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private CommodityService commodityService;

    private AnimationServiceUtils animationServiceUtils;

    private UserRepo userRepo;
    private AvatarRepo avatarRepo;

    private AuthenticationManager authenticationManager;

    private StorageConfig storageConfig;
    public MainController(AnimationRepo animationRepo, StorageConfig storageConfig, AvatarRepo avatarRepo,
                          UserRepo userRepo, AuthenticationManager authenticationManager, CommodityService commodityService) {
        this.animationServiceUtils = new AnimationServiceUtils(storageConfig, animationRepo);
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.storageConfig = storageConfig;
        this.avatarRepo = avatarRepo;
        this.commodityService = commodityService;
    }

    // ================================== API Harga Komoditi ==============================

    @GetMapping("/public/commodity/v1/data/image/{name}")
    @Tag(name = "Get Image Commodity", description = "get image icon commodity data")
    public byte[] getCommodityImage(@PathVariable String name) {
        return commodityService.getDataImage(name);
    }

    @GetMapping("/public/commodity/v1/data/all")
    @Tag(name = "Get List Commodity", description = "get list commodity data")
    public List<CommodityResponseDTO> getCommodityList(@RequestParam int page, @RequestParam int size) {
        return commodityService.getCommodityList(page, size);
    }

    @GetMapping("/commodity/v1/planttype/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Get List Plants Type Commodity", description = "get list of commodity plant type data for checking available image icon in commodity")
    public List<PlantTypeResponseDTO> getCommodityTypeList() {
        return commodityService.getPlantsType();
    }

    @PostMapping(value = "/commodity/v1/data/image/add/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Add Image Icon Commodity", description = "adding new image icon commodity")
    public StringResponse addCommodity(@RequestParam(value = "file") MultipartFile file, @PathVariable Long id) {
        String name = commodityService.addIcon(file, id);
        return new StringResponse.Builder().status("success").message("Success add new image icon commodity "+name).build();
    }

    @DeleteMapping("/commodity/v1/data/image/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Image Icon Commodity", description = "delete image icon commodity")
    public StringResponse deleteCommodity(@PathVariable Long id, @RequestParam String filename) {
        commodityService.deleteIcon(filename, id);
        return new StringResponse.Builder().status("success").message("Success delete image icon "+filename).build();
    }

    // ================================== API Avatar ======================================

    @GetMapping("/public/user/v1/avatar/get/all")
    @Tag(name = "Get Image Avatar", description = "get images Avatar for User")
    public List<String> getAvatar(@RequestParam int page, @RequestParam int size) {
        return avatarRepo.findAll(PageRequest.of(page, size)).map(item -> item.getName()).getContent();
    }

    @PostMapping(value = "/user/v1/avatar/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Add Image Avatar", description = "adding new images Avatar for User")
    @Transactional
    public StringResponse addAvatar(@RequestParam(value = "avatar") MultipartFile avatarFile) {
        Avatar avatar = new Avatar();
        avatar.setName(storageConfig.addMedia(avatarFile, "avatar", StorageConfig.SubDir.AVATAR));
        Avatar avatar1 = avatarRepo.save(avatar);
        return new StringResponse.Builder().status("success").message("Success add new image avatar "+avatar1.getName()).build();
    }

    @DeleteMapping("/user/v1/avatar/delete/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete Image Avatar", description = "delete image avatar from database and storage")
    @CacheEvict(value = "userDataImageCache", key = "#name")
    @Transactional
    public StringResponse deleteAvatar(@PathVariable String name) {
        Avatar avatar = avatarRepo.findByName(name).get();
        if(storageConfig.deleteMedia(name, StorageConfig.SubDir.AVATAR))
            avatarRepo.delete(avatar);
        return new StringResponse.Builder().status("success").message("Success delete image avatar "+name).build();
    }

    // ================================== API User ========================================

    @GetMapping(value = "/user/v1/data/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','FARMER','EXPERTS','DISTRIBUTOR','SALES')")
    @Tag(name = "Get Image User", description = "getting data image user")
    @Cacheable("userDataImageCache")
    public byte[] getDataImage(@PathVariable String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.AVATAR);
    }

    @GetMapping("/user/v1/data/get")
    @PreAuthorize("hasAuthority('EXPERTS')")
    @Tag(name = "Get Data User Experts", description = "getting data that bring information about how many data that have been added from experts information")
    @Cacheable("userDataCache")
    public UserDataDTO getData(@RequestParam Long id) {
        User user = userRepo.findById(id).get();
        return new UserDataDTO.Builder().care(user.getCare().stream().filter(item ->
                item.getCaringPlants()!=null).map(item ->
                        new PlantsResponseMinDTO.Builder().id(item.getCaringPlants().getId()).name(item.getCaringPlants().getName()).build()).
                collect(Collectors.toList())).diseases(user.getDiseases().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image((!item.getImages().isEmpty())?item.getImages().get(0):null).type(item.getPlantTypeDisease().getType()).build()).
                collect(Collectors.toList())).pests(user.getPests().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image((!item.getImages().isEmpty())?item.getImages().get(0):null).type(item.getPlantTypePest().getType()).build()).
                collect(Collectors.toList())).weeds(user.getWeeds().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image((!item.getImages().isEmpty())?item.getImages().get(0):null).type(item.getPlantTypeWeed().getType()).build()).
                collect(Collectors.toList())).planting(user.getPlantings().stream().filter(item ->
                        item.getPlantingPlants()!=null).map(item ->
                        new PlantsResponseMinDTO.Builder().id(item.getPlantingPlants().getId()).name(item.getPlantingPlants().getName()).build()).
                collect(Collectors.toList())).build();
    }

    @PostMapping("/public/user/v1/signup")
    @Tag(name = "Sign Up", description = "user sign up, default")
    public StringResponse signUp(@ModelAttribute UserDTO userDTO) {
        User user = new User.Builder().email(userDTO.getEmail()).
                image(userDTO.getImage()).password(new SCryptPasswordEncoder().encode(userDTO.getPassword())).
                username(userDTO.getUsername()).status(Status.getFromLabel(userDTO.getStatus())).build();
        user.setDisable(false);
        User user1 = userRepo.save(user);
        return new StringResponse.Builder().status("success").message("Success to signup "+user1.getUsername()+" ready to make new discover, good luck").build();
    }

    @PostMapping("/user/v1/expert/signup")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Sign Up Expert", description = "user sign up for expert role")
    public StringResponse signUpExpert(@ModelAttribute UserDTO userDTO) {
        User user = new User.Builder().email(userDTO.getEmail()).
                image(userDTO.getImage()).password(new SCryptPasswordEncoder().encode(userDTO.getPassword())).
                username(userDTO.getUsername()).status(Status.EXPERTS).build();
        user.setDisable(false);
        User user1 = userRepo.save(user);
        return new StringResponse.Builder().status("success").message("Success add new user expert "+user1.getUsername()+", please be careful and pay attention to this user").build();
    }

    @PostMapping("/public/user/v1/login")
    @Tag(name = "Login", description = "user login for all users")
    public UserResponseDTO login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<User> user = userRepo.findByUsername(auth.getName());
            if(user.isPresent()) {
                User usr = user.get();
                TokenManager.bindToken(usr.getUsername(),usr.getId(), usr.getEmail(),
                        usr.getStatus(), response);
                return new UserResponseDTO.Builder().id(usr.getId()).email(usr.getEmail()).image(usr.getImage()).
                        status(usr.getStatus().getLabel()).username(usr.getUsername()).build();
            }
            throw new NullPointerException(username);
        }
        catch(AuthenticationException | NullPointerException e) {
            response.sendError(401, "Invalid credential`s, please check your credential");
            return null;
        }
        catch(Exception e) {
            response.sendError(500, "There`s some error when connect to the server, try to connect again");
            return null;
        }
    }

    private boolean validatePassword(String encryptedPassword, String password) {
        return new SCryptPasswordEncoder().matches(password, encryptedPassword);
    }

    @PutMapping("/user/v1/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','FARMER','EXPERTS','DISTRIBUTOR','SALES')")
    @Tag(name = "Modify User", description = "modify user data, except password")
    @Transactional
    public StringResponse modify(@ModelAttribute UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).get();
        if (userDTO.getEmail() != null)
            user.setEmail(userDTO.getEmail());
        if (userDTO.getStatus() != null)
            user.setStatus(Status.getFromLabel(userDTO.getStatus()));
        if (userDTO.getUsername() != null)
            user.setUsername(userDTO.getUsername());
        if (userDTO.getImage() != null)
            user.setImage(userDTO.getImage());
        userRepo.save(user);
        return new StringResponse.Builder().status("success").message("Success, Your user account has been updated").build();
    }

    @PutMapping("/user/v1/password/modify")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','FARMER','EXPERTS','DISTRIBUTOR','SALES')")
    @Tag(name = "Modify User Password", description = "modify user password")
    @Transactional
    public StringResponse modifyPassword(@RequestParam Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = userRepo.findById(id).get();
        if (validatePassword(user.getPassword(), oldPassword)) {
            user.setPassword(new SCryptPasswordEncoder().encode(newPassword));
            userRepo.save(user);
            return new StringResponse.Builder().status("success").message("Success, Your password has been updated").build();
        } else {
            throw new RuntimeException("This is wrong password, you can't make steps further");
        }
    }

    @DeleteMapping("/user/v1/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Delete User", description = "delete user")
    @CacheEvict(value = "userDataCache", key = "#id")
    @Transactional
    public StringResponse delete(@RequestParam Long id) {
        User user = userRepo.findById(id).get();
        if(user.getStatus() != Status.ADMIN) {
            if (user.getStatus() != Status.EXPERTS) {
                userRepo.delete(user);
                return new StringResponse.Builder().status("success").message("Success to delete user "+user.getUsername()).build();
            } else {
                user.setDisable(true);
                userRepo.save(user);
                return new StringResponse.Builder().status("success").message("Success to disable experts user "+user.getUsername()).build();
            }
        } else {
            return new StringResponse.Builder().status("failure").message("you can't delete admin").build();
        }
    }

    // ==================================

    @GetMapping("/public/media/v1/animation/data/{type}")
    @Tag(name = "Get Animations", description = "get all animation in the specific type and size")
    public List<String> getAnimationList(@PathVariable String type,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return animationServiceUtils.getAnimations(page,size,type);
    }

    @GetMapping("/media/v1/animation/types")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Get Animation Types", description = "get all animation types that will use in adding animation")
    public List<AnimationType> getAnimationType() {
        return animationServiceUtils.getAnimationType();
    }

    @PostMapping(value = "/media/v1/animation/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "Add Animation", description = "adding new animation to this application")
    public StringResponse addAnimation(@RequestParam(value = "animation") MultipartFile animation, @RequestParam String type) throws IOException {
        animationServiceUtils.addAnimation(animation, type);
        return new StringResponse.Builder().status("success").message("Success add new animation").build();
    }

    @DeleteMapping("/media/v1/animation/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Tag(name = "delete animation", description = "delete animation")
    public StringResponse deleteAnimation(@RequestParam String name) {
        animationServiceUtils.deleteAnimation(name);
        return new StringResponse.Builder().status("success").message("Success delete animation "+name).build();
    }
}
