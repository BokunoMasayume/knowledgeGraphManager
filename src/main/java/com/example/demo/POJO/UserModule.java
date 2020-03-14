package com.example.demo.POJO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Document
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name="for_unique_label_name" , unique = true , def = "{'userId':1 , 'labelName':1 }")
        }
)
public class UserModule {
    @Id
    private String id;

    private String userId;
    private String labelName;
    private String describe;
    private boolean node;
    private boolean abstr;
    private ArrayList<String> parentIds;
    private String avatarUri;
    private LinkedHashMap<String , Map<String , Object>> properties;

    //module group id
    private String groupId;

}
