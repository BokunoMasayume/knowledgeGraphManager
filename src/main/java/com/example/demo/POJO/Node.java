package com.example.demo.POJO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NodeEntity
public class Node {
    @Id @GeneratedValue private Long id;

    @Labels
    private List<String> labels ;

    @Properties
    private Map<String , Object> properties;

    //the label related to the showed up module avatar
    @Property
    private String mainLabel;

    @JsonIgnore
    @Relationship(type = "NORMAL")
    private Set<Relation> relNodes;

    @Property
    private boolean delete;
}
