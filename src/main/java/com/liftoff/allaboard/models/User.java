package com.liftoff.allaboard.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @ManyToMany
    private final List<GameGroup> gameGroup = new ArrayList<>();

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

    private String addressLineTwo;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "This field cannot be left blank!")
    @Size(max = 2)
    private String state;

    @NotNull
    @Positive
    private Integer zipCode;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(String username, String password, String email, String addressLineOne, String addressLineTwo, String city, String state, Integer zipCode) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.email = email;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public List<GameGroup> getGameGroup() {
        return gameGroup;
    }

    public void addGameGroup(GameGroup gameGroup) {
        this.gameGroup.add(gameGroup);
    }

    @Override
    public String toString() {
        return username;
    }


    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


}

