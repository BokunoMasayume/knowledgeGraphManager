package com.example.demo.repository;

import com.example.demo.POJO.UserModule;
import com.mongodb.Mongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserModuleRepository extends MongoRepository<UserModule ,String> {

    Boolean existsByParentIds(String parentId);

    List<UserModule> findByUserId(String userId);

    UserModule findByIdAndUserId(String id , String userId);

    Boolean existsByIdAndUserId(String id , String userId);

    Boolean existsByGroupIdAndUserId(String groupId, String userId);
}
