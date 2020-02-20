package com.example.demo.service;

import com.example.demo.POJO.Node;
import com.example.demo.POJO.Relation;
import com.example.demo.POJO.RelationWarp;

import java.util.List;
import java.util.Map;

public interface GraphService {


    //node methods

    List<Node> getNodesByFile(String userLabel , String fileLabel);

    //logically ps:delete files physically can straightly detach delete nodes
    List<Node> deleteNodesByFile(String userLabel , String fileLabel);

    Node patchNode(String userLabel , String fileLabel , Map<String , Object> mapToPatch);

    Node PutNode(String userLabel, String fileLabel , Node nodeToPut);

    Node insertNode(String userLabel , String fileLabel , Node nodeToInsert);

    //logically
    Node deleteNode(String userLabel , String fileLabel , Long nodeId);

    Node getNode(String userLabel , String fileLabel , Long nodeId);

    //relationship methods

    List<RelationWarp> getRelationsByFile(String userLabel , String fileLabel);

    //logically
    List<RelationWarp> deleteRelationsByFile(String userLabel , String fileLabel);

    Relation patchRelation(String userLabel , String fileLabel ,  Map<String , Object> mapToPatch);

    Relation putRelation(String userLabel , String fileLabel , Relation relationToPut);

    Relation insertRelation(String userLabel , String fileLabel ,RelationWarp relationToInsert);

    //logically
    Relation deleteRelation(String userLabel , String fileLabel , Long relationId);

    RelationWarp getRelation(String userLabel , String fileLabel , Long relationId);

}
