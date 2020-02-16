package com.example.demo.POJO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.HashMap;
import java.util.Map;

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

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationName() {
        return relationName;
    }

    public Person getEndp() {
        return endp;
    }

    public void setEndp(Person endp) {
        this.endp = endp;
    }

    public Person getStartp() {
        return startp;
    }

    public void setStartp(Person startp) {
        this.startp = startp;
    }

}
