package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.houssem.leboncopro.Controller.RequestActivity;
import com.example.houssem.leboncopro.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Condo;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Condo> data = Collections.emptyList();
    private Context context;

    public MyAdapter(Context context, List<Condo> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Condo condo = data.get(position);

        holder.nameView.setText(condo.getName());
        holder.addressView.setText(condo.getAddress());
        holder.cityView.setText(condo.getCity());
        holder.countryView.setText(condo.getCountry());
        holder.element.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RequestActivity.class);

                intent.putExtra("id", condo.getId());
                intent.putExtra("name", condo.getName());
                intent.putExtra("address", condo.getAddress());
                intent.putExtra("city", condo.getCity());
                intent.putExtra("country", condo.getCountry());
                intent.putExtra("postalCode", condo.getPostalCode());
                intent.putExtra("numBlocs", condo.getNumBlocs());
                intent.putExtra("numBlocHouses", condo.getNumBlocHouses());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setFilter(List<Condo> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView addressView;
        TextView cityView;
        TextView countryView;
        LinearLayout element;

        ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.condoName);
            addressView = itemView.findViewById(R.id.condoAddress);
            cityView = itemView.findViewById(R.id.condoCity);
            countryView = itemView.findViewById(R.id.condoCountry);
            element = itemView.findViewById(R.id.element);
        }
    }
}
