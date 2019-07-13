package com.example.ccl_emp_sal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class SecondActivity extends AppCompatActivity {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = SecondActivity.class.getSimpleName();

    /**
     * Url will go here
     */
    private static final String URL = "http://192.168.43.123/ccl/fetch.php";

    public String pis = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // gets the value of userId
        Intent intent = getIntent();
        pis = intent.getStringExtra("pis");

        // Kick off an {@link AsyncTask} to perform the network request
        SalaryAsyncTask task = new SalaryAsyncTask();
        task.execute();

        final Button detail = findViewById(R.id.detail);

        // on click listener
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(detailIntent);
            }
        });
    }

    /**
     * Update the screen to display information from the given {@link Detail}.
     */
    private void updateUi(Detail salary) {

        // Display the name in the UI
        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(salary.mName);

        // Display the designation in the UI
        TextView designationTextView = findViewById(R.id.designation);
        designationTextView.setText(salary.mDesignation);

        // Display the total earning in the UI
        TextView grossAmountTextView = findViewById(R.id.total_earning);
        grossAmountTextView.setText(salary.mGrossAmount);

        // Display the deduction in the UI
        TextView deductionTextView = findViewById(R.id.deduction);
        deductionTextView.setText(salary.mTotalDeduction);

        // Display the net salary in the UI
        TextView netSalaryTextView = findViewById(R.id.total_salary);
        netSalaryTextView.setText(salary.mNetPaid);

        // for third activity

        // Display the name in the UI
        TextView nameTextView1 = findViewById(R.id.name1);
        nameTextView1.setText(salary.mName);

        // Display the designation in the UI
        TextView designationTextView1 = findViewById(R.id.designation1);
        designationTextView1.setText(salary.mDesignation);

        // Display the total earning in the UI
        TextView grossAmountTextView1 = findViewById(R.id.total_earning1);
        grossAmountTextView1.setText(salary.mGrossAmount);

        // Display the deduction in the UI
        TextView deductionTextView1 = findViewById(R.id.deduction1);
        deductionTextView1.setText(salary.mTotalDeduction);

        // Display the net salary in the UI
        TextView netSalaryTextView1 = findViewById(R.id.total_salary1);
        netSalaryTextView1.setText(salary.mNetPaid);

        // Display the unit code in the UI
        TextView unitCodeTextView = findViewById(R.id.unit_code);
        unitCodeTextView.setText(salary.mUnitCode);

        // Display the basic salary in the UI
        TextView basicPaidTextView = findViewById(R.id.basic_paid);
        basicPaidTextView.setText(salary.mBasicSalary);

        // Display the vda in the UI
        TextView vdaTextView = findViewById(R.id.vda);
        vdaTextView.setText(salary.mVda);

        // Display the sda in the UI
        TextView sdaTextView = findViewById(R.id.sda);
        sdaTextView.setText(salary.mSda);
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the data.
     */
    private class SalaryAsyncTask extends AsyncTask<java.net.URL, Void, Detail> {

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
        @Override
        protected void onPostExecute(Detail salary) {
            if (salary == null) {
                return;
            }
            updateUi(salary);
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                // for encoding particular input
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pis", pis);

                String query = builder.build().getEncodedQuery();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                urlConnection.connect();

                if (urlConnection.getResponseCode()==200){
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);}
                else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                Log.e(LOG_TAG,"Problem retrieving  the earthquake json results. ",e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Detail} object by parsing out information about the salary.
         */
        private Detail extractFeatureFromJson(String salaryJSON) {

            try {
                JSONObject baseJsonResponse = new JSONObject(salaryJSON);

                JSONArray salaryArray = baseJsonResponse.getJSONArray("salary");

                for (int i = 0; i < salaryArray.length(); i++) {

                    JSONObject currentSalary = salaryArray.getJSONObject(i);

                    String emp_code = currentSalary.getString("EMP_CODE");
                    String name = currentSalary.getString("NAME");
                    String designation = currentSalary.getString("DESIGNATION");
                    String unit_code = currentSalary.getString("UNIT_CODE");
                    String year = currentSalary.getString("YEAR");
                    String month = currentSalary.getString("MONTH");
                    String basic_paid = currentSalary.getString("BASIC_PAID");
                    String vda = currentSalary.getString("VDA");
                    String sda = currentSalary.getString("SDA");
                    String gross_amount = currentSalary.getString("GROSS_AMOUNT");
                    String total_deduction_amount = currentSalary.getString("TOTAL_DEDUCTION_AMOUNT");
                    String net_paid = currentSalary.getString("NET_PAID");

                    // Create a new {@link Event} object
                    return new Detail(emp_code, name, designation, unit_code, year, month, basic_paid, vda, sda, gross_amount, total_deduction_amount, net_paid);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }
            return null;
        }
    }
}
