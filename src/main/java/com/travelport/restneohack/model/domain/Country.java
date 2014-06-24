package com.travelport.restneohack.model.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Country {
    
    @GraphId
    private Long id;

    @Indexed(unique=true) String code;
    String name;
    
    public Long getId() {
    	return id;
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Country() {
    }
}