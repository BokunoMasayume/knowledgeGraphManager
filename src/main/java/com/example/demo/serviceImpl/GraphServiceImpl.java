package com.example.demo.serviceImpl;

import com.example.demo.POJO.Node;
import com.example.demo.POJO.Relation;
import com.example.demo.POJO.RelationWarp;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.RelationRepository;
import com.example.demo.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Override
    public List<Node> getNodesByFile(String userLabel , String fileLabel){
        return nodeRepository.findByUserLabelAndFileLabel(userLabel, fileLabel);
    }

    @Override
    public List<Node> deleteNodesByFile(String userLabel , String fileLabel){
        deleteRelationsByFile(userLabel, fileLabel);

        List<Node> nodes = nodeRepository.findByUserLabelAndFileLabel(userLabel, fileLabel);

        for (Node node : nodes){
            node.setDelete(true);
            nodeRepository.save(node);
        }
        return nodes;
    }

    @Override
    public Node patchNode(String userLabel , String fileLabel , Map<String , Object> mapToPatch){
        Node node = nodeRepository.findByNodeIdAndUserLabelAndFileLabel((Long) mapToPatch.get("id") , userLabel , fileLabel);

        if(mapToPatch.get("labels")!= null){
            node.setLabels((List<String>) mapToPatch.get("labels"));
        }
        if (mapToPatch.get("properties") != null){
            node.setProperties((Map<String, Object>) mapToPatch.get("properties"));
        }
        if (mapToPatch.get("mainLabel") != null){
            node.setMainLabel((String) mapToPatch.get("mainLabel"));
        }

        if (node.getLabels().contains(userLabel) && node.getLabels().contains(fileLabel)){
            return nodeRepository.save(node);
        }else{
            return null;
        }
    }

    @Override
    public Node putNode(String userLabel, String fileLabel, Node nodeToPut) {
        Node node = nodeRepository.findByNodeIdAndUserLabelAndFileLabel(nodeToPut.getId() , userLabel , fileLabel);

        node.setMainLabel(nodeToPut.getMainLabel());
        node.setProperties(nodeToPut.getProperties());
        node.setLabels(nodeToPut.getLabels());

        if (node.getLabels().contains(userLabel) && node.getLabels().contains(fileLabel)){
            return nodeRepository.save(node);
        }else{
            return null;
        }
    }

    @Override
    public Node insertNode(String userLabel, String fileLabel, Node nodeToInsert) {
        if (!nodeToInsert.getLabels().contains(userLabel)){
            nodeToInsert.getLabels().add(userLabel);
        }
        if (!nodeToInsert.getLabels().contains(fileLabel)){
            nodeToInsert.getLabels().add(fileLabel);
        }

        nodeToInsert.setDelete(false);
        return  nodeRepository.save(nodeToInsert);
    }

    @Override
    public Node deleteNode(String userLabel, String fileLabel, Long nodeId) {
        Node node  = nodeRepository.findByNodeIdAndUserLabelAndFileLabel(nodeId , userLabel , fileLabel);

        node.setDelete(true);

        return nodeRepository.save(node);
    }

    @Override
    public Node getNode(String userLabel, String fileLabel, Long nodeId) {
        return nodeRepository.findByNodeIdAndUserLabelAndFileLabel(nodeId , userLabel , fileLabel);
    }

    @Override
    public List<RelationWarp> getRelationsByFile(String userLabel, String fileLabel) {
        return relationRepository.findByUserLabelAndFileLabel(userLabel, fileLabel);
    }

    @Override
    public List<RelationWarp> deleteRelationsByFile(String userLabel, String fileLabel) {
        List<RelationWarp> relations = relationRepository.findByUserLabelAndFileLabel(userLabel, fileLabel);
        for (RelationWarp relation: relations){
            relation.getRelaUnit().setDelete(true);
            relationRepository.save(relation.getRelaUnit());
        }
        return relations;
    }

    @Override
    public Relation patchRelation(String userLabel, String fileLabel, Map<String, Object> mapToPatch) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAnd((Long)mapToPatch.get("id") ,new ArrayList<>( Arrays.asList(fileLabel , userLabel)));

        if (mapToPatch.get("relationName") != null){
            relation.getRelaUnit().setRelationName( (String)mapToPatch.get("relationName") );
        }
        if(mapToPatch.get("properties") != null ){
            relation.getRelaUnit().setProperties( (Map<String, Object>) mapToPatch.get("properties"));
        }

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public Relation putRelation(String userLabel, String fileLabel, Relation relationToPut) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAnd(relationToPut.getId() ,new ArrayList<>( Arrays.asList(fileLabel , userLabel)));

        relation.getRelaUnit().setProperties(relationToPut.getProperties());
        relation.getRelaUnit().setRelationName(relationToPut.getRelationName());

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public Relation insertRelation(String userLabel, String fileLabel, RelationWarp relationToInsert) {
        Relation relation = relationRepository.createEmptyRelationByIds(relationToInsert.getStartId() , relationToInsert.getEndId());

        relation.setRelationName(relationToInsert.getRelaUnit().getRelationName());
        relation.setProperties(relationToInsert.getRelaUnit().getProperties());
        relation.setDelete(false);

        return relationRepository.save(relation);
    }

    @Override
    public Relation deleteRelation(String userLabel, String fileLabel, Long relationId) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAnd(relationId,new ArrayList<>( Arrays.asList(fileLabel , userLabel)));

        relation.getRelaUnit().setDelete(true);

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public RelationWarp getRelation(String userLabel, String fileLabel, Long relationId) {
        return relationRepository.findByIdAndLabelsAnd(relationId,new ArrayList<>( Arrays.asList(fileLabel , userLabel)));
    }
}
