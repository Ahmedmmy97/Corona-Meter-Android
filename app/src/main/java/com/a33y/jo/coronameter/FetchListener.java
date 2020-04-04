package com.a33y.jo.coronameter;

import com.android.volley.VolleyError;

import java.util.List;

public interface FetchListener {
    public void onFetched(List<Country> data);
    public void onError(VolleyError error);
}
