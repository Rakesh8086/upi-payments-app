package com.upi.upi_payments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import com.upi.upi_payments.entity.UserType;

// data transfor object class is exposed in the frontend for getting user input
// do not expose the entity fields like id to the frontend user
// so use a separate class to get input from frontend

public class UserRegistrationDTO{
    @NotBlank(message = "User name cannot be blank")
    private String userName;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "User type is required")
    private UserType userType;

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType(){
        return userType;
    }
    public void setUserType(UserType userType){
        this.userType = userType;
    }
}
