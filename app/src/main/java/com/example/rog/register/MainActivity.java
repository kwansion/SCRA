package com.example.rog.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "https://www.veonic.com/aigogo.co/synapse/logFromMobile.php";
    public static final String UPLOAD_USR = "email";
    public static final String UPLOAD_PWD = "password";
    private EditText email;
    private EditText password;
    private Button login;
    private Button signUp;
    String getUsr, getPwd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean saveLogin;
    CheckBox saveLoginCheckBox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Loginref", MODE_PRIVATE);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.checkBox3);
        editor = sharedPreferences.edit();

        Intent intent = getIntent();
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btnLogin);
        signUp = (Button)findViewById(R.id.button2);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        saveLogin=sharedPreferences.getBoolean("saveLogin", true);
        if (saveLogin == true){
            email.setText(sharedPreferences.getString("Email" , null));
            password.setText(sharedPreferences.getString("Password", null));
        }



    }

    private void goLogin() {
        class UploadImage extends AsyncTask<String,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Signing In", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {//This part involves the php codes
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("Correct")){// IF in php, the data was found AND IF the echo produced is "Correct", then...
                    finish();//The operation from php will be ended (finish()) and..
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);// This intent will be initiated
                    intent.putExtra("email",getUsr);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_USR, getUsr);
                data.put(UPLOAD_PWD, getPwd);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }

    @Override
    public void onClick(View view) {
        if (view == login) {
            int gotUsername = 0;
            int gotPassword = 0;

            if (view == login) {
                getPwd = password.getText().toString();
                getUsr = email.getText().toString();

                if (getUsr.length() > 0) {
                    gotUsername = 1;

                }
                if (getPwd.length() > 0) {
                    gotPassword = 1;
                }

                /* Proceed to login */
                if (gotUsername == 1 && gotPassword == 1){
                    if(saveLoginCheckBox.isChecked()){
                        editor.putBoolean("saveLogin", true);
                        editor.putString("Email", getUsr);
                        editor.putString("Password", getPwd);
                        editor.commit();
                        goLogin();
                    }else{
                        goLogin();
                    }
                }

                /* print error message */
                else if (gotUsername == 0 && gotPassword == 1) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 1 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 0 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (view == signUp) {
            Intent intent = new Intent(MainActivity.this, register.class);// This intent will be initiated
            startActivity(intent);
        }
    }
}