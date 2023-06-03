package com.maayan.integrative_20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.maayan.integrative_20.Adatpters.ViewPagerAdapter;
import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Fragments.TabOne;
import com.maayan.integrative_20.Fragments.TabOne1;
import com.maayan.integrative_20.Interfaces.DataTransferCallback;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.UserRole;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.CountDownLatch;


public class MainActivity extends AppCompatActivity implements TabOne1.OnViewCreatedCallback{
   // TextView status;
   private CountDownLatch latch;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;
    TabItem tab1;
    TabItem tab2;
    TextView dateToday;
    private boolean readyToTransfer = false;
    private Event currentEvent;
    DataTransferCallback dataTransferCallback;
    private CurrentUser currentUser = CurrentUser.getInstance();
    private Handler handler;



    com.github.badoualy.datepicker.DatePickerTimeline datePickerTimeline;


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private boolean canDo;
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

        String [] part = new String[] {"hi", "byeee"};
        currentEvent = new Event(part, "Sample content", "Sample participants", "Sample time", "HI", "bye", EventType.HOLIDAY, "hi");

        datePickerTimeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                TabOne1 tabOne = TabOne1.newInstance(day, month, year);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.framelayout, tabOne)
                        .commit();
/*
     handler = new Handler();
                if(!readyToTransfer){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MNBV", "ready to transfer? " + readyToTransfer);
                            TabOne1 tabOne = TabOne1.newInstance(day, month, year);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .detach(tabOne)
                                    .attach(tabOne)
                                    .commit();


                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.framelayout, tabOne)
                                    .commit();
                            tabOne.replaceList();



                        }
                    });
                }
                else{
                    TabOne1 tabOne = TabOne1.newInstance(day, month, year);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .detach(tabOne)
                            .attach(tabOne)
                            .commit();


                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.framelayout, tabOne)
                            .commit();

                    tabOne.replaceList();
                }
 */


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
                if(tab.getPosition() == 0){
                    datePickerTimeline.setVisibility(View.VISIBLE);

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

        //createNewUser();
        //getUsers();
      //  createNewObject();
        //172.20.27.45 AFEKA
        //10.0.0.22 HOME
    }

    private void createNewUser(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.27.45:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewUserBoundary newUser = new NewUserBoundary(UserRole.MINIAPP_USER.toString(), "HI", "AVATAR", "demo@gmail.com");
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<UserBoundary> user = api_interface.createANewUser(newUser);
        user.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                if(response.isSuccessful()){
                    Log.d("XX1", ""+response.body());
                }
                Log.d("XX1", "succeeded reach " + response);

            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {

            }
        });
    }

 /*
    private void createNewObject(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.15:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CalendarBoundary calendar = new CalendarBoundary("12345", null);

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<ResponseBody> call = api_interface.createAnObject(gson.toJson(calendar));
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        CalendarBoundary calendar = gson.fromJson(json, CalendarBoundary.class);
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
  */

    private void getUsers() {
        //172.20.29.14
        //10.0.0.15
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.20.27.45:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<UserBoundary[]> users = api_interface.getAllUsers();
        users.enqueue(new Callback<UserBoundary[]>() {
            @Override
            public void onResponse(Call<UserBoundary[]> call, Response<UserBoundary[]> response) {
                Log.d("XX1", "success" + users.toString());
                UserBoundary[] allUsers = response.body();
                Log.d("XX1", "********");
                for (UserBoundary user : allUsers) {
                    Log.d("XX1", "" + user.getUserId());

                }
            }

            @Override
            public void onFailure(Call<UserBoundary[]> call, Throwable t) {
                Log.d("XX1", "oh no" + t.getMessage());
            }
        });
    }

    public void setOnItemClickListener(DataTransferCallback listener) {
        this.dataTransferCallback = listener;
    }

    @Override
    public void onViewCreatedCompleted() {
        readyToTransfer = true;

    }

    public interface OnViewCreatedCallback {
        void onViewCreatedCompleted();
    }
}
