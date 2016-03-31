package com.icore.jsonpost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String LOGGER_TAG = "TAG";
    public static final String dev_Url= "http://intranet.cytrion.com/Private/External/Releases/helpie/V2.0_TEST/public/webservice/";

    EditText edt_username;
    EditText edt_password;
    TextView tv;
    Button btnLogin;
    String username;
    String password;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        edt_username=(EditText)findViewById(R.id.edt_username);
        edt_password=(EditText)findViewById(R.id.edt_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        tv=(TextView)findViewById(R.id.tv_response);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=edt_username.getText().toString();
                password=edt_password.getText().toString();

                new ExampleTask().execute();
            }
        });
    }


    private class ExampleTask extends AsyncTask<Void,Void,Void>{


        String requestBody;


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setIndeterminate(false);
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL(dev_Url + "signIn");

                //UDID or device Token
                String deviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                String deviceType = "android";

                JSONObject json = new JSONObject();
                json.put("email", username);
                json.put("password", password);
                json.put("device_token", deviceToken);
                json.put("device_type", deviceType);
                requestBody = json.toString();

                JsonParser jsonParser = new JsonParser();
                response = jsonParser.makeHttpRequest(url, requestBody);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

            @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
            tv.setText(response);
        }
    }
}






