package com.example.demo.POJO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.Map;
//cannot use @Data cause stackoverflowerror when saving relationship
@Getter
@Setter
@RelationshipEntity(type = "NORMAL")
public class Relation {
    @Id @GeneratedValue private Long id;

    @JsonIgnore
    @StartNode
    private Node startNode;

    @JsonIgnore
    @EndNode
    private Node endNode;

    @Property
    private String relationName;



    @Properties
    private Map<String , Object> properties;

    @Property
    private boolean delete;
}
