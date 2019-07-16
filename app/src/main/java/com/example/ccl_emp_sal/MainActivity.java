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

public class MainActivity extends AppCompatActivity {

    String monthYearStr;
    String pis;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = findViewById(R.id.user_id);
        final EditText date_input = findViewById(R.id.date_input);
        final Button login = findViewById(R.id.login_button);

        // on click listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pis = input.getText().toString();
                date = date_input.getText().toString();
                Intent loginIntent = new Intent(MainActivity.this, SecondActivity.class);
                String[] separated = date.split("/");
                String year = separated[0];
                String monthOfTheYear = separated[1];
                loginIntent.putExtra("pis", pis);
                loginIntent.putExtra("year", year);
                loginIntent.putExtra("month", monthOfTheYear);
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
                        monthYearStr = year + "/" + month + "/" + i2;
                        date_input.setText(monthYearStr);
                    }
                });
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
            }
        });

    }
}