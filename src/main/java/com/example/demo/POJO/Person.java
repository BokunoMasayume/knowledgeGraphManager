package com.example.demo.POJO;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

@Data
@NodeEntity
public class Person {
    @Id @GeneratedValue private Long id;

    @Labels
    private List<String> ilabel = new ArrayList<>();


    @Properties
    private Map<String,Object> properties = new HashMap<>();

    @Property
    private String mainLabel;

    @JsonManagedReference
    @Relationship(type="NORMAL")
    private Set<Rela> relNodes;



}
