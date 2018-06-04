package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.houssem.leboncopro.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Model.Spending;

/**
 * Created by HOUSSEM on 13/05/2018.
 */

public class MyAdapter6 extends RecyclerView.Adapter<MyAdapter6.ViewHolder6>  {

    private LayoutInflater inflater;
    private List<Spending> data = Collections.emptyList();
    private Context context;

    public MyAdapter6(Context context, List<Spending> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter6.ViewHolder6 onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.spend, parent, false);
        MyAdapter6.ViewHolder6 vh = new MyAdapter6.ViewHolder6(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter6.ViewHolder6 holder, int position) {

        final Spending spending = data.get(position);

        holder.description.setText("" + spending.getDescription());
        holder.price.setText(spending.getPrice() + ".000 TND");
    }

    public void setFilter(List<Spending> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder6 extends RecyclerView.ViewHolder {

        TextView description;
        TextView price;

        ViewHolder6(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.sPrice);
            description = itemView.findViewById(R.id.sDescription);
        }
    }

}