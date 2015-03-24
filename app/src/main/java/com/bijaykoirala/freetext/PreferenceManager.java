package com.bijaykoirala.freetext;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Macbook on 3/24/15.
 */
public class PreferenceManager {

    public String FROM = "com.bijaykoirala.freetext.from";
    public String TO = "com.bijaykoirala.freetext.to";
    public String ACCOUNT_SID = "com.bijaykoirala.freetext.sid";
    public String AUTH_TOKEN = "com.bijaykoirala.freetext.token";
    public String NAME_PREFS = "bijaykoirala.com.np";

    public void setValues(Context context, Data data) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FROM, data.from);
        editor.putString(TO, data.to);
        editor.putString(ACCOUNT_SID, data.sid);
        editor.putString(AUTH_TOKEN, data.token);
        Log.d("Set from, to, sid and token", data.from+", "+data.to+", "+data.sid+", "+data.token);
        editor.commit();
    }

    public Data getValues(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFS, context.MODE_PRIVATE);

        String from = sp.getString(FROM, null);
        String to = sp.getString(TO, null);
        String sid = sp.getString(ACCOUNT_SID, null);
        String token = sp.getString(AUTH_TOKEN, null);

        if (from == null && to == null && sid == null && token == null ){
            return null;
        }
        else{
            Data data = new Data();
            data.from = from;
            data.to = to;
            data.sid = sid;
            data.token = token;
            Log.d("Getting from, to, sid and token", data.from+", "+data.to+", "+data.sid+", "+data.token);
            return data;

        }
    }
}
