package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.houssem.leboncopro.Controller.ContentActivity;
import com.example.houssem.leboncopro.R;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import Model.Bill;

/**
 * Created by HOUSSEM on 03/05/2018.
 */

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.ViewHolder5>  {

    private LayoutInflater inflater;
    private List<Bill> data = Collections.emptyList();
    private Context context;

    public MyAdapter5(Context context, List<Bill> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter5.ViewHolder5 onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.bill, parent, false);
        return new MyAdapter5.ViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter5.ViewHolder5 holder, int position) {

        final Bill bill = data.get(position);

        holder.reference.setText("Reference : #" + bill.getReference());
        Timestamp stamp = new Timestamp(bill.getDate());
        final Date date = new Date(stamp.getTime());
        holder.time.setText(date.toString());

        holder.desc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra("key", (bill.getKey() + ""));
                intent.putExtra("reference", bill.getReference());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    public void setFilter(List<Bill> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder5 extends RecyclerView.ViewHolder {

        TextView reference;
        TextView time;
        LinearLayout desc;

        ViewHolder5(View itemView) {
            super(itemView);
            reference = itemView.findViewById(R.id.bReference);
            time = itemView.findViewById(R.id.bTime);
            desc = itemView.findViewById(R.id.bDesc);
        }
    }

}
