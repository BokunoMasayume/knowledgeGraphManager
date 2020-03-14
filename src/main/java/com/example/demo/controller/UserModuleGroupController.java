package com.example.demo.controller;

import com.example.demo.POJO.UserModuleGroup;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.UserModuleGroupRepository;
import com.example.demo.service.UserModuleGroupService;
import com.example.demo.util.JwtTokenUtil;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modulegroup")
public class UserModuleGroupController {
    @Autowired
    private UserModuleGroupRepository userModuleGroupRepository;

    @Autowired
    private UserModuleGroupService userModuleGroupService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<UserModuleGroup> getAllMoudleGroup(){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userModuleGroupRepository.findByUserId(user.getId());
    }

    @PostMapping
    public UserModuleGroup insertOneModuleGroup(@RequestBody UserModuleGroup groupToAdd){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        groupToAdd.setUserId(user.getId());
        return userModuleGroupService.insertModuleGroup(groupToAdd);
    }

    @DeleteMapping("/{groupId}")
    public UserModuleGroup deleteOneModuleGroup(@PathVariable String groupId){
        JwtUser user = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userModuleGroupService.deleteModuleGroup(groupId , user.getId());
    }

    @PutMapping("/{groupId}")
    public UserModuleGroup putOneModuleGroup(@PathVariable String groupId , @RequestBody UserModuleGroup groupToPut){
        JwtUser user = (JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        groupToPut.setUserId(user.getId());
        groupToPut.setId(groupId);

        return userModuleGroupService.putModuleGroup(groupToPut);
    }
}
