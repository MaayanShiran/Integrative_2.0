package com.maayan.integrative_20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.maayan.integrative_20.Adatpters.MyAdapter;
import com.maayan.integrative_20.R;

import java.util.ArrayList;

public class TabTwo extends Fragment {


    com.google.android.material.floatingactionbutton.FloatingActionButton button;
    Button ok_buton;
    EditText enter_new_note;
    RecyclerView recyclerView;
    ArrayList <String> notes;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_two, container, false);
        notes = new ArrayList<>();
        // Find the RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_viewc);
        button = rootView.findViewById(R.id.buttonPlus);
        ok_buton = rootView.findViewById(R.id.BTN_OK);
        ok_buton.setVisibility(View.GONE);
        enter_new_note = rootView.findViewById(R.id.enter_note);
        enter_new_note.setVisibility(View.GONE);
        // Set up the RecyclerView with a LinearLayoutManager and an adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter myAdapter = new MyAdapter(this.getContext(), notes);
        recyclerView.setAdapter(myAdapter);

     //   checkBox = rootView.findViewById(R.id.checkbox);

        // Find the TextView and CheckBox
       // text = rootView.findViewById(R.id.text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_new_note.setVisibility(View.VISIBLE);
                ok_buton.setVisibility(View.VISIBLE);

                ok_buton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String note = (enter_new_note.getText().toString());
                        notes.add(note);
                        Log.d("CC2", notes.toString());
                        myAdapter.notifyItemInserted(notes.size() - 1);
                        enter_new_note.setVisibility(View.GONE);
                        ok_buton.setVisibility(View.GONE);
                        enter_new_note.getText().clear();

                    }
                });




            }
        });

return rootView;


    }
}