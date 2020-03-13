package com.example.demo.POJO;

import lombok.Data;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name="for_unique_group_name" , unique = true , def = "{'parentId':1 , 'groupName':1}")
        }
)
public class UserModuleGroup {
    @Id
    private String id;

    @Indexed
    private String userId;

    private String parentId;

    private String groupName;


}
