package com.example.demo.POJO;

import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
@Data
public class RelationWarp {


    private Relation relaUnit;


    private long startId;
    private long endId;

}
