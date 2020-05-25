package com.example.demo.repository;

import com.example.demo.POJO.RelaCSVWarp;
import com.example.demo.POJO.Relation;
import com.example.demo.POJO.RelationWarp;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends Neo4jRepository<Relation, Long> {
//    @Query(value = "match (p)-[relaUnit]->(q) where $userLabel in labels(p) and $fileLabel in labels(p) and $userLabel in labels(q) and $fileLabel in labels(q) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByUserLabelAndFileLabel(String userLabel , String fileLabel);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where $userLabel in labels(p) and $userLabel in labels(q)  return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByUserLabel(String userLabel );
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  all(la in $labelList where la in labels(p)) and  all(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByLabelsAnd(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  any(la in $labelList where la in labels(p)) and  any(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByLabelsOr(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  all(la in $labelList where la in labels(p))  return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByStartLabelsAnd(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  any(la in $labelList where la in labels(p))  return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByStartLabelsOr(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  all(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByEndLabelsAnd(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where  any(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByEndLabelsOr(List<String> labelList);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where id(p)=$startId and id(q)=$endId return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    List<RelationWarp> findByStartIdAndEndId(Long startId, Long endId);
//
    @Query(value = "match (p) where id(p)=$startId "+
                   "match (q) where id(q)=$endId "+
                   "create (p)-[relaUnit:NORMAL]->(q) return p,relaUnit,q "
    )
    Relation createEmptyRelationByIds(Long startId, Long endId);
//
//
    @Query(value = "match (p)-[relaUnit]->(q) where id(p)=$nodeId or id(q)=$nodeId return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
    List<RelationWarp> findByRelatedNodeId(Long nodeId);
//
//    @Query(value = "match (p)-[relaUnit]->(q) where id(relaUnit)=$relationId and all(la in $labelList where la in labels(p)) and  all(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
//    RelationWarp findByIdAndLabelsAnd(Long relationId , List<String> labelList);
//
//





    @Query(value = "match (p)-[relaUnit]->(q) where relaUnit.delete=$delete and $userLabel in labels(p) and $fileLabel in labels(p) and $userLabel in labels(q) and $fileLabel in labels(q) return p ,relaUnit ,q  ,id(p) as startId, id(q) as endId ")
    List<RelationWarp> findByUserLabelAndFileLabelAndDelete(String userLabel , String fileLabel , boolean delete);


    @Query(value = "match (p)-[relaUnit]->(q) where relaUnit.delete=$delete and id(relaUnit)=$relationId and all(la in $labelList where la in labels(p)) and  all(la in $labelList where la in labels(q)) return p,relaUnit ,q ,id(p) as startId, id(q) as endId ")
    RelationWarp findByIdAndLabelsAndAndDelete(Long relationId , List<String> labelList , boolean delete);

    @Query(value = "match (p)-[relaUnit]->(q) where relaUnit.delete=$delete and $userLabel in labels(p) and $fileLabel in labels(p) and $userLabel in labels(q) and $fileLabel in labels(q) return relaUnit ,p.mainLabel as startStr, q.mainLabel as endStr ")
    List<RelaCSVWarp> findForCSVFormat(String userLabel , String fileLabel , boolean delete);
}
