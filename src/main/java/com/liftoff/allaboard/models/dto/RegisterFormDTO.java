package com.liftoff.allaboard.models.dto;

import org.springframework.context.annotation.Role;

import javax.validation.constraints.*;

public class RegisterFormDTO extends LoginFormDTO {

//    private String verifyPassword;

    @NotNull
    @NotBlank
    @Email(message = "Please enter a valid email")
    private String email;

    @NotNull
    @NotBlank(message = "This field can not be left blank!")
    @Size(min = 5, max = 50, message = "Please enter an address between 5 and 50 characters.")
    private String addressLineOne;


    private String addressLineTwo;

    @NotNull
    @NotBlank(message = "This field can not be left blank!")
    @Size(min = 3, max = 100, message = "Invalid city. Must be between 3 and 100 characters.")
    private String city;

    @NotNull
    @NotBlank(message = "This field can not be left blank!")
    @Size(min = 2, max = 2, message = "Please enter 2 letter state abbreviation.")
    private String state;

    @NotNull
    @Positive(message = "This field can not be a negative number")
    private Integer zipCode;

    private String userRole;
    private String role;
    private String verifyPassword;


    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
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

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}
}