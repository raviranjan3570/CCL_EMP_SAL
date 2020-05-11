package com.example.ccl_emp_sal;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    String pis;
    String password;
    String email;
    EditText pis_no, user_email, user_password;
    Boolean CheckEditText;
    Button submit;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://dc1aa186.ngrok.io/ccl/CCL_Server_Side_Script/register.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        submit = findViewById(R.id.submit_button);
        pis_no = findViewById(R.id.user_id_register);
        user_email = findViewById(R.id.user_email_register);
        user_password = findViewById(R.id.user_password_register);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserRegisterFunction(pis, email, password);

                }
                else {

                    Toast toast = Toast.makeText(RegisterActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
            }
        });
    }

    // checks if the input is empty or not

    public void CheckEditTextIsEmptyOrNot(){

        pis = pis_no.getText().toString();
        email = user_email.getText().toString();
        password = user_password.getText().toString();

        CheckEditText = !TextUtils.isEmpty(pis) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);
    }

    // sends data to remote server

    public void UserRegisterFunction(final String Pis_No, final String Email, final String Password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterActivity.this,"Registering Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(RegisterActivity.this,httpResponseMsg, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("pis",params[0]);

                hashMap.put("email",params[1]);

                hashMap.put("password",params[2]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(Pis_No,Email,Password);
    }
}