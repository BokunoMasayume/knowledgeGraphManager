package com.example.demo.serviceImpl;

import com.example.demo.POJO.UserModule;
import com.example.demo.repository.UserModuleRepository;
import com.example.demo.service.UserModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@Service
public class UserModuleServiceImpl implements UserModuleService {
    @Autowired
    UserModuleRepository  userModuleRepository;

    @Override
    public UserModule deleteModule(String moduleId, String userId) {
        if(userModuleRepository.existsByParentIds(moduleId)){
            return null;
        }

        UserModule moduleToDele = userModuleRepository.findByIdAndUserId(moduleId , userId);
        if(moduleToDele == null){
            return null;
        }

        userModuleRepository.deleteById(moduleId);
        return moduleToDele;
    }


    @Override
    public UserModule insertModule(UserModule moduleToAdd) {
        if(moduleToAdd.getUserId() == null) return  null;
        if(moduleToAdd.getLabelName() == null) return null;

        return userModuleRepository.insert(moduleToAdd);
    }

    @Override
    public UserModule patchModule(Map<String, Object> mapToPatch) {
        UserModule module = userModuleRepository.findByIdAndUserId((String)mapToPatch.get("id") , (String)mapToPatch.get("userId"));
        if(module == null)return null;

        List<String> patchableProps = new ArrayList<>(Arrays.asList("labelName" , "describe" , "node" , "abstr", "parentIds" , "avatarUri" , "properties" , "groupId"));
        for (String name: patchableProps){
            if(mapToPatch.get(name)!=null){
                Method meth = null;
                try {
                    System.out.println("generate method name: set"+name.substring(0,1).toUpperCase()+name.substring(1) );
                    System.out.println("the class of "+name +"is "+mapToPatch.get(name).getClass().getName());
                    meth = UserModule.class.getMethod("set"+name.substring(0,1).toUpperCase()+name.substring(1) , mapToPatch.get(name).getClass().getName().equals("java.lang.Boolean")?boolean.class:mapToPatch.get(name).getClass());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    meth.invoke(module , mapToPatch.get(name));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return userModuleRepository.save(module);

    }


    @Override
    public UserModule putModule(UserModule moduleToPut) {
        UserModule module = userModuleRepository.findByIdAndUserId(moduleToPut.getId() , moduleToPut.getUserId());
        if(module == null)return null;

//        List<String> patchableProps = new ArrayList<>(Arrays.asList("labelName" , "describe" , "node" , "abstr", "parentIds" , "avatarUri" , "properties"));
        module.setLabelName(moduleToPut.getLabelName());
        module.setDescribe(moduleToPut.getDescribe());
        module.setNode(moduleToPut.isNode());
        module.setAbstr(moduleToPut.isAbstr());
        module.setParentIds(moduleToPut.getParentIds());
        module.setAvatarUri(moduleToPut.getAvatarUri());
        module.setProperties(moduleToPut.getProperties());

        module.setGroupId(moduleToPut.getGroupId());

        return userModuleRepository.save(module);

    }
}
