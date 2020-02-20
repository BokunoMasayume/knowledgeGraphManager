package com.example.demo.POJO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.HashMap;
import java.util.Map;
@Data
@RelationshipEntity(type="NORMAL")
public class Rela {
    @Id @GeneratedValue private Long relationshipId;


    @JsonBackReference
    @StartNode
    private Person startp;

    @JsonIgnore
    @EndNode
    private Person endp;


    @Property
    private String relationName;

    @Properties
    private Map<String ,Object> properties = new HashMap<>();


}
