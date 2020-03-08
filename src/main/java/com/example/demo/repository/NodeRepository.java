package com.example.demo.repository;


import com.example.demo.POJO.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node , Long> {
    //in neo4j repository ,your query will forced added a constraint: must have the label Node

    //without check delete property
//    @Query(value = "match (p) where $userLabel in labels(p) and $fileLabel in labels(p) return p")
//    List<Node> findByUserLabelAndFileLabel(String userLabel , String fileLabel);
//
//    @Query(value = "match (p) where $userLabel in labels(p) return p")
//    List<Node> findByUserLabel(String userLabel);
//
//    @Query(value = "match (p) where $labelName in labels(p) return p")
//    List<Node> findByLabelName(String labelName);
//
//    //and
//    @Query(value = "match (p)  where all(la in $labelList where  la in labels(p) ) return p")
//    List<Node> findByLabelsAnd(List<String> labelList);
//
//    //or
//    @Query(value = "match (p)  where any(la in $labelList where  la in labels(p) ) return p")
//    List<Node> findByLabelsOr(List<String> labelList);
//
//    @Query(value="match (p) where $userLabel in labels(p) and $fileLabel in labels(p) detach delete p")
//    void deleteFilePhysically(String userLabel , String fileLabel);
//
//    @Query(value = "match (p) where id(p)=$nodeId and $userLabel in labels(p) and $fileLabel in labels(p) return p")
//    Node findByNodeIdAndUserLabelAndFileLabel(Long nodeId , String userLabel , String fileLabel);






    @Query(value = "match (p) where p.delete=$delete and $userLabel in labels(p) and $fileLabel in labels(p) return p")
    List<Node> findByUserLabelAndFileLabelAndDelete(String userLabel , String fileLabel , boolean delete);

    @Query(value = "match (p) where p.delete=$delete and id(p)=$nodeId and $userLabel in labels(p) and $fileLabel in labels(p) return p")
    Node findByNodeIdAndUserLabelAndFileLabelAndDelete(Long nodeId , String userLabel , String fileLabel , boolean delete);

}
