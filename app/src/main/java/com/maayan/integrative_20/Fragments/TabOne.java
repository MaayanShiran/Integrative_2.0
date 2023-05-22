package com.maayan.integrative_20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.maayan.integrative_20.Adatpters.HourListAdapter;
import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.R;

import java.util.ArrayList;


public class TabOne extends Fragment implements Callback_Adapter_Fragment {

    private ListView day_hours;
    private Button close_popup;
    private ImageView popup_window;
    private ImageView blurred_back;
    private TextView subject;
    private TextView content;
    private TextView participants;
    private TextView time;
    private ArrayList<Event> hourList;
    private  HourListAdapter adapter;
    Callback_Adapter_Fragment callback_adapter_fragment;

    public static TabOne newInstance(String data) {
        TabOne fragment = new TabOne();
        Bundle args = new Bundle();
        args.putString("key_data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);
        hourList = new ArrayList<>();

        //String[] participants, String subject, String content, String startTime, String endTime
        String[] participants1 = new String[] {"hi", "bye"};
        hourList.add(new Event(participants1, "test", "cont", "20:00", "end"));
        hourList.add(new Event(participants1, "test2", "cont", "21:00", "end"));

        // hourList.add("hi2");
        day_hours = rootView.findViewById(R.id.day_hours);
        close_popup = rootView.findViewById(R.id.BTN_close_popup);
        popup_window = rootView.findViewById(R.id.IMG_popup);
        blurred_back = rootView.findViewById(R.id.blurred_back);
        subject = rootView.findViewById(R.id.TXT_popup_subject);
        content = rootView.findViewById(R.id.TXT_popup_content);
        participants = rootView.findViewById(R.id.TXT_popup_participants);
        time= rootView.findViewById(R.id.TXT_popup_time);

        close_popup.setVisibility(View.INVISIBLE);
        popup_window.setVisibility(View.INVISIBLE);
        subject.setVisibility(View.INVISIBLE);
        content.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);

        adapter = new HourListAdapter(this.getContext(), hourList); // Replace YourAdapter with the actual adapter class name
        day_hours.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        close_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_popup.setVisibility(View.INVISIBLE);
                popup_window.setVisibility(View.INVISIBLE);
                subject.setVisibility(View.INVISIBLE);
                content.setVisibility(View.INVISIBLE);
                participants.setVisibility(View.INVISIBLE);
                time.setVisibility(View.INVISIBLE);
                blurred_back.setVisibility(View.INVISIBLE);

            }
        });


        if (getArguments() != null) {
            String data = getArguments().getString("key_data");
            Log.d("XX2", data);
            hourList.get(1).setSubject(data);//
            Log.d("XX2", "this is the updated list: " + hourList);
         //   adapter.setUpdatedItem(hourList, 1);
            adapter.setUpdatedItem(hourList, 1, time, subject);
          //  adapter.notifyDataSetChanged();
        }
        return rootView;
    }

    public HourListAdapter getAdapter(){
        return adapter;
    }

    @Override
    public void openPopUp(int position) {
        Log.d("XX2", "this is the position transfered: " + position);
        popup_window.setVisibility(View.VISIBLE);
        blurred_back.setVisibility(View.VISIBLE);
        blurred_back.bringToFront();
        popup_window.bringToFront();
        close_popup.setVisibility(View.VISIBLE);

        close_popup.bringToFront();
        subject.setVisibility(View.VISIBLE);
        subject.setText(hourList.get(position).getSubject());
        subject.bringToFront();
    }


/*
 public void updateHourList(String data) {
        if (hourList != null && hourList.size() > 1) {
            hourList.get(1).setSubject(data);
            adapter.setUpdatedItem(hourList);
            adapter.notifyDataSetChanged();
        }
    }
 */


}