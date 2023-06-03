package com.maayan.integrative_20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maayan.integrative_20.Activities.MainActivity;
import com.maayan.integrative_20.Adatpters.HourListAdapter1;
import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Interfaces.DataTransferCallback;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class TabOne1 extends Fragment implements Callback_Adapter_Fragment, DataTransferCallback {
    private RecyclerView day_hours;
    private Button close_popup;
    private Button refresh;
    private ImageView popup_window;
    private ImageView blurred_back;
    private TextView subject;
    private TextView content;
    private TextView participants;
    private TextView time;
    private ArrayList<Event> hourList = new ArrayList<>();
    private CurrentUser currentUser = CurrentUser.getInstance();
    Callback_Adapter_Fragment callback_adapter_fragment;
    HourListAdapter1 adapter;
    private View rootView;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private boolean canEnter;
    MainActivity main;
    private OnViewCreatedCallback onViewCreatedCallback;


    public void setOnViewCreatedCallback(OnViewCreatedCallback callback) {
        onViewCreatedCallback = callback;
    }

    public static TabOne1 newInstance(int day, int month, int year) {
        Log.d("XX26", "this happens first?");
        TabOne1 fragment = new TabOne1();
        Bundle args = new Bundle();
        args.putInt("day", day);
        args.putInt("month", month);
        args.putInt("year", year);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("XX26", "or this happens first?");

        if (getArguments() != null) {
            selectedDay = getArguments().getInt("day");
            selectedMonth = getArguments().getInt("month") + 1;
            selectedYear = getArguments().getInt("year");
            Log.d("MM1", "this is the selected date: " + selectedDay + " " + selectedMonth + " " + selectedYear);
            canEnter = true;
        }
        rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);



        return rootView;
    }

    public HourListAdapter1 getAdapter() {
        return adapter;
    }

    @Override
    public void openPopUp(int position) {
        Log.d("XX2", "this is the position transferred: " + position);
        popup_window.setVisibility(View.VISIBLE);
        blurred_back.setVisibility(View.VISIBLE);
        blurred_back.bringToFront();
        popup_window.bringToFront();
        close_popup.setVisibility(View.VISIBLE);
        close_popup.bringToFront();
        subject.setVisibility(View.VISIBLE);
        subject.setText(hourList.get(position).getSubject());
        participants.setVisibility(View.VISIBLE);
        participants.setText(hourList.get(position).getParticipants());
        subject.bringToFront();
        participants.bringToFront();
    }


    public void onDateSelected(int day, int month, int year) {
        this.selectedDay = day;
        this.selectedMonth = month;
        this.selectedYear = year;
    }

    @Override
    public void onDataTransfer() {
        //replaceOldListWithNewList();
    }
/*
  @Override
    public void onResume() {
        super.onResume();
            replaceList();

    }
 */


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Log.d("XX34", "this is current user's events: " + currentUser.getEvents());
        day_hours = rootView.findViewById(R.id.day_hours);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        day_hours.setLayoutManager(layoutManager);
        String [] part = new String[] {"Maayanishiran@gmail.com", "Johny12@gmail.com"};
        hourList.add(new Event(part, "TEST "+selectedYear, "hi1", "hi2", ""+selectedMonth, ""+selectedDay, EventType.HOLIDAY, "hiii"));
        hourList.add(new Event(part, "TEST2 "+selectedYear, "hi1", "hi2", ""+selectedMonth, ""+selectedDay, EventType.HOLIDAY, "hiii"));
        if (adapter == null) {
            adapter = new HourListAdapter1(getContext(), hourList);
            day_hours.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }

//        main.setOnItemClickListener(this::onDataTransfer);
        day_hours.invalidate();
        adapter.notifyDataSetChanged();

/*
 if(canEnter){
            canEnter = false;
            hourList.clear();

            String [] part1 = new String[] {"Maayanishiran@gmail.com", "Johny12@gmail.com"};
           Event event = new Event(part1, "NON-TEST"+selectedYear, "hi1", "hi2", ""+selectedMonth, ""+selectedDay, EventType.HOLIDAY, "hiii");
            hourList.add(new Event(part1, "NON-TEST"+selectedYear, "hi1", "hi2", ""+selectedMonth, ""+selectedDay, EventType.HOLIDAY, "hiii"));
            hourList.addAll((Collection<? extends Event>) event);
            adapter.notifyDataSetChanged();
        }

 */





        /*
        String [] part = new String[] {"Maayan@gmail.com", "Johny12@gmail.com"};
        Event event = new Event(part, "hi123", "hi1", "hi2", "hi3", "hi4", EventType.HOLIDAY, "hiii");
        hourList.clear();
        ArrayList<Event> tempList1 = new ArrayList<>();
        tempList1.add(event);
        this.hourList.addAll(tempList1);
         */


        refresh = rootView.findViewById(R.id.BTN_refresh);

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
/*
 LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        day_hours.setLayoutManager(layoutManager);
        HourListAdapter adapter = new HourListAdapter(this.getContext(), hourList);
        day_hours.setAdapter(adapter);
 */

        // adapter.setOnItemClickListener(this);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourList.clear();
                hourList.addAll(currentUser.getEvents());
                Log.d("MM11", "hi1: " + hourList);
                //adapter.notifyDataSetChanged();
                Log.d("MM1X", "this is the same adapter? "+ adapter);
                //  this.adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        });

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




        if (onViewCreatedCallback != null) {
            onViewCreatedCallback.onViewCreatedCompleted();
        }

        replaceList();
    }

    public void replaceList() {
        // clear old list
        this.hourList.clear();

        // add new list
       // ArrayList<Event> newList = new ArrayList<>(currentUser.getEvents());
        ArrayList<Event> newList = new ArrayList<>();
        //String[] participants, String subject, String content, String startTime,
        // String endTime, String date, EventType type, String objectid
        String [] parti = new String[] {"Maayani@gmail.com"};
        newList.add(new Event(parti, ""+selectedDay+ " " + selectedMonth+ " " + selectedYear, "hi", "22:00", "23:00", "date", EventType.HOLIDAY, "1"));
        this.hourList.addAll(newList);
        Log.d("KLKL1", ""+this.hourList);
        // notify the adapter that the dataset has changed
        // initialize the adapter if it's null

        this.adapter.notifyChange();

    }

        // notify the adapter that the dataset has changed
        public interface OnViewCreatedCallback {
            void onViewCreatedCompleted();
        }


    }
/*
  private void replaceOldListWithNewList() {
        // clear old list
        this.hourList.clear();

        // add new list
        ArrayList<Event> newList = new ArrayList<>(currentUser.getEvents());
        Log.d("KLKL", "curent users events: "+currentUser.getEvents());
      //  newList.addAll(0, currentUser.getEvents());
        this.hourList.addAll(newList);
        Log.d("KLKL", ""+this.hourList);
        // notify adapter
        adapter.notifyDataSetChanged();
    }
 */



    // ..

