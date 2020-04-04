package com.a33y.jo.coronameter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataHelper {
    private static final int MY_SOCKET_TIMEOUT_MS = 5000;
    public static List<Country> data;
    private static final String URL = "https://limitless-beyond-15894.herokuapp.com/api/v1/resources/countries/all";
    public static Date lastUpdate;
    public static Country getCountryById(int id) {
        for (Country c : data)
            if (c.getId() == id)
                return c;
        return null;
    }

    public static void getJsonArray(final View mCLayout, final Context mContext, final FetchListener fetchListener) {
        final List<Country> data = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject country = response.getJSONObject(i);
                                Gson gson = new Gson();
                                Country c = gson.fromJson(country.toString(), Country.class);
                                c.setId(i);
                                data.add(c);
                                lastUpdate = new Date();
                            }
                            if (fetchListener != null)
                                fetchListener.onFetched(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("server", error.getMessage() == null ? "error" : error.getMessage());
                        // Do something when error occurred
                        Snackbar snackbar = Snackbar.make(
                                mCLayout,
                                "Error Contacting server!",
                                Snackbar.LENGTH_LONG
                        );
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                        snackbar.show();
                        if (fetchListener != null)
                            fetchListener.onError(error);
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    public static List<Country> FilterListByName(String query) {
        List<Country> filteredData = new ArrayList<>();
        for (Country c : data)
            if (c.getName().toLowerCase().contains(query.toLowerCase()))
                filteredData.add(c);

        return filteredData;
    }
}
