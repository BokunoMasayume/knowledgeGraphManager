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
        return nodeRepository.findByUserLabelAndFileLabelAndDelete(userLabel, fileLabel,false);
    }

    @Override
    public List<Node> deleteNodesByFile(String userLabel , String fileLabel){
        deleteRelationsByFile(userLabel, fileLabel);

        List<Node> nodes = nodeRepository.findByUserLabelAndFileLabelAndDelete(userLabel, fileLabel,false);

        for (Node node : nodes){
            node.setDelete(true);
            nodeRepository.save(node);
        }
        return nodes;
    }

    @Override
    public Node patchNode(String userLabel , String fileLabel , Node nodeToPatch){
        Node node = nodeRepository.findByNodeIdAndUserLabelAndFileLabelAndDelete(nodeToPatch.getId() , userLabel , fileLabel  ,false);

        if(node== null)return null;
//        System.out.println("have this node");

        if(nodeToPatch.getLabels()!= null){
            node.setLabels(nodeToPatch.getLabels());
        }
        if (nodeToPatch.getProperties() != null){
            node.setProperties(nodeToPatch.getProperties());
        }
        if (nodeToPatch.getMainLabel()!= null){
            node.setMainLabel(nodeToPatch.getMainLabel());
        }

        if (node.getLabels().contains(userLabel) && node.getLabels().contains(fileLabel)){
            return nodeRepository.save(node);
        }else{
            return null;
        }
    }

    @Override
    public Node putNode(String userLabel, String fileLabel, Node nodeToPut) {
        Node node = nodeRepository.findByNodeIdAndUserLabelAndFileLabelAndDelete(nodeToPut.getId() , userLabel , fileLabel,false);

        if(node== null)return null;

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
        Node node  = nodeRepository.findByNodeIdAndUserLabelAndFileLabelAndDelete(nodeId , userLabel , fileLabel,false);

        if(node== null)return null;
        deleteRelationsByRelatedNodeId(node.getId());
        node.setDelete(true);

        return nodeRepository.save(node);
    }

    @Override
    public Node getNode(String userLabel, String fileLabel, Long nodeId) {
        return nodeRepository.findByNodeIdAndUserLabelAndFileLabelAndDelete(nodeId , userLabel , fileLabel,false);
    }

    public void deleteRelationsByRelatedNodeId(Long nodeId){
        List<RelationWarp> relationwarps = relationRepository.findByRelatedNodeId(nodeId);
        for(RelationWarp warp : relationwarps){
            warp.getRelaUnit().setDelete(true);
            relationRepository.save(warp.getRelaUnit());
        }
    }

    @Override
    public List<RelationWarp> getRelationsByFile(String userLabel, String fileLabel) {
        return relationRepository.findByUserLabelAndFileLabelAndDelete(userLabel, fileLabel,false);
    }

    @Override
    public List<RelationWarp> deleteRelationsByFile(String userLabel, String fileLabel) {
        List<RelationWarp> relations = relationRepository.findByUserLabelAndFileLabelAndDelete(userLabel, fileLabel,false);
        for (RelationWarp relation: relations){
            relation.getRelaUnit().setDelete(true);
            relationRepository.save(relation.getRelaUnit());
        }
        return relations;
    }

    @Override
    public Relation patchRelation(String userLabel, String fileLabel, Relation relationToPatch) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAndAndDelete(relationToPatch.getId() ,new ArrayList<>( Arrays.asList(fileLabel , userLabel)) , false);
        System.out.println("before null check");
        if(relation==null)return null;
        System.out.println("after null check");

        if (relationToPatch.getRelationName()!= null){
            relation.getRelaUnit().setRelationName( relationToPatch.getRelationName() );
        }
        if(relationToPatch.getProperties() != null ){
            relation.getRelaUnit().setProperties( relationToPatch.getProperties() );
        }

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public Relation putRelation(String userLabel, String fileLabel, Relation relationToPut) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAndAndDelete(relationToPut.getId() ,new ArrayList<>( Arrays.asList(fileLabel , userLabel)),false);

        if(relation==null)return null;

        relation.getRelaUnit().setProperties(relationToPut.getProperties());
        relation.getRelaUnit().setRelationName(relationToPut.getRelationName());

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public Relation insertRelation(String userLabel, String fileLabel, RelationWarp relationToInsert) {
        Relation relation = relationRepository.createEmptyRelationByIds(relationToInsert.getStartId() , relationToInsert.getEndId());

        if(relation==null)return null;

        relation.setRelationName(relationToInsert.getRelaUnit().getRelationName());
        relation.setProperties(relationToInsert.getRelaUnit().getProperties());
        relation.setDelete(false);

        return relationRepository.save(relation);
    }

    @Override
    public Relation deleteRelation(String userLabel, String fileLabel, Long relationId) {
        RelationWarp relation = relationRepository.findByIdAndLabelsAndAndDelete(relationId,new ArrayList<>( Arrays.asList(fileLabel , userLabel)),false);

        if(relation==null)return null;

        relation.getRelaUnit().setDelete(true);

        return relationRepository.save(relation.getRelaUnit());
    }

    @Override
    public RelationWarp getRelation(String userLabel, String fileLabel, Long relationId) {
        return relationRepository.findByIdAndLabelsAndAndDelete(relationId,new ArrayList<>( Arrays.asList(fileLabel , userLabel)),false);
    }
}
