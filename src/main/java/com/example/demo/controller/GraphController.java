package com.example.demo.controller;

import com.example.demo.POJO.Person;
import com.example.demo.POJO.Rela;
import com.example.demo.POJO.Relation;
import com.example.demo.POJO.RelationWarp;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.RelaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/graph")
public class GraphController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RelaRepository relaRepository;

    @GetMapping("/nodes/{name}")
    @Transactional
    public Iterable<Person> greeting(@PathVariable("name")String name){

       return personRepository.findAll();
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
