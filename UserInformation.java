package com.example.sanyam.ftt;

/**
 * Created by Sanyam on 26-08-2017.
 */
public class UserInformation
{
    public String USERNAME;
    public String ADDRESS;
    public String EMAIL;
    public String MOBILE_NO;

    public UserInformation(){

    }

    public UserInformation(String name, String address,String email,String mobileNo) {
        this.USERNAME = name;
        this.ADDRESS = address;
        this.EMAIL=email;
        this.MOBILE_NO= mobileNo;
    }
}
