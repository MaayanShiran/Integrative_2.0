package com.maayan.integrative_20.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import com.maayan.integrative_20.R;


public class TabOne extends Fragment {

    ListView day_hours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_one, container, false);
        day_hours = rootView.findViewById(R.id.day_hours);
        return rootView;
    }
}