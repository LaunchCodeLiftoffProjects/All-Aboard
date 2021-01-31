package com.liftoff.allaboard.models;

import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User  extends AbstractEntity{


//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;


    private String username;
    private String pw_hash;
    private String email;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private Integer zipCode;
    private String role;
    private boolean enabled;
    private String userRole;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User(String username, String password, String email, String addressLineOne, String addressLineTwo, String city, String state, Integer zipCode, String role, String userRole) {
        this.username = username;
        this.pw_hash = encoder.encode(password);
        this.email = email;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.role = role;
        this.userRole = userRole;
    }

    public User findByUserName(User username) { return username; }


    public String getUsername() {
        return this.username;
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

    public void setRole(String role) {this.role = role;}

    public String getUserRole(){return userRole;}

    public void setUserRole(String userRole) {this.userRole = userRole;}







    @Override
    public String toString() {
        return this.username;
    }


    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pw_hash);
    }


    @Override
    public String getRole() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.pw_hash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }


    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

