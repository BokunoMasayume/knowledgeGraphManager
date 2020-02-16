package com.example.demo.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.example.demo.POJO.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//@RepositoryRestResource(collectionResourceRel = "relation", path="relation")
@Repository
public interface RelaRepository extends Neo4jRepository<Rela,Long> {

//    @Query(value = "match (p:Person)-[r]->(q:Person) with id(r)as id, id(p) as startId ,id(q) as endId,r return  r{.*,startId,endId,id} as relaUnit")
//    List<RelationWarp> findByfileLabel(String fileLabel );
//    @Query(value = "match (p:Person)-[r]->(q:Person) return p,r ,q, id(p) as startId, id(q) as endId")
//    List<Rela> findByfileLabel(String fileLabel );
    @Query(value = "match (p)-[relaUnit]->(q) where $fileLabel in labels(p) and $fileLabel in labels(q) return p,relaUnit,q , id(p) as startId, id(q) as endId")
    List<RelationWarp> findByfileLabel(String fileLabel );
    Rela findById(long i);
}
