package edu.birzeit.hotelproject.models;

public class UserInfoShared {
    private static final String USERNAME = "USERNAME";
    private static final String CREDIT_CARD = "CREDIT_CARD";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String CUSTOMER_PREF = "CUSTOMER_PREF";
    private static final String CUSTOMER_ID = "CUSTOMER_ID";

    public UserInfoShared() {}

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getCreditCard() {
        return CREDIT_CARD;
    }

    public String getPhoneNumber() {
        return PHONE_NUMBER;
    }

    public String getCustomerPref() {
        return CUSTOMER_PREF;
    }

    public String getCustomerId() {
        return CUSTOMER_ID;
    }
}

