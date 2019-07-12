package com.example.ccl_emp_sal;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    }

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
        int total = totalSalary(salary);
        button.setText("Total salary: " + total + " Rupees");
    }

    // this function calculate sum
    private int totalSalary(Detail salary) {
        int base = Integer.parseInt(salary.mBasicSalary);
        int transport = Integer.parseInt(salary.mTransportAllowance);
        int misc = Integer.parseInt(salary.mMiscellaneous);
        int tax = Integer.parseInt(salary.mProfessionalTax);
        return base + transport + misc - tax;
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

                    String pis = currentSalary.getString("pis");
                    String basic = currentSalary.getString("basic");
                    String transport = currentSalary.getString("transport");
                    String misc = currentSalary.getString("misc");
                    String tax = currentSalary.getString("tax");

                    // Create a new {@link Event} object
                    return new Detail(pis, basic, transport, misc, tax);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }
            return null;
        }
    }
}
