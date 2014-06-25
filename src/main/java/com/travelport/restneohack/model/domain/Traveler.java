package com.travelport.restneohack.model.domain;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.util.Assert;

@NodeEntity
public class Traveler {

    private final static String HAS_ADDRESS = "ADDRESS";

    private final static String HAS_ACCOUNT = "ACCOUNT";

    private final static String HAS_FOP = "FOP";

    private String firstName, lastName;

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private String emailAddress;

    @Fetch
    @RelatedTo(elementClass = Address.class, type = HAS_ADDRESS)
    private Set<Address> addresses = new HashSet<Address>();

    @Fetch
    @RelatedTo(elementClass = FormOfPayment.class, type = HAS_FOP)
    private Set<FormOfPayment> formsOfPayment = new HashSet<FormOfPayment>();

    @Fetch
    @RelatedTo(elementClass = Account.class, type = HAS_ACCOUNT)
    private Set<Account> accounts = new HashSet<Account>();

    public Traveler() {

    }

    public Long getId() {
        return id;
    }

    public Traveler(String firstName, String lastName, String emailAddress) {

        Assert.hasText(firstName);
        Assert.hasText(lastName);
        Assert.hasText(emailAddress);

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public Set<Address> getAddresses() {
        return addresses;
        //	return Collections.unmodifiableSet(addresses);
    }

    public boolean hasAddress(Address address) {
        return addresses.contains(address);
    }

    public void addFormOfPayment(FormOfPayment formOfPayment) {
        formsOfPayment.add(formOfPayment);
    }

    public Set<FormOfPayment> getFormOfPaymentes() {
        return formsOfPayment;
        //	return Collections.unmodifiableSet(formsOfPayment);
    }

    public boolean hasFormOfPayment(FormOfPayment formOfPayment) {
        return formsOfPayment.contains(formOfPayment);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Set<Account> getAccountes() {
        return accounts;
        //	return Collections.unmodifiableSet(accountes);
    }

    public boolean hasAccount(Account account) {
        return accounts.contains(account);
    }

    @Override
    public String toString() {
    
        return String.format("%s %s <%s>", firstName, lastName, emailAddress);
    }
}
