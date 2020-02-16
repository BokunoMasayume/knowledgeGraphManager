package com.example.demo.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.POJO.Person;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(collectionResourceRel = "people", path="people")
@Repository
public interface PersonRepository extends Neo4jRepository<Person , Long> {

    @Query("Match (p:Person) return p")
    List<Person> findByfileId(int fileId, int userId);


    Person findById(long i);
}
