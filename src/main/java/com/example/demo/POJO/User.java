package com.example.demo.POJO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document
@Data
public class User {
    @Id
    private String id;

    @Indexed(unique = true )
    private String username;

//    @JsonIgnore
    private String password;
    private List<String> roles;

    @Indexed(unique = true )
    private String email;
    @Indexed(unique = true )
    private String phoneNumber;

    @JsonIgnore
    private Date lastPasswordResetDate;

    @JsonIgnore
    private Date lastUsernameResetDate;
    @JsonIgnore
    private Date registerDate;
    private String avatarURI;



}
