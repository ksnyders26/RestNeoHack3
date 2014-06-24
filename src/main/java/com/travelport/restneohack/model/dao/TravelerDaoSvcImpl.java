package com.travelport.restneohack.model.dao;


import com.travelport.restneohack.model.domain.Account;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.travelport.restneohack.model.domain.AccountView;
import com.travelport.restneohack.model.domain.Address;
import com.travelport.restneohack.model.domain.FormOfPayment;
import com.travelport.restneohack.model.domain.Traveler;
import com.travelport.restneohack.model.repositories.FormOfPaymentRepository;
import com.travelport.restneohack.model.repositories.TravelerRepository;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sheimmer
 */


@Service
@Transactional
public class TravelerDaoSvcImpl {    
    
    @Autowired
    private TravelerRepository travelerRepository;
    
    @Autowired
    private FormOfPaymentRepository formOfPaymentRepository;
    
    @Autowired
    private Neo4jTemplate template;
    

    public Traveler addAddress(Address address, Traveler traveler){
        traveler.addAddress(address);
        return travelerRepository.save(traveler);
    }
    
    public Traveler addAccount(Account account, Traveler traveler){
        traveler.setAccount(account);
        return travelerRepository.save(traveler);
    }
                
    public Traveler createTraveler(Traveler traveler){

        return travelerRepository.save(traveler);

    }
    
    public Traveler findOne(Long id){
        return travelerRepository.findOne(id);
    }
    
    public Traveler findByEmailAddress(String emailAddress){
        return travelerRepository.findByEmailAddress(emailAddress);
    }
    
    public Iterable<Traveler> getAllTravelers() {
        return travelerRepository.findAll();
    }
    
    public Traveler findTravelerById(Long id) {
		return travelerRepository.findOne(id);
	}
    
    public long getNumberOfTravelers() {
		return travelerRepository.count();
	}
    
    
    public void persistTravelertoDb(Traveler traveler, Set<Address> addresses, FormOfPayment FOP) {
        
        
        Traveler persistTraveler = new Traveler();
        
        
        for (Address travelerAddress: addresses){
       
            traveler.addAddress(travelerAddress);
        }

     //   System.out.println("traveler addresses = " + persistTraveler.getAddresses().);
        persistTraveler = template.save(traveler);
        
        template.save(FOP);
        
        AccountView accountView = new AccountView(persistTraveler);
        accountView.add(FOP,2);
        
        Iterator iter = addresses.iterator();
        Object address = iter.next();
        System.out.println("billing address = " + address.toString());
//        accountView.withBillingAddress((Address) address);
        
        template.save(accountView);
        
       //find addresses from db and serialize to shipping/billing address 

    }
}
