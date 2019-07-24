package com.example.ccl_emp_sal;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String monthYearStr;
    String pis;
    String password;
    String date;
    EditText input, user_password, date_input;
    Boolean CheckEditText;
    Button login, register;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://943c11e7.ngrok.io/ccl/CCL_Server_Side_Script/login.php";
    String finalResult ;
    String year;
    String monthOfTheYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.user_id);
        date_input = findViewById(R.id.date_input);
        login = findViewById(R.id.login_button);
        user_password = findViewById(R.id.user_password);
        register = findViewById(R.id.register_button);

        // on click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserLoginFunction(pis, password, monthOfTheYear, year);

                }
                else {

                    Toast toast = Toast.makeText(MainActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
            }
        });

        // date picker
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPicker pickerDialog = new MonthYearPicker();
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        monthYearStr = year + "/" + month;
                        date_input.setText(monthYearStr);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        pis = input.getText().toString();
        password = user_password.getText().toString();
        date = date_input.getText().toString();

        if(TextUtils.isEmpty(pis) || TextUtils.isEmpty(date) || TextUtils.isEmpty(password) )
        {
            CheckEditText = false;
        }
        else {

            String[] separated = date.split("/");
            year = separated[0];
            monthOfTheYear = separated[1];
            CheckEditText = true ;
        }
    }

    // sends data to remote server

    public void UserLoginFunction(final String Pis_No, final String Password, final String Month, final  String Year){

        class UserLoginFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MainActivity.this,"Loading Salary Details",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();
                    Intent loginIntent = new Intent(MainActivity.this,SecondActivity.class);
                    loginIntent.putExtra("pis", pis);
                    loginIntent.putExtra("password", password);
                    loginIntent.putExtra("month", monthOfTheYear);
                    loginIntent.putExtra("year", year);
                    startActivity(loginIntent);

                }
                else{

                    Toast.makeText(MainActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("pis",params[0]);

                hashMap.put("password",params[1]);

                hashMap.put("month",params[2]);

                hashMap.put("year",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginFunctionClass userLoginFunctionClass = new UserLoginFunctionClass();

        userLoginFunctionClass.execute(Pis_No, Password, Month, Year);
    }
}