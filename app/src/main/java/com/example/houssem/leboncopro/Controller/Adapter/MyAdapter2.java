package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.houssem.leboncopro.Controller.DetailsActivity;
import com.example.houssem.leboncopro.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import Model.Incident;

/**
 * Created by HOUSSEM on 30/04/2018.
 */

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder2> {

    private LayoutInflater inflater;
    private List<Incident> data = Collections.emptyList();
    private Context context;

    public MyAdapter2(Context context, List<Incident> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter2.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.entry, parent, false);
        return new MyAdapter2.ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter2.ViewHolder2 holder, int position) {

        final Incident incident = data.get(position);

        holder.subject.setText(incident.getSubject());
        Timestamp stamp = new Timestamp(incident.getTimestamp());
        final Date date = new Date(stamp.getTime());
        holder.time.setText(date.toString());

        switch (incident.getSubject()) {
            case "Fire":
                holder.button.setBackgroundResource(R.drawable.miscellaneous);
                break;
            case "Water Leak":
                holder.button.setBackgroundResource(R.drawable.water);
                break;
            case "Gardening":
                holder.button.setBackgroundResource(R.drawable.jardin);
                break;
            case "Blackout":
                holder.button.setBackgroundResource(R.drawable.electricity);
                break;
            case "Noise":
                holder.button.setBackgroundResource(R.drawable.tongzhi);
                break;
            case "Paint":
                holder.button.setBackgroundResource(R.drawable.paint);
                break;
            case "Road Fix":
                holder.button.setBackgroundResource(R.drawable.road);
                break;
            case "Other":
                holder.button.setBackgroundResource(R.drawable.other);
                break;
            case "Empty Trash Can":
                holder.button.setBackgroundResource(R.drawable.trash);
                break;
            case "Lighting":
                holder.button.setBackgroundResource(R.drawable.lighting);
                break;
            case "Repair":
                holder.button.setBackgroundResource(R.drawable.repair);
                break;
            case "Cleanliness":
                holder.button.setBackgroundResource(R.drawable.cleanliness);
                break;

        }
        holder.entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);

                intent.putExtra("subject", incident.getSubject());
                intent.putExtra("state", incident.getState());
                intent.putExtra("message", incident.getMessage());
                intent.putExtra("date", date.toString());
                intent.putExtra("sender", incident.getSender());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    public void setFilter(List<Incident> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView subject;
        TextView time;
        Button button;
        LinearLayout entry;

        ViewHolder2(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            time = itemView.findViewById(R.id.time);
            entry = itemView.findViewById(R.id.entry);
            button = itemView.findViewById(R.id.entry_btn);
        }
    }

}
