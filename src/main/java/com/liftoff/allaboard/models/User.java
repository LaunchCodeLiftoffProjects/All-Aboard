package com.liftoff.allaboard.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 150)
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 50)
    private String addressLineOne;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 50)
    private String addressLineTwo;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 2)
    private String state;


    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 5)
    private String zipCode;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


}

