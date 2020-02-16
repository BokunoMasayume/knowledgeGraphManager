package com.example.demo.repository;

import com.example.demo.POJO.UserFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserFileRepository extends MongoRepository<UserFile , String> {

        List<UserFile> findByUserIdAndDelete(String userId , boolean isDelete);

        Boolean existsByParentIdAndDelete(String parentId , boolean isDelete);

        UserFile findByIdAndUserId(String id, String userId);

}
