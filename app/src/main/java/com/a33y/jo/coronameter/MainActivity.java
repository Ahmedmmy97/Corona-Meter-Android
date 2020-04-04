package com.a33y.jo.coronameter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.android.volley.VolleyError;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchListener{
    CustomGridView gridView;
    RecyclerView.LayoutManager layoutManager;
    ScrollView scrollView;
    Adapter adapter;
    LinearLayout main;
    EditText search;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.recycler);
        main = findViewById(R.id.main);
        search = findViewById(R.id.search);
        progressBar = findViewById(R.id.progress_circular);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataHelper.getJsonArray(main,MainActivity.this,MainActivity.this);

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UpdateData(DataHelper.FilterListByName(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        DataHelper.getJsonArray(main,this,this);
    }
    public void UpdateData(List<Country> data){
        adapter = new Adapter(this,data);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if(dm.densityDpi<240){
            gridView.setNumColumns(3);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("id:",String.valueOf(i)+" "+String.valueOf(l) );
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("id",l);
                startActivity(intent);
            }
        });

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onFetched(List<Country> data) {
        DataHelper.data = data;
        UpdateData(data);
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        search.setText("");
        hideKeyboard(this);
    }

    @Override
    public void onError(VolleyError error) {
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }
}
