package com.example.demo.service;

import com.example.demo.POJO.UserFile;

import java.util.Map;

public interface UserFileService {

    //logically delete
    UserFile deleteFile(String fileId , String userId);

    UserFile insertFile(UserFile fileToAdd);

    //update partly
    UserFile patchFile(Map<String , Object> mapToPatch);

    UserFile putFile(UserFile fileToPut);

}
