package com.example.demo.service;

import com.example.demo.POJO.UserModuleGroup;

public interface UserModuleGroupService {

    UserModuleGroup deleteModuleGroup(String groupId, String userId);

    UserModuleGroup insertModuleGroup(UserModuleGroup groupToAdd);

    UserModuleGroup putModuleGroup(UserModuleGroup groupToPut);

}
