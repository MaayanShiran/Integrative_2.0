package com.maayan.integrative_20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Interfaces.DataTransferCallback;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.R;
import com.maayan.integrative_20.Utils.ObjectOperations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabOne1 extends Fragment implements Callback_Adapter_Fragment, DataTransferCallback {
    private RecyclerView day_hours;
    private Button close_popup;
    private Button refresh;
    private Button addEvent;
    private ImageView editEvent;
    private ImageView deleteEvent;
    private ImageView popup_window;
    private ImageView blurred_back;
    private TextView subject;
    private TextView content;
    private TextView participants;
    private TextView time;
    private ArrayList<Event> hourList;
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

        rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);

        findViews();

        day_hours.setLayoutManager(new LinearLayoutManager(getActivity()));
        hourList = new ArrayList<>();
        adapter = new HourListAdapter1(getContext(), hourList);
        day_hours.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        String[] part = new String[]{"Maayanishiran@gmail.com", "Johny12@gmail.com"};

        return rootView;
    }

    private void findViews() {

        addEvent = rootView.findViewById(R.id.BTN_add);
        refresh = rootView.findViewById(R.id.BTN_refresh);
        day_hours = rootView.findViewById(R.id.day_hours);
        close_popup = rootView.findViewById(R.id.BTN_close_popup);
        popup_window = rootView.findViewById(R.id.IMG_popup);
        editEvent = rootView.findViewById(R.id.IMG_edit);
        deleteEvent = rootView.findViewById(R.id.IMG_trash);
        blurred_back = rootView.findViewById(R.id.blurred_back);
        subject = rootView.findViewById(R.id.TXT_popup_subject);
        content = rootView.findViewById(R.id.TXT_popup_content);
        participants = rootView.findViewById(R.id.TXT_popup_participants);
        time = rootView.findViewById(R.id.TXT_popup_time);

        close_popup.setVisibility(View.INVISIBLE);
        popup_window.setVisibility(View.INVISIBLE);
        subject.setVisibility(View.INVISIBLE);
        content.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        deleteEvent.setVisibility(View.INVISIBLE);
        editEvent.setVisibility(View.INVISIBLE);


        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectOperations objectOperations = new ObjectOperations();
                try {
                    objectOperations.createAnEvent(currentUser.getUserId().getEmail(), "SHOW");
               //     objectOperations.commandSearchByDate(currentUser.getDateSelected());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourList.clear();
                ObjectOperations objectOperations = new ObjectOperations();
                objectOperations.commandSearchByDate(currentUser.getDateSelected());
                hourList.addAll(currentUser.getEvents());
                adapter.setNewList(currentUser.getEvents());
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
    }

    public HourListAdapter1 getAdapter() {
        return adapter;
    }

    @Override
    public void openPopUp(int position) {

        hourList.clear();
        hourList.addAll(currentUser.getEvents());//something is not right here
        adapter.setNewList(hourList);

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
        editEvent.setImageResource(R.drawable.pencil);
        editEvent.setVisibility(View.VISIBLE);
        editEvent.bringToFront();
        deleteEvent.setImageResource(R.drawable.trashsymbol);
        deleteEvent.setVisibility(View.VISIBLE);
        deleteEvent.bringToFront();

        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                String[] particiants = new String[]{"TEST1@gmail.com", "TEST2@gmail.com"};

                Map<String, Object> objectDetails = new HashMap<>();

                objectDetails.put("date", "15.04.2023");

                objectDetails.put("subject", "TEST200");
                objectDetails.put("startTime", "13:00");
                objectDetails.put("endTime", "20:00");
                objectDetails.put("participants", particiants);
                objectDetails.put("type", EventType.BIRTHDAY);
                SuperAppObjectBoundary updateBoundary = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);

                ObjectOperations objectOperations = new ObjectOperations();
                objectOperations.editEvent(hourList.get(position).getInternalObjectId(), updateBoundary);
               // objectOperations.commandSearchByDate(currentUser.getDateSelected());


            }
        });

        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectOperations objectOperations = new ObjectOperations();
                objectOperations.commandDeleteEvent(hourList.get(position).getInternalObjectId());
                //objectOperations.commandSearchByDate(currentUser.getDateSelected());
            }
        });


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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews();

        if (getArguments() != null) {
            this.selectedDay = getArguments().getInt("day");
            this.selectedMonth = getArguments().getInt("month") + 1;
            this.selectedYear = getArguments().getInt("year");
            canEnter = true;

            ObjectOperations objectOperations = new ObjectOperations();
            if (selectedDay != 0) {
                currentUser.setDateSelected("" + selectedDay + "." + selectedMonth + "." + selectedYear);
            }

            objectOperations.commandSearchByDate("" + selectedDay + "." + selectedMonth + "." + selectedYear);

        }

        hourList.clear();

        if (onViewCreatedCallback != null) {
            onViewCreatedCallback.onViewCreatedCompleted();
        }


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
                editEvent.setVisibility(View.INVISIBLE);
                deleteEvent.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void replaceList() {
        // clear old list
        this.hourList.clear();

        // add new list
        ArrayList<Event> newList = new ArrayList<>();
        String[] parti = new String[]{"Maayani@gmail.com"};
        newList.add(new Event(parti, "" + selectedDay + " " + selectedMonth + " " + selectedYear, "hi", "22:00", "23:00", "date", EventType.HOLIDAY, "1"));
        this.hourList.addAll(newList);

    }

    // notify the adapter that the dataset has changed
    public interface OnViewCreatedCallback {
        void onViewCreatedCompleted();
    }


}


