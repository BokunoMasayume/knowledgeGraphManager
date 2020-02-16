package com.example.demo.controller;


import com.example.demo.POJO.UserModule;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserModuleRepository;
import com.example.demo.service.UserModuleService;
import com.example.demo.util.JwtTokenUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/module")
public class UserModuleController {
    @Autowired
    private UserModuleRepository userModuleRepository;

    @Autowired
    private UserModuleService userModuleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @Data
    public static class MapWarp{
        private Map<String , Object> rawMap;

    public Map<String, Object> getRawMap() {
        return rawMap;
    }

    public void setRawMap(Map<String, Object> rawMap) {
        this.rawMap = rawMap;
    }
}


    @GetMapping
    public List<UserModule> getAllModules(){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModuleRepository.findByUserId(user.getId());
    }

    @PostMapping
    public UserModule insertOneModule(@RequestBody UserModule moduleToAdd){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        moduleToAdd.setUserId(user.getId());

        return userModuleService.insertModule(moduleToAdd);
    }

    @PatchMapping("/{moduleId}")
    public UserModule patchOneModule(@PathVariable String moduleId , @RequestBody MapWarp mapWarp){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        mapWarp.getRawMap().put("userId" , user.getId());
        mapWarp.getRawMap().put("id" , moduleId);

        return userModuleService.patchModule(mapWarp.getRawMap());
    }

    @PutMapping("/{moduleId}")
    public UserModule putOneModule(@PathVariable String moduleId , @RequestBody UserModule moduleToPut){

    }


}
