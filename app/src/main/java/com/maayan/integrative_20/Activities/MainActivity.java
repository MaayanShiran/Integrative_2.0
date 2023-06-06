package com.maayan.integrative_20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.maayan.integrative_20.Adatpters.ViewPagerAdapter;
import com.maayan.integrative_20.Fragments.TabOne1;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TabOne1.OnViewCreatedCallback {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;
    TabItem tab1;
    TabItem tab2;
    TextView dateToday;
    ImageView exitSymbol;
    private boolean readyToTransfer = false;
    private Event currentEvent;
    private CurrentUser currentUser = CurrentUser.getInstance();

    com.github.badoualy.datepicker.DatePickerTimeline datePickerTimeline;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_menu);
        datePickerTimeline = findViewById(R.id.date_picker_scroll);
        tabLayout = findViewById(R.id.tabLayout);
        tab1 = findViewById(R.id.tabone);
        tab2 = findViewById(R.id.tabtwo);
        dateToday = findViewById(R.id.todays_date);
        exitSymbol = findViewById(R.id.IMG_exit);
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        frameLayout = findViewById(R.id.framelayout);

        exitSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String[] part = new String[]{"hi", "byeee"};
        currentEvent = new Event(part, "Sample content", "Sample participants", "Sample time", "HI", "bye", EventType.HOLIDAY, "hi");

        datePickerTimeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                TabOne1 tabOne = TabOne1.newInstance(day, month, year);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, tabOne)
                        .commit();

            }
        });


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateToday.setText(date);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                viewPager2.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    datePickerTimeline.setVisibility(View.VISIBLE);

                } else {
                    datePickerTimeline.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);


            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

    }

    @Override
    public void onViewCreatedCompleted() {
        readyToTransfer = true;

    }

}
