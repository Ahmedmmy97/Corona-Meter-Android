package com.a33y.jo.coronameter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends BaseAdapter {
    Context context;
    ViewHolder holder;
    List<Country> data;

    public Adapter(Context context, List<Country> data) {
        this.context = context;
        this.data = data;
    }
/* @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_layout,viewGroup,false);
        return new ReviewViewHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder viewHolder, int i) {
           viewHolder.bind_data(Category.categories.get(i));
    }

    @Override
    public int getItemCount() {
        return Category.categories.size();
    }*/

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
            holder = new ViewHolder(view, context);
            //holder.bind_data(Category.categories.get(i));
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.bind_data(data.get(i));
        return view;
    }
}

class ViewHolder extends RecyclerView.ViewHolder{

   TextView name;
   TextView newCases;
   TextView newDeath;
   TextView total;
   LinearLayout container;
   Context c;
    public ViewHolder(@NonNull View itemView, Context c) {
        super(itemView);
        this.c= c;
        assign_views(itemView);
    }
    void assign_views(View v){
        container = v.findViewById(R.id.container);
        name = v.findViewById(R.id.country_name);
        newCases = v.findViewById(R.id.new_cases);
        newDeath = v.findViewById(R.id.new_death);
        total = v.findViewById(R.id.total_cases);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)c).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        container.getLayoutParams().width =(int) ((width-100)/2);
        if(dm.densityDpi<240){
            container.getLayoutParams().width =(int) (width/3);
        }
    }

    public  void bind_data(Country c){
        name.setText(c.getName());
        newCases.setText(c.getNew().equals("") ? "+0":c.getNew());
        newDeath.setText(c.getNewDeath().equals("") ? "+0":c.getNewDeath());
        total.setText(c.getTotal().equals("") ? "0":c.getTotal());

    }

}