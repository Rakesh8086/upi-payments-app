package com.upi.upi_payments.dto;

import com.upi.upi_payments.entity.UserType;

// data transfor object class is exposed in the frontend for getting user input
// do not expose the entity fields like id to the frontend user
// so use a separate class to get input from frontend

public class UserRegistrationDTO{
    private String userName;
    private String phoneNumber;
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
