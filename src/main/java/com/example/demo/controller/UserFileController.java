package com.example.demo.controller;

import com.example.demo.POJO.UserFile;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserFileRepository;
import com.example.demo.service.UserFileService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class UserFileController {
//    @Value("${jwt.tokenHeader}")
//    private String tokenHead;

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserFile> getAllFiles(){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userFileRepository.findByUserIdAndDelete(user.getId() , false);
    }

    @PostMapping
    public UserFile insertOneFile( @RequestBody UserFile fileToAdd){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fileToAdd.setUserId(user.getId());
        return userFileService.insertFile(fileToAdd);
    }

    @PatchMapping("/{fileId}")
    public  UserFile patchOneFile(@PathVariable String fileId , @RequestBody Map<String , Object> mapToPatch){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        mapToPatch.put("userId"  , user.getId());
        mapToPatch.put("id" , fileId);
//        fileToPatch.setUserId(user.getId());
//        fileToPatch.setId(fileId);

        return userFileService.patchFile(mapToPatch);
    }

    @PutMapping("/{fileId}")
    public UserFile putOneFile(@PathVariable String fileId , @RequestBody UserFile fileToPut){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fileToPut.setUserId(user.getId());
        fileToPut.setId(fileId);
        return  userFileService.putFile(fileToPut);
    }

    @DeleteMapping("/{fileId}")
    public UserFile deleteOneFile(@PathVariable String fileId ){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userFileService.deleteFile(fileId,user.getId());
    }


}
