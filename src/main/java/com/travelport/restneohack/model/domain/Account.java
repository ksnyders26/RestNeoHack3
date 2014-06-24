package com.travelport.restneohack.model.domain;


import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.util.Assert;

@NodeEntity
public class Account {

    private String name;
    @GraphId
    private Long id;
    @Indexed(unique = true)
    private String emailAddress;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public Account(String name, String emailAddress) {

        Assert.hasText(name);
        Assert.hasText(emailAddress);

        this.name = name;
        this.emailAddress = emailAddress;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    @Override
    public String toString() {
     
        return String.format("%s %s ", name, emailAddress);
    }
}
