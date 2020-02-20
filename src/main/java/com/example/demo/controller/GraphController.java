package com.example.demo.controller;

import com.example.demo.POJO.*;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RelaRepository;
import com.example.demo.repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RelaRepository relaRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    RelationRepository relationRepository;

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

    @Transactional
    @GetMapping("/relation")
    public List<RelationWarp> getRela(@RequestParam("fileId") String fileId){
        System.out.println(fileId);
        return relaRepository.findByfileLabel(fileId);
    }

    @Transactional
    @PutMapping("/relation")
    public Rela createRela(@RequestBody Rela rela){
//        Person startp = personRepository.findById(17);
//        Person endp = personRepository.findById(2);
//        rela.setEndp(endp);
//        rela.setStartp(startp);
//        System.out.println("hhhhhhhhhh");
//
//        System.out.println(rela.getRelationName());

        Rela rela1 = relaRepository.findById((long)5);
        System.out.println(rela1.getRelationName());

        rela.setRelationshipId(rela1.getRelationshipId());
//        rela.setStartp(rela1.getStartp());
//        rela.setEndp((rela1.getEndp()));

        rela1.setProperties(rela.getProperties());
        System.out.println(rela.getRelationshipId());


        return relaRepository.save(rela1);
    }
}
