package com.example.demo.repository;

import com.example.demo.POJO.UserModuleGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserModuleGroupRepository extends MongoRepository<UserModuleGroup, String> {

    List<UserModuleGroup> findByUserId(String userId);

    Boolean existsByParentId(String parentId);
}
