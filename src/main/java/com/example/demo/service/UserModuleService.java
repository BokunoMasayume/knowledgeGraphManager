package com.example.demo.service;

import com.example.demo.POJO.UserModule;

import java.util.Map;

public interface UserModuleService {

    //delete physically
    UserModule deleteModule(String moduleId , String userId);

    UserModule insertModule(UserModule moduleToAdd);

    UserModule patchModule(Map<String , Object> mapToPatch);

    UserModule putModule(UserModule moduleToPut);
}
