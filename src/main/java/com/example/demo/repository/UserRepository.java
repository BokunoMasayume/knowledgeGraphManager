package com.example.demo.repository;

import com.example.demo.POJO.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User ,String > {

    User findByUsername(String uesrname);

    User findByEmail(String email);

    User findByPhoneNumber(String phonenumber);

}
