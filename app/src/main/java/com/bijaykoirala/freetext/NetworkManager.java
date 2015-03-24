package com.bijaykoirala.freetext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Macbook on 3/24/15.
 */
public class NetworkManager {

    /**
     * This function handles all the part of sending the message, it first creates a header encoding the SID and TOKEN
     * Then it will add in the body of the message, sender and the receiver to create an http post entity which will be posted
     * to the links mentioned in the APIs page
     * @param messageBody
     * @return the response given by the twilio server
     *
     */
    public Result sendMessage(String messageBody, Data data) {

        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost();

            URI uri = new URI(new APIs().getURL(data.sid));
            httpPost.setURI(uri);
            httpPost.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials(data.sid, data.token),
                    HTTP.UTF_8, false));

            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("To", data.to));
            postParameters.add(new BasicNameValuePair("From", data.from));
            postParameters.add(new BasicNameValuePair("Body", messageBody));

            httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String readLine = bufferedReader.readLine();
            //tvReport.setText(readLine);
            while (readLine != null) {
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            Log.d("Freetext", "Exception was catched");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                }
            }
        }

        Result result = new Result();

        try {
            JSONObject jObject = new JSONObject(stringBuffer.toString());
            result.body = jObject.getString("body");
            result.status = jObject.getString("status");
            result.from = jObject.getString("from");
            result.to = jObject.getString("to");
            result.errorMessage = jObject.getString("error_message");
        }
        catch (JSONException exception){
            return null;
        }
        return result;

    }

    /**
     * This method returns a boolean true if network is available
     * @param context the activity from where this method is called
     * @return true if we have internet, false else
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

