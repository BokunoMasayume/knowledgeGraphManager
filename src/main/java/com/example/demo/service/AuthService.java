package com.example.demo.service;

import com.example.demo.POJO.User;

public interface AuthService {

    //add user with user role
    User register(User userToAdd);
    //give validated user token
    String login(String username , String password);
    //refresh validated user token
    String refresh(String oldToken);

    User getSelfInfo(String dressToken);


}
