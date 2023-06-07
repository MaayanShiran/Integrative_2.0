package com.maayan.integrative_20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maayan.integrative_20.Adatpters.HourListAdapter1;
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Interfaces.Callback_Adapter_Fragment;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.R;
import com.maayan.integrative_20.Utils.ObjectOperations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabOne1 extends Fragment implements Callback_Adapter_Fragment {
    private LinearLayout userInput;
    private RecyclerView day_hours;
    private Spinner spinner;
    private Button close_popup;
    private Button refresh;
    private Button addEvent;
    private Button exitUserInput;
    private Button submitUserInput;
    private ImageView editEvent;
    private ImageView deleteEvent;
    private ImageView popup_window;
    private ImageView blurred_back;
    private TextView subject;
    private TextView content;
    private TextView participants;
    private TextView time;
    private EditText inputSubject;
    private EditText inputContent;
    private EditText inputStartTime;
    private EditText inputEndTime;
    private EditText inputParticipants;
    private ArrayList<Event> hourList;
    private CurrentUser currentUser = CurrentUser.getInstance();
    HourListAdapter1 adapter;
    private View rootView;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private String spinnerVal;
    private boolean canEnter;
    private OnViewCreatedCallback onViewCreatedCallback;


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

        return rootView;
    }

    private void findViews() {
        userInput = rootView.findViewById(R.id.user_input);
        spinner = rootView.findViewById(R.id.dropdown_menu);
        inputSubject = rootView.findViewById(R.id.TXT_enterSubject);
        inputContent = rootView.findViewById(R.id.TXT_enterContent);
        inputStartTime = rootView.findViewById(R.id.TXT_enterStartTime);
        inputEndTime = rootView.findViewById(R.id.TXT_enterEndTime);
        inputParticipants = rootView.findViewById(R.id.TXT_enterParticipants);
        submitUserInput = userInput.getRootView().findViewById(R.id.BTN_submit_userInput);
        exitUserInput = userInput.getRootView().findViewById(R.id.BTN_exit_userInput);
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
        userInput.setVisibility(View.INVISIBLE);
        exitUserInput.setVisibility(View.INVISIBLE);
        submitUserInput.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter1);

        exitUserInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput.setVisibility(View.INVISIBLE);
                exitUserInput.setVisibility(View.INVISIBLE);
                submitUserInput.setVisibility(View.INVISIBLE);

            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operateUserInput();
                disappearPopup();
                submitUserInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectOperations objectOperations = new ObjectOperations();
                        try {
                            //get input from user:
                            objectOperations.createAnEvent(String.valueOf(inputSubject.getText()), String.valueOf(inputContent.getText()), String.valueOf(inputStartTime.getText()), String.valueOf(inputEndTime.getText()), spinnerVal, new String[]{String.valueOf(inputParticipants.getText())}, currentUser.getDateSelected());
                            exitUserInput.setVisibility(View.INVISIBLE);
                            userInput.setVisibility(View.INVISIBLE);
                            submitUserInput.setVisibility(View.INVISIBLE);

                            clearForm();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });

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

    private void disappearPopup() {
        close_popup.setVisibility(View.INVISIBLE);
        popup_window.setVisibility(View.INVISIBLE);
        subject.setVisibility(View.INVISIBLE);
        content.setVisibility(View.INVISIBLE);
        participants.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        blurred_back.setVisibility(View.INVISIBLE);
        deleteEvent.setVisibility(View.INVISIBLE);
        editEvent.setVisibility(View.INVISIBLE);
    }

    private void clearForm() {
        inputSubject.setText("");
        inputParticipants.setText("");
        inputEndTime.setText("");
        inputStartTime.setText("");
        inputContent.setText("");
    }

    private void operateUserInput() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerVal = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        exitUserInput.bringToFront();
        userInput.bringToFront();
        submitUserInput.bringToFront();
        exitUserInput.setVisibility(View.VISIBLE);
        userInput.setVisibility(View.VISIBLE);
        submitUserInput.setVisibility(View.VISIBLE);
        exitUserInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitUserInput.setVisibility(View.INVISIBLE);
                userInput.setVisibility(View.INVISIBLE);
                submitUserInput.setVisibility(View.INVISIBLE);
            }
        });
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
        content.setVisibility(View.VISIBLE);
        content.setText(hourList.get(position).getContent());
        time.setVisibility(View.VISIBLE);
        time.setText(hourList.get(position).getStartTime() + "-" + hourList.get(position).getEndTime());
        participants.setVisibility(View.VISIBLE);
        participants.setText(hourList.get(position).getParticipants());
        subject.bringToFront();
        participants.bringToFront();
        time.bringToFront();
        content.bringToFront();
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

                operateUserInput();
                disappearPopup();
                submitUserInput.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> objectDetails = new HashMap<>();

                        objectDetails.put("date", currentUser.getDateSelected());
                        objectDetails.put("subject", String.valueOf(inputSubject.getText()));
                        // put internal
                        objectDetails.put("internalObjectId", hourList.get(position).getInternalObjectId());
                        objectDetails.put("startTime", String.valueOf(inputStartTime.getText()));
                        objectDetails.put("endTime", String.valueOf(inputEndTime.getText()));
                        objectDetails.put("participants", new String[]{String.valueOf(inputParticipants.getText())});
                        objectDetails.put("type", spinnerVal);
                        //add content
                        objectDetails.put("content", String.valueOf(inputContent.getText()));
                        SuperAppObjectBoundary updateBoundary = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);
                        ObjectOperations objectOperations = new ObjectOperations();

                        clearForm();

                        objectOperations.editEvent(hourList.get(position).getInternalObjectId(), updateBoundary);
                        exitUserInput.setVisibility(View.INVISIBLE);
                        userInput.setVisibility(View.INVISIBLE);
                        submitUserInput.setVisibility(View.INVISIBLE);
                    }
                });


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

    // notify the adapter that the dataset has changed
    public interface OnViewCreatedCallback {
        void onViewCreatedCompleted();
    }


}


