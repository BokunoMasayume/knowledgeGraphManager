package com.example.demo.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
@Data
public class RelationWarp {


    private Relation relaUnit;

    @JsonProperty("source")
    private long startId;

    @JsonProperty("target")
    private long endId;

}
