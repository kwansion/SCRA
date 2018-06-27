package com.example.rog.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class register extends AppCompatActivity {

    Button signUp,signUpG;
    EditText email,password;
    String email1 ="",password1="";
    boolean gotPass = false, gotEmail = false;
    public static final String UPLOAD_URL = "https://www.veonic.com/aigogo.co/synapse/register.php";
    public static final String UPLOAD_USR = "username";
    public static final String UPLOAD_PWD = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signUp = (Button)findViewById(R.id.su1);
        email = (EditText)findViewById(R.id.e);
        password = (EditText)findViewById(R.id.p);
    }
    public void signUpClick(View view) {
        email1 = email.getText().toString();
        password1 = password.getText().toString();
        //boolean validEmail = isValidEmail(password1);
        if (email1.length() > 0 && password1.length() > 0) {
            gotPass = true;
            //if (validEmail)
            gotEmail = true;
           /* else {
                email.requestFocus();
                Toast.makeText(getApplicationContext(), "Please enter valid email address", Toast.LENGTH_LONG).show();
            }*/
        }
        else
            Toast.makeText(getApplicationContext(), "Email or Password cannot be empty", Toast.LENGTH_LONG).show();
        if (gotEmail && gotPass)
            goReg();

    }
    public final static boolean isValidEmail(String target) {
        return (Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    /* Method to handle data sending to server */
    private void goReg() {
        class UploadImage extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(register.this, "Registering", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("Success")) {
                    finish();//The operation from php will be ended (finish()) and..
                    Intent intent = new Intent(register.this, MainActivity.class);// This intent will be initiated
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USR, email1);
                data.put(UPLOAD_PWD, password1);
                String result = rh.sendPostRequest(UPLOAD_URL,data);
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }
}

