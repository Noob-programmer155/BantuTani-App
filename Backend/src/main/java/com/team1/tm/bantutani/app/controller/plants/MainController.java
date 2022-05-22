package com.team1.tm.bantutani.app.controller.plants;

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
import com.team1.tm.bantutani.app.service.utils.AnimationServiceUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private AnimationServiceUtils animationServiceUtils;
    private UserRepo userRepo;
    private AvatarRepo avatarRepo;

    private AuthenticationManager authenticationManager;

    private StorageConfig storageConfig;
    public MainController(AnimationRepo animationRepo, StorageConfig storageConfig, AvatarRepo avatarRepo,
                          UserRepo userRepo, AuthenticationManager authenticationManager) {
        this.animationServiceUtils = new AnimationServiceUtils(storageConfig, animationRepo);
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.storageConfig = storageConfig;
        this.avatarRepo = avatarRepo;
    }

    // ================================== API Harga Komoditi ==============================

//    @Tag(name = "", description = ")()

    // ================================== API Avatar ======================================

    @PostMapping("/user/v1/avatar/add")
    @Tag(name = "Add Image Avatar", description = "adding new images Avatar for User")
    public void addAvatar(@RequestParam(value = "avatar") MultipartFile avatarFile) {
        Avatar avatar = new Avatar();
        avatar.setName(storageConfig.addMedia(avatarFile, "avatar", StorageConfig.SubDir.AVATAR));
        avatarRepo.save(avatar);
    }

    @DeleteMapping("/user/v1/avatar/delete/{name}")
    @Tag(name = "Delete Image Avatar", description = "delete image avatar from database and storage")
    @CacheEvict(value = "userDataImageCache", key = "#name")
    public void deleteAvatar(@PathVariable String name) {
        Avatar avatar = avatarRepo.findByName(name).get();
        if(storageConfig.deleteMedia(name, StorageConfig.SubDir.AVATAR))
            avatarRepo.delete(avatar);
    }

    // ================================== API User ========================================

    @GetMapping(value = "/user/v1/data/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Tag(name = "Get Image User", description = "getting data image user")
    @Cacheable("userDataImageCache")
    public byte[] getDataImage(@PathVariable String name) {
        return storageConfig.getMedia(name, StorageConfig.SubDir.AVATAR);
    }

    @GetMapping("/user/v1/data/get")
    @Tag(name = "Get Data User Experts", description = "getting data that bring information about how many data that have been added from experts information")
    @Cacheable("userDataCache")
    public UserDataDTO getData(@RequestParam Long id) {
        User user = userRepo.findById(id).get();
        return new UserDataDTO.Builder().care(user.getCare().stream().filter(item ->
                item.getCaringPlants()!=null).map(item ->
                        new PlantsResponseMinDTO.Builder().id(item.getCaringPlants().getId()).name(item.getCaringPlants().getName()).build()).
                collect(Collectors.toList())).diseases(user.getDiseases().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image(item.getImages().get(0)).type(item.getPlantTypeDisease().getType()).build()).
                collect(Collectors.toList())).pests(user.getPests().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image(item.getImages().get(0)).type(item.getPlantTypePest().getType()).build()).
                collect(Collectors.toList())).weeds(user.getWeeds().stream().map(item ->
                        new PlantAttributeResponseMinDTO.Builder().id(item.getId()).name(item.getName()).
                                image(item.getImages().get(0)).type(item.getPlantTypeWeed().getType()).build()).
                collect(Collectors.toList())).planting(user.getPlantings().stream().filter(item ->
                        item.getPlantingPlants()!=null).map(item ->
                        new PlantsResponseMinDTO.Builder().id(item.getPlantingPlants().getId()).name(item.getPlantingPlants().getName()).build()).
                collect(Collectors.toList())).build();
    }

    @PostMapping("/user/v1/signup")
    @Tag(name = "Sign Up", description = "user sign up, default")
    public String signUp(@ModelAttribute UserDTO userDTO) {
        User user = new User.Builder().email(userDTO.getEmail()).
                image(userDTO.getImage()).password(new SCryptPasswordEncoder().encode(userDTO.getPassword())).
                username(userDTO.getUsername()).status(Status.getFromLabel(userDTO.getStatus())).build();
        user.setDisable(false);
        userRepo.save(user);
        return "success";
    }

    @PostMapping("/user/v1/expert/signup")
    @Tag(name = "Sign Up Expert", description = "user sign up for expert role")
    public String signUpExpert(@ModelAttribute UserDTO userDTO) {
        User user = new User.Builder().email(userDTO.getEmail()).
                image(userDTO.getImage()).password(new SCryptPasswordEncoder().encode(userDTO.getPassword())).
                username(userDTO.getUsername()).status(Status.EXPERTS).build();
        user.setDisable(false);
        userRepo.save(user);
        return "success";
    }

    @PostMapping("/user/v1/login")
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
    @Tag(name = "Modify User", description = "modify user data, except password")
    public String modify(@ModelAttribute UserDTO userDTO) {
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
        return "success";
    }

    @PutMapping("/user/v1/password/modify")
    @Tag(name = "Modify User Password", description = "modify user password")
    public String modifyPassword(@RequestParam Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        User user = userRepo.findById(id).get();
        if (validatePassword(user.getPassword(), oldPassword)) {
            user.setPassword(new SCryptPasswordEncoder().encode(newPassword));
        }
        userRepo.save(user);
        return "success";
    }

    @DeleteMapping("/user/v1/delete")
    @Tag(name = "Delete User", description = "delete user")
    @CacheEvict(value = "userDataCache", key = "#id")
    public String delete(@RequestParam Long id) {
        User user = userRepo.findById(id).get();
        if (user.getStatus() != Status.EXPERTS || user.getStatus() != Status.ADMIN) {
            userRepo.delete(user);
        } else {
            user.setDisable(true);
            userRepo.save(user);
        }
        return "success";
    }

    // ==================================

    @GetMapping("/media/v1/animation/data/{type}")
    @Tag(name = "Get Animations", description = "get all animation in the specific type and size")
    public List<String> getAnimationList(@PathVariable int type,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return animationServiceUtils.getAnimations(page,size,type);
    }

    @GetMapping("/media/v1/animation/types")
    @Tag(name = "Get Animation Types", description = "get all animation types that will use in adding animation")
    public List<AnimationType> getAnimationType() {
        return animationServiceUtils.getAnimationType();
    }

    @PostMapping("/media/v1/animation/add")
    @Tag(name = "Add Animation", description = "adding new animation to this application")
    public String addAnimation(@RequestParam MultipartFile animation, @RequestParam int type) throws IOException {
        animationServiceUtils.addAnimation(animation, type);
        return "success";
    }

    @DeleteMapping("/media/v1/animation/delete")
    @Tag(name = "delete animation", description = "delete animation")
    public String deleteAnimation(@RequestParam String name) {
        animationServiceUtils.deleteAnimation(name);
        return "success";
    }
}
