package com.example.ccl_emp_sal;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = SecondActivity.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Return an {@link Detail} object by parsing out information about the salary.
     */
    public static Detail extractFeatureFromJson(String salaryJSON) {
        try {
            JSONObject baseJsonResponse = new JSONObject(salaryJSON);

            String pis = baseJsonResponse.getString("pis");
            String basic = baseJsonResponse.getString("basic");
            String transport = baseJsonResponse.getString("transport");
            String misc = baseJsonResponse.getString("misc");
            String tax = baseJsonResponse.getString("tax");

            // Create a new {@link Event} object
            return new Detail(pis, basic, transport, misc, tax);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return null;
    }

/**
 * Returns new URL object from the given string URL.
 */
    /*private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }*/

/**
 * Make an HTTP request to the given URL and return a String as the response.
 */
    /*private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);

            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);}
            else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            // TODO: Handle the exception
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
    }*/

/**
 * Convert the {@link InputStream} into a String which contains the
 * whole JSON response from the server.
 */
    /*private String readFromStream(InputStream inputStream) throws IOException {
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
    }*/
}
