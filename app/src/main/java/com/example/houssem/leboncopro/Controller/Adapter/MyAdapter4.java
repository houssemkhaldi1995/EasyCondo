package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.houssem.leboncopro.R;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import Model.Payment;

/**
 * Created by HOUSSEM on 03/05/2018.
 */

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.ViewHolder4> {


    private LayoutInflater inflater;
    private List<Payment> data = Collections.emptyList();
    private Context context;

    public MyAdapter4(Context context, List<Payment> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter4.ViewHolder4 onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.pay, parent, false);
        return new MyAdapter4.ViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter4.ViewHolder4 holder, int position) {

        final Payment payment = data.get(position);

        holder.ref.setText("Reference : #" + payment.getReference());
        holder.amount.setText(payment.getAmount() + ".000 TND");
        Timestamp stamp = new Timestamp(payment.getDate());
        final Date date = new Date(stamp.getTime());
        holder.time.setText(date.toString());
    }

    public void setFilter(List<Payment> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder4 extends RecyclerView.ViewHolder {
        TextView ref;
        TextView amount;
        TextView time;

        ViewHolder4(View itemView) {
            super(itemView);

            ref = itemView.findViewById(R.id.pReference);
            time = itemView.findViewById(R.id.pTime);
            amount = itemView.findViewById(R.id.pAmount);
        }
    }

}
