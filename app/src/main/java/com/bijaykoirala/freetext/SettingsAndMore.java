package com.bijaykoirala.freetext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Macbook on 3/24/15.
 */
public class SettingsAndMore extends Activity {
    EditText etFrom, etTo, etSID, etToken;
    String from="", to="", sid="", token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_and_more);
        intializeVariables();
        setValues();

    }

    private void setValues() {
        Data data = new PreferenceManager().getValues(getApplicationContext());
        if (data != null) {
            if (data.from != null && data.to != null && data.sid != null && data.token != null) {
                etFrom.setText(data.from);
                etTo.setText(data.to);
                etSID.setText(data.sid);
                etToken.setText(data.token);
            }
        }
    }

    private void intializeVariables() {
        etFrom = (EditText)findViewById(R.id.et_twilio_number);
        etTo = (EditText)findViewById(R.id.et_your_number);
        etSID = (EditText)findViewById(R.id.et_sid_number);
        etToken = (EditText)findViewById(R.id.et_auth_token);

    }

    public void saveValues(View v){
        from = etFrom.getText().toString().trim();
        to = etTo.getText().toString().trim();
        sid = etSID.getText().toString().trim();
        token = etToken.getText().toString().trim();

        if (check(etFrom) && check(etTo) && check(etSID) && check(etToken)){
            Data data = new Data();
            data.from = from;
            data.to = to;
            data.sid = sid;
            data.token = token;

            new PreferenceManager().setValues(getApplicationContext(), data);
            startActivity(new Intent(SettingsAndMore.this, StartTexting.class));
            finish();
            }
        else{
            Toast.makeText(getApplicationContext(), "None of the values can be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendEmail(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "bijay@bijaykoirala.com.np", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FREE_TEXT_USER");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public boolean check(EditText e){
        if (e.getText().toString().trim().length() < 1){
            return false;
        }
        else{
            return true;
        }

    }
}
