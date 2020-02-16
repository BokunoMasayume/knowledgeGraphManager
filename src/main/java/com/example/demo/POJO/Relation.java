package com.example.demo.POJO;

import org.neo4j.ogm.annotation.Properties;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Map;
@QueryResult
public class Relation {
    private long startId ;
    private long endId;


    private long id;

    @Properties
    private Map<String, Object>properties;
    private String relationName;

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartId() {
        return startId;
    }

    public void setStartId(long startId) {
        this.startId = startId;
    }

    public long getEndId() {
        return endId;
    }

    public void setEndId(long endId) {
        this.endId = endId;
    }
}
