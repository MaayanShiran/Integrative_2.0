package com.maayan.integrative_20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.maayan.integrative_20.Adatpters.ViewPagerAdapter;
import com.maayan.integrative_20.Entities.CalendarEntity;
import com.maayan.integrative_20.Entities.UserEntity;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

   // TextView status;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;
    TabItem tab1;
    TabItem tab2;
    TextView dateToday;
    com.github.badoualy.datepicker.DatePickerTimeline datePickerTimeline;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    Gson gson = new Gson();

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
        viewPager2 = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        frameLayout = findViewById(R.id.framelayout);

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
                if(tab.getPosition() == 0){
                    datePickerTimeline.setVisibility(View.VISIBLE);
                    datePickerTimeline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                else{
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
        switch (position){
            case 0:
            case 1:
                tabLayout.getTabAt(position).select();
        }
        super.onPageSelected(position);
    }
});

        getUsers();
        createNewObject();
    }

    private void createNewObject(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.15:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CalendarEntity calendar = new CalendarEntity("12345", null);

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<ResponseBody> call = api_interface.createAnObject(gson.toJson(calendar));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        CalendarEntity calendar = gson.fromJson(json, CalendarEntity.class);
                        // object created successfully
                        Log.d("XX1", "Object created successfully: " + calendar.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // error creating object
                    Log.d("XX1", "Error creating object: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.15:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<List<UserEntity>> users = api_interface.getAllUsers();
        users.enqueue(new Callback<List<UserEntity>>() {


            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                if (!response.isSuccessful()){
                    System.out.println("Code: " + response.code());
                    Log.d("XX1", "Code: " + response.code());
               //     status.setText("Code: " + response.code());
                    return;
                }
                Log.d("XX1", "weve made it");
                List<UserEntity> allUsers = response.body();
                for(UserEntity user : allUsers) {
                    System.out.println("" + user.getUserId());
                    Log.d("XX1", "" + user.toString());
                 //   status.setText("success");
                }
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                Log.d("XX1", "oh no " + t.getMessage());
          //      status.setText("oh no " + t.getMessage());
            }
        });
    }
}
