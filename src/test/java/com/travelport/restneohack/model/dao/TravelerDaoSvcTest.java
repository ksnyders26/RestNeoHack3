package com.travelport.restneohack.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.travelport.restneohack.model.domain.Address;
import com.travelport.restneohack.model.domain.Country;
import com.travelport.restneohack.model.domain.EmailAddress;
import com.travelport.restneohack.model.domain.FormOfPayment;
import com.travelport.restneohack.model.domain.Traveler;
import com.travelport.restneohack.model.repositories.TravelerRepository;
import java.util.Collection;
import java.util.HashSet;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath:/spring/ApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false) //turn this to true to rollback any changes and unpopulate the db
@Transactional
public class TravelerDaoSvcTest {

    @Autowired
    private TravelerRepository travelerRepository;
    @Autowired
    private Neo4jTemplate template;
    @Autowired
    TravelerDaoSvcImpl dataImpl;

    @Rollback(false)
    @BeforeTransaction
    public void refreshDB() {
        Neo4jHelper.cleanDb(template);
        makeSomeTravelers();
    }

    @Test
    public void shouldFindTravelersById() {
       // dataImpl.makeSomeTravelers();
        for (Traveler traveler : dataImpl.getAllTravelers()) {
            Traveler foundTraveler = dataImpl.findTravelerById(traveler.getId());
            assertNotNull(foundTraveler);
        }
    }

    @Test
    public void shouldFindTravelerbyEmailAddress() {
        //dataImpl.makeSomeTravelers();

        for (Traveler traveler : dataImpl.getAllTravelers()) {
            Traveler foundTraveler = dataImpl.findByEmailAddress(traveler.getEmailAddress());
            Set<Address> addresses = foundTraveler.getAddresses();
            System.out.println("Size of set = " + addresses.size());
            List<Address> addressList = new ArrayList<Address>(addresses);
            for (int i = 0; i < addressList.size(); i++) {
                System.out.println(addressList.get(i).getStreet() + " " + addressList.get(i).getCity());
            }

            assertNotNull(foundTraveler); 
       }
    }

//    @Test
//    public void persistTravelertoDb() {
//        Set<Address> addresses = new HashSet<>();
//
//        Traveler hanSolo = new Traveler("Han", "Solo", "hanSolo@Rebels.com");
//        Country usa = new Country("US", "Unisted States of America");
//        Address billingAddress = new Address("27 Broadway", "New York", usa);
//        Address shippingAddress = new Address("2250 Battery Park ln", "New York", usa);
//        addresses.add(billingAddress);
//        addresses.add(shippingAddress);
//        FormOfPayment fop = new FormOfPayment("Visa", "5526-5584-8856-9985");
//
//        dataImpl.persistTravelertoDb(hanSolo, addresses, fop);
//
//        Traveler foundTraveler = dataImpl.findByEmailAddress("hanSolo@Rebels.com");
//
//        assertNotNull(foundTraveler);
//
//
//    }
    
        public void makeSomeTravelers(){
        Set<Address> addresses = new HashSet<>();
            Country usa = new Country ("US","United States of America");
            Country russia = new Country ("RU","Russia");
            Country uk = new Country ("UK", "United Kingdom");
        
            Traveler hanSolo = new Traveler ("Han", "Solo", "hanSolo@Rebels.com");
            Address hanBillingAddress = new Address("27 Broadway", "Brooklyn, NY", usa);
            Address hanShippingAddress = new Address("2250 Battery Park ln", "New York", usa);
            addresses.add(hanBillingAddress);
            addresses.add(hanShippingAddress);
            FormOfPayment hanFop = new FormOfPayment("Visa", "5526-5584-8856-9985");

            dataImpl.persistTravelertoDb(hanSolo, addresses, hanFop);
            addresses.clear();
            
            Traveler chewy  = new Traveler ("Chew", "bacca", "Chewbacca@Rebels.com");
            Address chewyBillingAddress = new Address("2525 Qualious ln.", "New York, NY", usa);
            addresses.add(chewyBillingAddress);
            FormOfPayment chewyFop = new FormOfPayment("Visa", "5524-5555-8888-9987");

            dataImpl.persistTravelertoDb(chewy, addresses, chewyFop);         
            addresses.clear();
            
            Traveler darthVader = new Traveler ("Darth", "Vader", "darthvader@Empire.com");
            Address vaderBillingAddress = new Address("2215 Delta rd", "Bakersville, CA", usa);
            Address vaderShippingAddress = new Address("2250 Delta rd", "Bakersville, CA", usa);
            addresses.add(vaderBillingAddress);
            addresses.add(vaderShippingAddress);
            FormOfPayment vaderFop1 = new FormOfPayment("Visa", "3212-6698-8874-8852");

            dataImpl.persistTravelertoDb(darthVader, addresses, vaderFop1);
            addresses.clear();
        
            Traveler obiWan = new Traveler ("Obi-Wan ", "Kenobi", "obiKenobi@JediAcademy.com");
            Address obiWanBillingAddress = new Address("2258 tatooine pl.", "Tampa Bay, FL", usa);
            Address obiWanShippingAddress = new Address("778545 Skyridge rd", "Tampa Bay, FL", usa);
            addresses.add(obiWanBillingAddress);
            addresses.add(obiWanShippingAddress);
            FormOfPayment obiWanFop = new FormOfPayment("Discover", "7777-8888-9987-5412");
            
            dataImpl.persistTravelertoDb(obiWan, addresses, obiWanFop);
            addresses.clear();
            
            Traveler luke = new Traveler ("Luke", "Skywalker", "skywalker@RebelAlliance.com");
            Address lukeBillingAddress = new Address("2152 Sunnyville Wy.", "Denver, CO", usa);
            Address lukeShippingAddress = new Address("PO Box 2254 Battery Park ln", "New York, NY", usa);
            addresses.add(lukeBillingAddress);
            addresses.add(lukeShippingAddress);
            FormOfPayment lukeFop = new FormOfPayment("Visa", "2252-6646-5522-2222");

            dataImpl.persistTravelertoDb(luke, addresses, lukeFop);
            addresses.clear();
            
            Traveler peter = new Traveler ("Peter", "Parker", "Spiderman@webSlinging.com");
            Address peterBillingAddress = new Address("85245 Queens rd.", "New York, NY", usa);
            addresses.add(peterBillingAddress);
            FormOfPayment peterFop = new FormOfPayment("American Express", "4444-5555-6666-9999");

            dataImpl.persistTravelertoDb(peter, addresses, peterFop);
            addresses.clear();
            
            Traveler bucky = new Traveler ("James", "Barnes", "wintersoldier@AIM.com");
            Address buckyBillingAddress = new Address("4456 Gogolevskiy b-r", "Moscow", russia);
            Address buckyShippingAddress = new Address("54123 Manezhnaya ul.", "Moscow", russia);
            addresses.add(buckyBillingAddress);
            addresses.add(buckyShippingAddress);
            FormOfPayment buckyFop = new FormOfPayment("Visa", "5555-5555-5555-5555");

            dataImpl.persistTravelertoDb(bucky, addresses, buckyFop);
            addresses.clear();
            
            Traveler steve = new Traveler ("Steve", "Rogers", "captainAmerica@shield.com");
            Address steveBillingAddress = new Address("55231 Marigold way", "Brooklyn, NY", usa);
            Address steveShippingAddress = new Address("4421 Freedom lane", "Washington D.C.", usa);
            addresses.add(steveBillingAddress);
            addresses.add(steveShippingAddress);
            FormOfPayment steveFop = new FormOfPayment("American Express Black", "9989-9632-5547-8521");

            dataImpl.persistTravelertoDb(steve, addresses, steveFop);
            addresses.clear();
            
            Traveler jackie = new Traveler ("Jacqueline", "Falsworth", "spitfire@earth-616.com");
            Address jackieBillingAddress = new Address("22156 Charles St", "London", uk);
            addresses.add(jackieBillingAddress);
            FormOfPayment jackieFop = new FormOfPayment("Discover", "4456-5521-8878-9874");

            dataImpl.persistTravelertoDb(jackie, addresses, jackieFop);
            addresses.clear();
            
            long numOfTravelers = dataImpl.getNumberOfTravelers();
            assertEquals(9,numOfTravelers);
    }
}
