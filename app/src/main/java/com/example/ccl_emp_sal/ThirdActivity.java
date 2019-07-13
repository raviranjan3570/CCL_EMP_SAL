package com.example.ccl_emp_sal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    public String name = null;
    public String designation = null;
    public String unitCode = null;
    public String basic = null;
    public String vda = null;
    public String sda = null;
    public String totalEarning = null;
    public String totalDeduction = null;
    public String netPaid = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        designation = intent.getStringExtra("designation");
        unitCode = intent.getStringExtra("unitCode");
        basic = intent.getStringExtra("basic");
        vda = intent.getStringExtra("vda");
        sda = intent.getStringExtra("sda");
        totalEarning = intent.getStringExtra("totalEarning");
        totalDeduction = intent.getStringExtra("totalDeduction");
        netPaid = intent.getStringExtra("netPaid");

        // Display the name in the UI
        TextView nameTextView = findViewById(R.id.name1);
        nameTextView.setText(name);

        // Display the designation in the UI
        TextView designationTextView = findViewById(R.id.designation1);
        designationTextView.setText(designation);

        // Display the total earning in the UI
        TextView grossAmountTextView = findViewById(R.id.total_earning1);
        grossAmountTextView.setText(totalEarning + " Rs");

        // Display the deduction in the UI
        TextView deductionTextView = findViewById(R.id.deduction1);
        deductionTextView.setText(totalDeduction + " Rs");

        // Display the net salary in the UI
        TextView netSalaryTextView = findViewById(R.id.total_salary1);
        netSalaryTextView.setText(netPaid + " Rs");

        // Display the unit code in the UI
        TextView unitCodeTextView = findViewById(R.id.unit_code);
        unitCodeTextView.setText(unitCode);

        // Display the basic salary in the UI
        TextView basicSalaryTextView = findViewById(R.id.basic_paid);
        basicSalaryTextView.setText(basic + " Rs");

        // Display the vda in the UI
        TextView vdaTextView = findViewById(R.id.vda);
        vdaTextView.setText(vda + " Rs");

        // Display the sda in the UI
        TextView sdaTextView = findViewById(R.id.sda);
        sdaTextView.setText(sda + " Rs");
    }
}