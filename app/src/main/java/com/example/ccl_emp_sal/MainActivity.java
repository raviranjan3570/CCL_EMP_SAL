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

                    UserLoginFunction();

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
        date = date_input.getText().toString();
        password = user_password.getText().toString();

        if(TextUtils.isEmpty(pis) || TextUtils.isEmpty(date) || TextUtils.isEmpty(password) )
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(){

        Intent loginIntent = new Intent(MainActivity.this,SecondActivity.class);
        String[] separated = date.split("/");
        String year = separated[0];
        String monthOfTheYear = separated[1];
        loginIntent.putExtra("pis", pis);
        loginIntent.putExtra("password", password);
        loginIntent.putExtra("year", year);
        loginIntent.putExtra("month", monthOfTheYear);
        startActivity(loginIntent);
    }
}