package com.example.demo.POJO;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

@NodeEntity
public class Person {
    @Id @GeneratedValue private Long id;

    @Labels
    private List<String> ilabel = new ArrayList<>();


    @Properties
    private Map<String,Object> properties = new HashMap<>();

    @JsonManagedReference
    @Relationship(type="NORMAL")
    private Set<Rela> relNodes;



    public Set<Rela> getRelNodes() {
        return relNodes;
    }

    public void setRelNodes(Set<Rela> relNodes) {
        this.relNodes = relNodes;
    }

    public Long getId() {
        return id;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<String> getIlabel() {
        return ilabel;
    }

    public void setIlabel(List<String> ilabel) {
        this.ilabel = ilabel;
    }

    //    public void setProperties(String key, Object value){
//        this.properties.put(key, value);
//    }
//
//    public Object getProperties(String key){
//        return properties.get(key);
//    }
}
