package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String company;
    private final String address;

    public ContactData(String firstname, String lastname, String company, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }
}
