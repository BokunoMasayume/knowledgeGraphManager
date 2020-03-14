package com.example.demo.serviceImpl;

import com.example.demo.POJO.UserFile;
import com.example.demo.repository.UserFileRepository;
import com.example.demo.service.UserFileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    private UserFileRepository userFileRepository;

    @Override
    public UserFile deleteFile(String fileId , String userId) {
        if(userFileRepository.existsByParentIdAndDelete(fileId , false)){
            //have children
            return null;
        }

        Optional<UserFile> fileToDeleOpt =  userFileRepository.findById(fileId);
        if(!fileToDeleOpt.isPresent()){
            //the file do not exist
            return null;
        }


        //delete it
        UserFile fileToDele = fileToDeleOpt.get();

        //not this user's file
        if(!fileToDele.getUserId().equals(userId)) return null;

        fileToDele.setDelete(true);
        fileToDele.setOriginName(fileToDele.getFileName()+new ObjectId().toString());
        fileToDele.setFileName("dele"+new ObjectId().toString());
        return userFileRepository.save( fileToDele);

    }

    @Override
    public UserFile insertFile(UserFile fileToAdd) {
        if(fileToAdd.getFileName()== null) return null;
        if(fileToAdd.getUserId() == null) return null;
        fileToAdd.setDelete(false);

        return userFileRepository.insert(fileToAdd);
    }

    @Override
    public UserFile patchFile(Map<String , Object> mapToPatch) {
        //partly update
        Optional<UserFile> fileOpt = userFileRepository.findById((String) mapToPatch.get("id"));
        if(!fileOpt.isPresent()){
            return null;
        }

        UserFile file = fileOpt.get();
//        UserFile file = userFileRepository.findByIdAndUserId(fileToPatch.getId(), fileToPatch.getUserId());
//        if(file == null){
//            System.out.println("patch nullsss");
//            return null;
//        }
        //not this user's file
        if(!mapToPatch.get("userId").equals(file.getUserId())) return null;

        if(mapToPatch.get("parentId") !=null){
            file.setParentId((String) mapToPatch.get("parentId"));
        }
        if(mapToPatch.get("fileName") !=null){
            file.setFileName((String) mapToPatch.get("fileName"));
        }
        if(mapToPatch.get("folder")!= null){
            file.setFolder((boolean)mapToPatch.get("folder"));
        }
        return userFileRepository.save(file);

    }

    @Override
    public UserFile putFile(UserFile fileToPut) {
        Optional<UserFile> fileOpt = userFileRepository.findById(fileToPut.getId());
        if(!fileOpt.isPresent())return null;



        UserFile file = fileOpt.get();

        //not this user's file
        if(!fileToPut.getUserId().equals(file.getUserId())) return null;

//        file.setType(fileToPut.getType());
        file.setFolder(fileToPut.isFolder());
        file.setParentId(fileToPut.getParentId());
        file.setFileName(fileToPut.getFileName());
        return userFileRepository.save(file);
    }


}
