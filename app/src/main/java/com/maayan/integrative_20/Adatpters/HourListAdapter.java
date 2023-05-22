package com.maayan.integrative_20.Adatpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class HourListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> hourList;
    private ViewHolder viewHolder;
    private Callback_Adapter_Fragment openPopUp_Callback;

    public HourListAdapter(Context context, ArrayList<Event> hourList) {
        this.context = context;
        this.hourList = hourList;
    }

    @Override
    public int getCount() {
        return hourList.size();
    }

    @Override
    public Object getItem(int position) {
        return hourList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hour_item, parent, false);

            if (convertView == null) {
                return null; // Return null if inflation fails
            }

            viewHolder = new ViewHolder();

            viewHolder.startTime = convertView.findViewById(R.id.TXT_hour);
            viewHolder.subject = convertView.findViewById(R.id.TXT_subject);
         //   viewHolder.hourTextView = convertView.findViewById(R.id.hourTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        //String hour = hourList.get(position);

        // Set the data to the views
//        viewHolder.hourTextView.setText(hour);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clicking on an event will trigger
                openPopUp_Callback.openPopUp(position);
                Log.d("XX2", "pressed " +  position);
            }
        });
        updateView(viewHolder, position, hourList);


        return convertView;
    }

    private void updateView(ViewHolder viewHolder, int position, ArrayList<Event> hourList) {
        viewHolder.startTime.setText(hourList.get(position).getStartTime());
        viewHolder.subject.setText(hourList.get(position).getSubject());
    }

    public void setUpdatedItem(ArrayList<Event> hourList, int position, TextView startTime, TextView subject) {
        this.hourList = hourList;
        startTime.setText(hourList.get(position).getStartTime());
        subject.setText(hourList.get(position).getSubject());
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(Callback_Adapter_Fragment listener) {
        this.openPopUp_Callback = listener;
    }

    private static class ViewHolder {
        TextView startTime;
        TextView subject;


    }
}
