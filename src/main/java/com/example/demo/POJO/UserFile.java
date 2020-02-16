package com.example.demo.POJO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name="for_unique_file_name" , unique = true , def = "{'parentId':1 , 'fileName':1}")
        }
)
public class UserFile {
//    @MongoId cause error : can not query by id
    @Id
    private String id;

    @Indexed
    private String userId;

    private String parentId;

    private String fileName;

    //"folder" "file"
//    private String type;
    private boolean folder;

    private boolean delete;

    private String originName;


}
