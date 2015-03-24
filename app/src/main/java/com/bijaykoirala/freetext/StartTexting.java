package com.bijaykoirala.freetext;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class StartTexting extends Activity {

    EditText etMessage;
    TextView tvReport;
    ProgressBar pbWaiting;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeVariables();
    }

    private void initializeVariables() {
        data = new PreferenceManager().getValues(getApplicationContext());
        if (data == null){
            startActivity(new Intent(getApplicationContext(), SettingsAndMore.class));
        }
        else {
            setContentView(R.layout.activity_start_texting);
        }
        etMessage = (EditText) findViewById(R.id.et_text);
        tvReport = (TextView) findViewById(R.id.tv_report);
        pbWaiting = (ProgressBar) findViewById(R.id.pb_waiting);
    }

    public void send(View v) {
       if (new NetworkManager().isNetworkAvailable(getApplicationContext())) {
           if (etMessage.getText().toString().trim().length() > 1) {
               new SendMessageAT().execute(etMessage.getText().toString().trim());
           } else
               Toast.makeText(getApplicationContext(),
                       "Please type something and press send", Toast.LENGTH_SHORT)
                       .show();
       }
        else{
           Toast.makeText(getApplicationContext(), "No access to Twilio", Toast.LENGTH_SHORT).show();
       }
    }

    public void clear(View v) {
        tvReport.setText("Report");
        etMessage.setText("");
    }

    private class SendMessageAT extends AsyncTask<String, Void, Result> {

        @Override
        protected Result doInBackground(String... params) {
            return new NetworkManager().sendMessage(params[0], new PreferenceManager().getValues(getApplicationContext()));
        }

        @Override
        protected void onPostExecute(Result result) {
            pbWaiting.setVisibility(View.GONE);
            if (result!= null) {
                String report = "";
                report += "Status   :" + result.status + "\n";
                report += "From :" + result.from + "\n";
                report += "To   :" + result.to + "\n";
                if (result.errorMessage != null)
                    report += "Error Message  :" + result.errorMessage + "\n";
                report += "Body :" + result.body + "\n";

                tvReport.setText(report);
            }
            else{
                tvReport.setText("Your Twilio details must be incorrect");

            }

        }

        @Override
        protected void onPreExecute() {
            pbWaiting.setVisibility(View.VISIBLE);
            closeKeyboard();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showSettings(View v){
        startActivity(new Intent(this, SettingsAndMore.class));
    }

    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);
    }

}
