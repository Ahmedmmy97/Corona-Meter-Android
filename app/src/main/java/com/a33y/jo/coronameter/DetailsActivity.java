package com.a33y.jo.coronameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class DetailsActivity extends AppCompatActivity {
    TextView totalCases;
    TextView newCases;
    TextView totalDeaths;
    TextView newDeaths;
    TextView totalRecovered;
    TextView activeCases;
    TextView name;
    TextView lastUpdate;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        int id = (int)getIntent().getLongExtra("id",0);
        Log.i("id:",String.valueOf(id));
        Country country = DataHelper.getCountryById(id);
        getViews();
        BindData(country);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }



    private void getViews(){
        totalCases = findViewById(R.id.total_cases);
        newCases = findViewById(R.id.new_cases);
        totalDeaths = findViewById(R.id.total_death);
        newDeaths = findViewById(R.id.new_death);
        totalRecovered = findViewById(R.id.total_recovered);
        activeCases = findViewById(R.id.active_cases);
        name= findViewById(R.id.name);
        lastUpdate = findViewById(R.id.last_update);
        mAdView = findViewById(R.id.adView);

    }

    private void BindData(Country country){
        totalCases.setText(country.getTotal());
        newCases.setText(country.getNew());
        totalDeaths.setText(country.getDeath());
        newDeaths.setText(country.getNewDeath());
        totalRecovered.setText(country.getRecovered());
        activeCases.setText(country.getActive());
        name.setText(country.getName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String strDate= formatter.format(DataHelper.lastUpdate);
        lastUpdate.setText(strDate);
    }
}
