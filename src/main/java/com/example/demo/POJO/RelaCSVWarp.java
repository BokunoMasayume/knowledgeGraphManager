package com.example.demo.POJO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
@Data
public class RelaCSVWarp {

    private Relation relaUnit;

    private String startStr;


    private String endStr;

}
