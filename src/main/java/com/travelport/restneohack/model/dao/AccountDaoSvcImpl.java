package com.travelport.restneohack.model.dao;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.travelport.restneohack.model.domain.Account;
import com.travelport.restneohack.model.repositories.AccountRepository;

import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class AccountDaoSvcImpl {    
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    @Autowired
    private Neo4jTemplate template;
                
    public Account createAccount(Account account){
        //return accountRepository.save(new Account(firstName, lastName, emailAddress));
        System.out.println("Account = " + account.toString());
        String firstName=account.getName();
        String emailAddress = account.getEmailAddress();
    //    return worldRepository.save(new World(name, moons));
        return accountRepository.save(new Account(firstName, emailAddress));
            
    }
    
    public Account findOne(Long id){
        return accountRepository.findOne(id);
    }
    
    public Account findByEmailAddress(String emailAddress){
        return accountRepository.findByEmailAddress(emailAddress);
    }
    
    public Iterable<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    
    public Account findAccountById(Long id) {
		return accountRepository.findOne(id);
	}
    
    public long getNumberOfAccounts() {
		return accountRepository.count();
	}
    
    public Collection<Account> makeSomeAccounts(){
        Collection<Account> accounts = new ArrayList<Account>();
     
        Account accountCloudCity = new Account ("Cloud City", "CloudCity@alliance.com");
       
        Account accountTatooine = new Account ("Tatooine", "tooine@JediAcademy.com");

        
        //Create accounts in graphdb and add accounts to list
        accounts.add(createAccount(accountCloudCity));
        accounts.add(createAccount(accountTatooine));


        
        return accounts;
    }
    
    public Account persistAccounttoDd(Account account) {
        
        
        Account persistAccount = template.save(account);
        
        return persistAccount;
    }
}
