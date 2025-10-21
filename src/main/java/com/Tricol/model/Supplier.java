package com.Tricol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Supplier {
    @Id
    @GeneratedValue
    private UUID id;
    private String company;
    private String address;
    private String Contact;
    private String email;
    private String phone;
    private String city;
    private int ice;

    public Supplier(String company, String address, String contact, String email, String phone, String city, int ice) {
        this.company = company;
        this.address = address;
        Contact = contact;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.ice = ice;
    }
    public Supplier() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }
}
