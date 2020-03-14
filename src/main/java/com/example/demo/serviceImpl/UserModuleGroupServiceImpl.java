package com.example.demo.serviceImpl;

import com.example.demo.POJO.UserModuleGroup;
import com.example.demo.configure.CommonNullException;
import com.example.demo.repository.UserModuleGroupRepository;
import com.example.demo.service.UserModuleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserModuleGroupServiceImpl implements UserModuleGroupService {

    @Autowired
    private UserModuleGroupRepository userModuleGroupRepository;

    @Override
    public UserModuleGroup deleteModuleGroup(String groupId, String userId) {
        if(userModuleGroupRepository.existsByParentId(groupId)){
            //have subgroup
            throw new CommonNullException();
        }

        Optional<UserModuleGroup> groupToDeleOpt = userModuleGroupRepository.findById(groupId);
        if(!groupToDeleOpt.isPresent()){
            //not exist
            throw new CommonNullException();
        }

        UserModuleGroup groupToDele = groupToDeleOpt.get();

        if(!groupToDele.getUserId().equals(userId)){
            //not this user's group
            throw new CommonNullException();
        }

        userModuleGroupRepository.deleteById(groupId);

        return groupToDele;
    }

    @Override
    public UserModuleGroup insertModuleGroup(UserModuleGroup groupToAdd) {
        if(groupToAdd.getGroupName()==null || groupToAdd.getUserId()==null){
            throw new CommonNullException();
        }

        return userModuleGroupRepository.insert(groupToAdd);
    }

    @Override
    public UserModuleGroup putModuleGroup(UserModuleGroup groupToPut) {
        Optional<UserModuleGroup> moduleGroupOpt = userModuleGroupRepository.findById(groupToPut.getId());
        if(!moduleGroupOpt.isPresent()){
            throw new CommonNullException();
        }

        UserModuleGroup group = moduleGroupOpt.get();

        if(!group.getUserId().equals(groupToPut.getUserId())){
            //not this user's module group
            throw  new CommonNullException();
        }

        group.setGroupName(groupToPut.getGroupName());
        group.setParentId(groupToPut.getParentId());
        return userModuleGroupRepository.save(group);
    }
}
