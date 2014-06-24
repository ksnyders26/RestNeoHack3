package com.travelport.restneohack.model.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import org.springframework.util.Assert;

@RelationshipEntity(type = "PAYMENT_TYPE")
public class PaymentType {

    @GraphId
    private Long id;
    
    @StartNode
    private AccountView accountView;
    
    @Fetch
    @EndNode
    private FormOfPayment formOfPayment;


    public PaymentType(AccountView accountView, FormOfPayment formOfPayment) {
    Assert.notNull(formOfPayment);
    Assert.notNull(accountView);

    this.accountView = accountView;
	this.formOfPayment = formOfPayment;
    }

    public PaymentType() {

    }
    
    public Long getId() {
    	return id;
    }

    public FormOfPayment getFormOfPayment() {
	return formOfPayment;
    }

    public AccountView getOrder() {
        return accountView;
    }


}
