package com.example.demo.controller;

import com.example.demo.POJO.*;
import com.example.demo.configure.JwtUser;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RelaRepository;
import com.example.demo.repository.RelationRepository;
import com.example.demo.service.GraphService;
import com.example.demo.util.JwtTokenUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    RelationRepository relationRepository;

    @Autowired
    GraphService graphService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/test")
    public Relation greeting(@RequestBody RelationWarp relationWarp){
        Relation rel = relationRepository.createEmptyRelationByIds(relationWarp.getStartId() , relationWarp.getEndId());
        System.out.println("after insert empty relationship");
        rel.setProperties(relationWarp.getRelaUnit().getProperties());
        rel.setRelationName(relationWarp.getRelaUnit().getRelationName());
        System.out.println("before save relationship");

        Relation a = relationRepository.save(rel);
        System.out.println("after save relationship");

        return a;

//       return nodeRepository.findByLabelsAnd(new ArrayList<>(Arrays.asList("ALIEN","HUMAN")));
//        return nodeRepository.findall();
    }

    @GetMapping("/test")
    public String operateImage() throws IOException {
        Thumbnails.of("src\\main\\resources\\static\\image\\original.png")
//                .size(500,500)
                .sourceRegion(Positions.CENTER , 500,500)
//                .scale(1f)
                .size(50,50)
                .toFile("target.png");
        return "done";
    }


    /* node things */

    @GetMapping("/node/{fileId}")
    public List<Node> getFileNodes(@PathVariable String fileId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return graphService.getNodesByFile("U"+user.getId() , "F"+fileId);
    }

    @DeleteMapping("/node/{fileId}")
    public List<Node> deleteFile(@PathVariable String fileId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return graphService.deleteNodesByFile("U"+user.getId() , "F"+fileId);
    }

    @GetMapping("/node/{fileId}/{nodeId}")
    public Node getOneNode(@PathVariable String fileId , @PathVariable Long nodeId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return graphService.getNode("U"+user.getId() , "F"+fileId , nodeId);
    }

    @PatchMapping("/node/{fileId}/{nodeId}")
    public Node patchOneNode(@PathVariable String fileId , @PathVariable Long nodeId , @RequestBody Map<String , Object> mapToPatch){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mapToPatch.put("id" , nodeId);
        return graphService.patchNode("U"+user.getId() , "F"+fileId ,mapToPatch);
    }

    @PutMapping("/node/{fileId}/{nodeId}")
    public Node putOneNode(@PathVariable String fileId , @PathVariable Long nodeId , @RequestBody Node nodeToPut){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        nodeToPut.setId(nodeId);
        return graphService.putNode("U"+user.getId() , "F"+fileId , nodeToPut);
    }

    @PostMapping("/node/{fileId}")
    public Node insertOneNode(@PathVariable String fileId , @RequestBody Node nodeToInsert){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return graphService.insertNode("U"+user.getId() , "F"+fileId , nodeToInsert);
    }

    @DeleteMapping("/node/{fileId}/{nodeId}")
    public Node deleteOneNode(@PathVariable String fileId , @PathVariable Long nodeId ){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return graphService.deleteNode("U"+user.getId() , "F"+fileId ,nodeId);
    }



    /* relationship things */

    @GetMapping("/relation/{fileId}")
    public List<RelationWarp> getFileRelations(@PathVariable String fileId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return graphService.getRelationsByFile("U"+user.getId() , "F"+fileId);
    }

    @DeleteMapping("/relation/{fileId}")
    public List<RelationWarp> deleteFileRelations(@PathVariable String fileId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return graphService.deleteRelationsByFile("U"+user.getId() , "F"+fileId);
    }

    @GetMapping("/relation/{fileId}/{relationId}")
    public RelationWarp getOneRelation(@PathVariable String fileId , @PathVariable Long relationId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return graphService.getRelation("U"+user.getId() , "F"+fileId , relationId);
    }

    @DeleteMapping("/relation/{fileId}/{relationId}")
    public Relation deleteOneRelation(@PathVariable String fileId , @PathVariable Long relationId){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return graphService.deleteRelation("U"+user.getId() , "F"+fileId , relationId);
    }

    @PostMapping("/relation/{fileId}")
    public Relation insertOneRelation(@PathVariable String fileId ,@RequestBody RelationWarp relationToInsert){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return graphService.insertRelation("U"+user.getId() , "F"+fileId , relationToInsert);
    }

    @PatchMapping("/relation/{fileId}/{relationId}")
    public Relation patchOneRelation(@PathVariable String fileId , @PathVariable Long relationId , @RequestBody Map<String , Object>mapToPatch){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mapToPatch.put("id" , relationId);

        return graphService.patchRelation("U"+user.getId() , "F"+fileId , mapToPatch);
    }


    @PutMapping("/relation/{fileId}/{relationId}")
    public Relation putOneRelation(@PathVariable String fileId , @PathVariable Long relationId , @RequestBody Relation relationToPut){
        JwtUser user =  (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        relationToPut.setId(relationId);

        return graphService.putRelation("U"+user.getId() , "F"+fileId , relationToPut);
    }



}
