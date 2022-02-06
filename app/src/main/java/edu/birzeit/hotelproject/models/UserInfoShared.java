package edu.birzeit.hotelproject.models;

public class UserInfoShared {
    private static final String USERNAME = "USERNAME";
    private static final String CREDIT_CARD = "CREDIT_CARD";
    private static final String PASSWORD = "PASSWORD";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String CUSTOMER_PREF = "CUSTOMER_PREF";
    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String USER_CHECK_SIGN_PREF = "USER_CHECK_SIGN_PREF";
    private static final String USER_CHECK_SIGN = "USER_CHECK_SIGN";
    public UserInfoShared() {}

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getCreditCard() {
        return CREDIT_CARD;
    }

    public String getPASSWORD() {
        return PASSWORD;
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

    public String getUserCheckSign() {
        return USER_CHECK_SIGN;
    }

    public static String getUserCheckSignPref() {
        return USER_CHECK_SIGN_PREF;
    }
}

