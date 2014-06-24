package com.travelport.restneohack.model.repositories;

import com.travelport.restneohack.model.domain.Account;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface AccountRepository extends GraphRepository<Account>{


	Account findByEmailAddress(String emailAddress);
        

}