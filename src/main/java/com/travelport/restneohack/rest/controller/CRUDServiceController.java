package com.travelport.restneohack.rest.controller;

import com.travelport.restneohack.model.dao.AccountDaoSvcImpl;
import com.travelport.restneohack.model.dao.TravelerDaoSvcImpl;
import com.travelport.restneohack.model.domain.Account;
import com.travelport.restneohack.model.domain.Address;
import com.travelport.restneohack.model.domain.Country;
import com.travelport.restneohack.model.domain.Traveler;
import com.travelport.restneohack.model.json.AccountJSON;
import com.travelport.restneohack.model.json.TravelerJSON;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/rest")
public class CRUDServiceController {

    private static final String APPLICATION_JSON = "application/json";
    @Autowired
    private AccountDaoSvcImpl actDaoImpl;
    @Autowired
    private TravelerDaoSvcImpl trvDaoImpl;

    @RequestMapping(value = "/saveAccount", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public AccountJSON storeRecord(@RequestBody AccountJSON account) throws Throwable {
        if (account == null) {
            throw new RuntimeException("Request cannot be empty");
        }

        Account accountDB = actDaoImpl.createAccount(new Account(account.getName(), account.getEmail()));

        account.setId(String.valueOf(accountDB.getId()));

        System.out.println("Account email  = " + accountDB.getEmailAddress());
        return account;
    }

    @RequestMapping(value = "/searchAccount/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public AccountJSON retrieveRecord(@PathVariable(value = "id") String id) throws Throwable {
        Long idLong = Long.valueOf(id);
        return mapAccount(actDaoImpl.findAccountById(idLong));
    }

    private AccountJSON mapAccount(Account account) {

        AccountJSON acct = new AccountJSON();
        if (account != null) {
            acct.setId(account.getId().toString());
            acct.setEmail(account.getEmailAddress());
            acct.setName(account.getName());
        }
        return acct;
    }
    
    
    @RequestMapping(value = "/saveTraveler", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public TravelerJSON storeRecord(@RequestBody TravelerJSON trv) throws Throwable {
        if (trv == null) {
            throw new RuntimeException("Request cannot be empty");
        }

        Traveler traveler = new Traveler(trv.getFirstName(), trv.getLastName(), trv.getEmail());
          if (trv.getStreet() != null) {
            String street = trv.getStreet();
            String city = trv.getCity();
            String countryStr = trv.getCountry();
            Country country = null;
            if ((countryStr!= null) && (countryStr.equals("SP"))) {
                country = new Country("SP", "Spain");
            }
            else {
                country = new Country("US", "United Stats of America");
            }
            Address addr = new Address(street, city, country);
            traveler.addAddress(addr);
        }
        if (trv.getAccount() != null){
            Long idLong = Long.valueOf(trv.getAccount());
            traveler.setAccount(actDaoImpl.findAccountById(idLong));
        }
        Traveler travelerDB = trvDaoImpl.createTraveler(traveler);
      
        trv.setId(String.valueOf(travelerDB.getId()));

        System.out.println("Traveler email  = " + travelerDB.getEmailAddress());
        return trv;
    }

    @RequestMapping(value = "/searchTraveler/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public TravelerJSON retrieveTraveler(@PathVariable(value = "id") String id) throws Throwable {
        Long idLong = Long.valueOf(id);
        return mapTraveler(trvDaoImpl.findTravelerById(idLong));
    }
    
    @RequestMapping(value = "/retrieveTraveler/{email}", method = RequestMethod.GET, produces = APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public TravelerJSON retrieveTravelerByEmail(@PathVariable(value = "email") String email) throws Throwable {
       
        return mapTraveler(trvDaoImpl.findByEmailAddress(email));
    }

    
    private TravelerJSON mapTraveler(Traveler traveler) {

        TravelerJSON trv = new TravelerJSON();
        if (traveler != null) {
            trv.setId(traveler.getId().toString());
            trv.setEmail(traveler.getEmailAddress());
            trv.setFirstName(traveler.getFirstName());
            trv.setLastName(traveler.getLastName());
            
            if (traveler.getAddresses() != null){
                Set <Address> addresses = traveler.getAddresses();
                for (Address travelerAddress : addresses) {

                    trv.setStreet(travelerAddress.getStreet());
                    trv.setCity(travelerAddress.getCity());
                    if (travelerAddress.getCountry() != null){
                        Country country = travelerAddress.getCountry();
                        trv.setCountry(country.getName());
                    }
                    
                }
                
            }
        }
        return trv;
    }
}
