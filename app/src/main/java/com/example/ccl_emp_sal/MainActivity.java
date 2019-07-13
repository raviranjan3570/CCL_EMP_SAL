package com.example.ccl_emp_sal;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    TextView date_input;
    String monthYearStr;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = findViewById(R.id.user_id);
        final EditText date_input= findViewById(R.id.date_input);
        final Button login = findViewById(R.id.login_button);

        // on click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, SecondActivity.class);
                String pis = input.getText().toString();
                String date = date_input.getText().toString();
                String[] separated = date.split("/");
                String monthOfTheYear = separated[0];
                String year = separated[1];
                loginIntent.putExtra("pis", pis);
                loginIntent.putExtra("month", monthOfTheYear);
                loginIntent.putExtra("year", year);
                startActivity(loginIntent);
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
                        monthYearStr =  year + "/" + month + "/" + i2;
                        date_input.setText(monthYearStr);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });
    }
}