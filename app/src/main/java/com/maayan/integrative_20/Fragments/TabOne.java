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

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class TabOne extends Fragment implements Callback_Adapter_Fragment {

    private RecyclerView recyclerView;
    private TabOneAdapter adapter;
    private ArrayList<Event> itemList;
    private View rootView;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private CurrentUser currentUser = CurrentUser.getInstance();
    com.github.badoualy.datepicker.DatePickerTimeline datePickerTimeline;

    private Button refresh;
    private Button close_popup;
    private ImageView popup_window;
    private ImageView blurred_back;
    private TextView subject;
    private TextView content;
    private TextView participants;
    private TextView time;

    public static TabOne newInstance(int day, int month, int year) {
        Log.d("XX26", "this happens first?");
        TabOne fragment = new TabOne();
        Bundle args = new Bundle();
        args.putInt("day", day);
        args.putInt("month", month);
        args.putInt("year", year);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);

        findViews();
        //this works

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemList = new ArrayList<>();
        //itemList.addAll(currentUser.getEvents());
        adapter = TabOneAdapter.getInstance(itemList);
      //  adapter = new TabOneAdapter(itemList);
        Log.d("MM1X", "first this is the same adapter? "+ this.adapter);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

/*
  if (getArguments() != null) {
            //this doesnt
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemList = new ArrayList<>();
            itemList.addAll(currentUser.getEvents());

            selectedDay = getArguments().getInt("day");
            selectedMonth = getArguments().getInt("month") + 1;
            selectedYear = getArguments().getInt("year");
            Log.d("MM1", "this is the selected date: " + selectedDay + " " + selectedMonth + " " + selectedYear);
        //    itemList.clear();
         //   itemList.addAll(currentUser.getEvents());
            Log.d("MM11", "hi: " + itemList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new TabOneAdapter(itemList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
        }
 */



        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            selectedDay = getArguments().getInt("day");
            selectedMonth = getArguments().getInt("month") + 1;
            selectedYear = getArguments().getInt("year");
            Log.d("MM1", "this is the selected date1: " + selectedDay + " " + selectedMonth + " " + selectedYear);
            itemList.clear();
              itemList.addAll(currentUser.getEvents());
            Log.d("MM11", "hi1: " + itemList);
            //adapter.notifyDataSetChanged();
            Log.d("MM1X", "this is the same adapter? "+ this.adapter);
          //  this.adapter.notifyDataSetChanged();
            this.adapter.notifyItemInserted(itemList.size() - 1);

            //this.adapter.notifyItemRangeChanged(0, itemList.size());
            /*
               getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new TabOneAdapter(itemList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
        }
    });
             */

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
            }
        });


    }

    private void findViews() {

        refresh = rootView.findViewById(R.id.BTN_refresh);
        recyclerView = rootView.findViewById(R.id.day_hours);
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


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                itemList.addAll(currentUser.getEvents());
                Log.d("MM11", "hi1: " + itemList);
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
        subject.setText(itemList.get(position).getSubject());
        participants.setVisibility(View.VISIBLE);
        participants.setText(itemList.get(position).getParticipants());
        subject.bringToFront();
        participants.bringToFront();
    }

    private static class TabOneAdapter extends RecyclerView.Adapter<TabOneAdapter.ViewHolder> {

        private ArrayList<Event> itemList;
        private static TabOneAdapter instance;
        Callback_Adapter_Fragment openPopUp_Callback;


        public TabOneAdapter(ArrayList<Event> itemList) {
            this.itemList = itemList;
        }

        public static TabOneAdapter getInstance(ArrayList<Event> itemList) {
                if (instance == null) {
                    instance = new TabOneAdapter(itemList);
                }
                return instance;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Log.d("MM12", "this is the data that adapter has: " + itemList);
            Event item = itemList.get(position);
            holder.subject.setText(item.getSubject());
            holder.startTime.setText(item.getStartTime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopUp_Callback.openPopUp(position);
                    Log.d("XX27", "here: " + position);

                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void setOnItemClickListener(Callback_Adapter_Fragment listener) {
            this.openPopUp_Callback = listener;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView startTime;
            TextView subject;

            public ViewHolder(View itemView) {
                super(itemView);

                startTime = itemView.findViewById(R.id.TXT_hour);
                subject = itemView.findViewById(R.id.TXT_subject);
            }
        }
    }
}

