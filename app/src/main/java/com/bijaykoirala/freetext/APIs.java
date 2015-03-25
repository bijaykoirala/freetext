package com.bijaykoirala.freetext;

import android.util.Log;

/**
 * Created by Macbook on 3/24/15.
 */
public class APIs {

    public static String getURL(String sid){
        String url =  "https://api.twilio.com/2010-04-01/Accounts/" +
                sid +
                "/Messages.json";
        Log.d("FreeText", url);
        return url;
    }
}
