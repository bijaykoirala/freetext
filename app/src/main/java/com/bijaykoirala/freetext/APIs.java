package com.bijaykoirala.freetext;

/**
 * Created by Macbook on 3/24/15.
 */
public class APIs {

    public static String getURL(String sid){
        return "https://api.twilio.com/2010-04-01/Accounts/" +
                sid +
                "/Messages.json";
    }
}
