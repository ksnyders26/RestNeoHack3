package com.travelport.restneohack.model.repositories;

import com.travelport.restneohack.model.domain.Traveler;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface TravelerRepository extends GraphRepository<Traveler>{


	Traveler findByEmailAddress(String emailAddress);
        

}