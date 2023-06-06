package com.maayan.integrative_20.Adatpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class HourListAdapter1 extends RecyclerView.Adapter<HourListAdapter1.MyNewViewHolder> {

    Context context;
    ArrayList<Event> eventsList;
    Callback_Adapter_Fragment openPopUp_Callback;
    private static HourListAdapter1 instance;


    @SuppressLint("NotifyDataSetChanged")
    public static HourListAdapter1 getInstance(Context context, ArrayList<Event> eventsList) {
        if (instance == null) {
            instance = new HourListAdapter1(context, eventsList);

        } else {
            instance.setNewList(eventsList);
            instance.setNewContext(context);
        }
        return instance;

    }

    private void setNewContext(Context context) {
        this.context = context;
    }


    public HourListAdapter1(Context context, ArrayList<Event> eventsList) {
        this.context = context;
        this.eventsList = eventsList;

    }

    @NonNull
    @Override
    public MyNewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
        return new MyNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (this.eventsList.size() != 0) {
            Event event = this.eventsList.get(position);
            holder.subject.setText(event.getSubject());
            holder.startTime.setText(event.getStartTime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopUp_Callback.openPopUp(position);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public void setOnItemClickListener(Callback_Adapter_Fragment listener) {
        this.openPopUp_Callback = listener;
    }

    public void setNewList(ArrayList<Event> newList) {
        this.eventsList = newList;
        notifyDataSetChanged();
    }

    public static class MyNewViewHolder extends RecyclerView.ViewHolder {

        TextView startTime;
        TextView subject;

        public MyNewViewHolder(@NonNull View itemView) {
            super(itemView);

            startTime = itemView.findViewById(R.id.TXT_hour);
            subject = itemView.findViewById(R.id.TXT_subject);
        }
    }

}

