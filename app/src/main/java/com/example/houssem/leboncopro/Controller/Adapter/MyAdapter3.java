package com.example.houssem.leboncopro.Controller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.houssem.leboncopro.Controller.ContaintActivity;
import com.example.houssem.leboncopro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import Model.Admin;
import Model.Message;

/**
 * Created by HOUSSEM on 01/05/2018.
 */

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder3> {

    private LayoutInflater inflater;
    private List<Message> data = Collections.emptyList();
    private Context context;
    private DatabaseReference mDatabase;
    private String name;
    private DatabaseReference mDatabase2;

    public MyAdapter3(Context context, List<Message> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public MyAdapter3.ViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.message, parent, false);
        return new MyAdapter3.ViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter3.ViewHolder3 holder, int position) {

        final Message message = data.get(position);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin").child(message.getSender());

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                name = (admin.getFirstName() + " " + admin.getLastName());
                holder.sender.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getDetails();
            }
        });

        Timestamp stamp = new Timestamp(message.getTimestamp());
        final Date date = new Date(stamp.getTime());
        holder.time.setText(date.toString());

        if (message.isSeen()) {
            holder.button.setBackgroundResource(R.drawable.visible);
        } else {
            holder.button.setBackgroundResource(R.drawable.invisible);
        }

        holder.message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("Client", Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id", "");

                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Messages").child(id).child(message.getKey()).child("seen");
                mDatabase2.setValue(true);
                Intent intent = new Intent(context, ContaintActivity.class);

                intent.putExtra("sender", name);
                intent.putExtra("message", message.getMessage());
                intent.putExtra("date", date.toString());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    public void setFilter(List<Message> newData) {

        data = new ArrayList<>();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder3 extends RecyclerView.ViewHolder {
        TextView sender;
        TextView time;
        Button button;
        LinearLayout message;

        ViewHolder3(View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.mSender);
            time = itemView.findViewById(R.id.mTime);
            message = itemView.findViewById(R.id.entryMsg);
            button = itemView.findViewById(R.id.mVisible);
        }
    }


}
