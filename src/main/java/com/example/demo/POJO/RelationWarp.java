package com.example.demo.POJO;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class RelationWarp {
//    private Relation relaUnit;
//
//    public Relation getRelaUnit() {
//        return relaUnit;
//    }
//
//    public void setRelaUnit(Relation relaUnit) {
//        this.relaUnit = relaUnit;
//    }

    private List<Rela> relaUnit;

    public void setRelaUnit(List<Rela> relaUnit) {
        this.relaUnit = relaUnit;
    }

    public List<Rela> getRelaUnit() {
        return relaUnit;
    }

    private long startId;
    private long endId;

    public void setEndId(long endId) {
        this.endId = endId;
    }

    public long getEndId() {
        return endId;
    }

    public void setStartId(long startId) {
        this.startId = startId;
    }

    public long getStartId() {
        return startId;
    }
}
