package com.example.ccl_emp_sal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        // Kick off an {@link AsyncTask} to perform the network request
        /*SalaryAsyncTask task = new SalaryAsyncTask();
        task.execute();*/
        updateUi(salary);
    }

    /**
     * Url will go here
     */
    private static final String URL = "{\"pis\":\"100\",\"basic\":\"1000\",\"transport\":\"100\",\"misc\":\"200\",\"tax\":\"300\"}";

    /**
     * Update the screen to display information from the given {@link Detail}.
     */
    private void updateUi(Detail salary) {

        // Display the basic salary in the UI
        TextView basicTextView = findViewById(R.id.basic);
        basicTextView.setText(salary.mBasicSalary);

        // Display the Transport allowance in the UI
        TextView TransportTextView = findViewById(R.id.transport);
        TransportTextView.setText(salary.mTransportAllowance);

        // Display the Miscellaneous Salary in the UI
        TextView miscellaneousTextView = findViewById(R.id.misc);
        miscellaneousTextView.setText(salary.mMiscellaneous);

        // Display the Transport allowance in the UI
        TextView taxTextView = findViewById(R.id.tax);
        taxTextView.setText(salary.mProfessionalTax);

        Button button = findViewById(R.id.total);
        button.setText("Total Salary : " + total + " Rupees");
    }

    // we store the detail object inside salary
    Detail salary = QueryUtils.extractFeatureFromJson(URL);

    // this is used to calculate total salary
    int base = Integer.parseInt(salary.mBasicSalary);
    int transport = Integer.parseInt(salary.mTransportAllowance);
    int misc = Integer.parseInt(salary.mMiscellaneous);
    int tax = Integer.parseInt(salary.mProfessionalTax);

    int totalSalary = (base + transport + misc - tax);

    String total = Integer.toString(totalSalary);

    /**
     * Tag for the log messages
     */
    /*public static final String LOG_TAG = SecondActivity.class.getSimpleName();

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the data.
     */
    /*private class SalaryAsyncTask extends AsyncTask<URL, Void, Detail> {

        @Override
        protected Detail doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Detail salary = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link SalaryAsyncTask}
            return salary;
        }

        /**
         * Update the screen with the given salary (which was the result of the
         * {@link SalaryAsyncTask}).
         */
        /*@Override
        protected void onPostExecute(Detail salary) {
            if (salary == null) {
                return;
            }
            updateUi(salary);
        }

    }*/
}

