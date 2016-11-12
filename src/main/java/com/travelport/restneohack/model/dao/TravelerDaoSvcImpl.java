package com.travelport.restneohack.model.dao;


import com.travelport.restneohack.model.domain.Account;
import com.travelport.restneohack.model.domain.AccountView;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

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
        traveler.addAccount(account);
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
    
    public void persistAccountView(AccountView accountView) {
        template.save(accountView);
    }
    
    public void persistTravelertoDb(Traveler traveler, Set<Address> addresses, Set<FormOfPayment> formsOfPayment) {
        
   
        
        for (Address travelerAddress: addresses){
            traveler.addAddress(travelerAddress);
        }

        
        
        for (FormOfPayment fop: formsOfPayment) {
            traveler.addFormOfPayment(fop);
            //template.save(fop);
        }
        template.save(traveler);
        
        
//        AccountView accountView = new AccountView(traveler);
//        //accountView.setName(null);
//        accountView.add;
        
 
        
//        template.save(accountView);
        
       //find addresses from db and serialize to shipping/billing address 

    }

	public Traveler updateTraveler(Traveler traveler) {
		return travelerRepository.save(traveler);
	}
}
