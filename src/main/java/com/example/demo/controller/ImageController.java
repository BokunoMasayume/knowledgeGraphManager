package com.example.demo.controller;

import com.example.demo.POJO.User;
import com.example.demo.POJO.UserModule;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserFileRepository;
import com.example.demo.repository.UserModuleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserModuleService;
import com.example.demo.util.ImageUtil;
import com.example.demo.util.JwtTokenUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageUtil imageUtil;

    @Autowired
    UserModuleRepository userModuleRepository;

    @Autowired
    UserModuleService userModuleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/useravatar")
    public String uploadUserAvatar(@RequestParam("image") MultipartFile image) throws IOException {
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> theuseropt = userRepository.findById(user.getId());

        User theuser = theuseropt.get();


        BufferedImage avatarbuf = ImageIO.read(image.getInputStream());

        String imagePath = imageUtil.saveImage(avatarbuf);

        theuser.setAvatarURI(imagePath);
        theuser = userRepository.save(theuser);
        return theuser.getAvatarURI();
    }

    @PostMapping("/moduleavatar/{moduleId}")
    public String uploadModuleAvatar(@RequestParam("image") MultipartFile image , @PathVariable String moduleId) throws IOException {
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModule module = userModuleRepository.findByIdAndUserId(moduleId, user.getId());
        if(module ==null ){
            return null;
        }
        BufferedImage avatarbuf = ImageIO.read(image.getInputStream());
        String imagePath = imageUtil.saveImage(avatarbuf);

        module.setAvatarUri(imagePath);
        return userModuleRepository.save(module).getAvatarUri();

    }
}
