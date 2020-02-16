package com.example.demo.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Document
@CompoundIndexes(
        {
                @CompoundIndex(name="for_full_name" , unique = true , def = "{'firstName':1 , 'lastName':1}")
        }
)
public class Customer {
//    @Id
    @MongoId
    private String id;

    private String firstName;

    private String lastName;

    private Map<String , KGMod> temp;

    public Map<String, KGMod> getTemp() {
        return temp;
    }

    public void setTemp(Map<String, KGMod> temp) {
        this.temp = temp;
    }

    //    public void setId(ObjectId id) {
//        this.id = id;
//    }
//
//    public ObjectId getId() {
//        return id;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
