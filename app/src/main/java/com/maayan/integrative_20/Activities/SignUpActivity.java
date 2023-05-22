package com.maayan.integrative_20.Activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.ObjectBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Boundaries.UserId;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.EventBoundary;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.Model.UserRole;
import com.maayan.integrative_20.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.apend.slider.model.Slide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private com.example.sliderviewlibrary.SliderView sliderView;
    private ir.apend.slider.ui.Slider imgSlider;
    private int avatarPos = 0;
    private Button submit;
    private EditText enterUsername;
    private EditText enterEmail;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
/*
 sliderView.setBackgroundColor(0x00000000);


        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.bluebackground);
        images.add(R.drawable.plus);
        images.add(R.drawable.plus1);
        sliderView.setImages(images);
 */
       // sliderView = findViewById(R.id.sliderView);
        imgSlider = findViewById(R.id.img_slider);
        submit = findViewById(R.id.BTN_signup);
        enterUsername = findViewById(R.id.TXT_username);
        enterEmail = findViewById(R.id.TXT_email);
        List<Slide> slideList = new ArrayList<>();
        int imageResource = R.drawable.avatar1;
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(imageResource) + '/' + getResources().getResourceTypeName(imageResource) + '/' + getResources().getResourceEntryName(imageResource));
        String imagePath = imageUri.toString();
        Slide slide1 = new Slide(0, imagePath, getResources().getDimensionPixelSize(ir.apend.sliderlibrary.R.dimen.slider_image_corner));
        slideList.add(slide1);

        int imageResource1 = R.drawable.avatar2;
        Uri imageUri1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(imageResource1) + '/' + getResources().getResourceTypeName(imageResource1) + '/' + getResources().getResourceEntryName(imageResource1));
        String imagePath1 = imageUri1.toString();
        Slide slide2 = new Slide(1, imagePath1, getResources().getDimensionPixelSize(ir.apend.sliderlibrary.R.dimen.slider_image_corner));
        slideList.add(slide2);

        int imageResource2 = R.drawable.avatar3;
        Uri imageUri2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(imageResource2) + '/' + getResources().getResourceTypeName(imageResource2) + '/' + getResources().getResourceEntryName(imageResource2));
        String imagePath2 = imageUri2.toString();
        Slide slide3 = new Slide(2, imagePath2, getResources().getDimensionPixelSize(ir.apend.sliderlibrary.R.dimen.slider_image_corner));
        slideList.add(slide3);

        imgSlider.addSlides(slideList);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enterUsername.getText().toString();
                String avatar = slideList.get(avatarPos).getImageUrl();
                String email = enterEmail.getText().toString();
                createNewUser(username, avatar, email);
                //retrieveAnEvent("85f680f6-8268-4b7d-9fe5-4cd2dfd15c94");
                try {
                    createAnEvent(email);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent newIntent = new Intent(SignUpActivity.this, MainActivity.class);

// Start the new Intent
                startActivity(newIntent);

// Close the current Activity
                finish();
            }
        });






        imgSlider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                avatarPos = position;
                Toast.makeText(getApplicationContext(), "Avatar #"+avatarPos+ " has chosen", Toast.LENGTH_SHORT).show();
                Log.d("123", ""+avatarPos);

            }
        });



    }

    private void retrieveAnEvent(String eventId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.22:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<ObjectBoundary> retrievedEvent = api_interface.retrieveObject("2023b.Liran.Sorokin-Student4U", eventId);
        retrievedEvent.enqueue(new Callback<ObjectBoundary>() {
            @Override
            public void onResponse(Call<ObjectBoundary> call, Response<ObjectBoundary> response) {
                Log.d("XX1", "trying retrieving - success " + response.body());
            }

            @Override
            public void onFailure(Call<ObjectBoundary> call, Throwable t) {
                Log.d("XX1", "trying retrieving - fail " + t.getMessage());

            }
        });

    }

    private void createAnEvent(String email) throws ParseException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.22:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
     //   UserId [] particiants = new UserId[] {new UserId("maayanShiran@gmail.com"), new UserId("Johnny@gmail.com")};
      //  UserId [] particiants2 = new UserId[] {new UserId("hana@gmail.com")};
      //  EventBoundary[] eventBoundaries = new EventBoundary[] {new EventBoundary("1234", "subject121", "start", "end", particiants, EventType.BIRTHDAY), new EventBoundary("1997", "subject2", "start2", "end2", particiants2, EventType.HOLIDAY)};
      /*
            Map<String, Object> calendar1 = new HashMap<>();
        for (EventBoundary event : eventBoundaries) {
            calendar1.put(event.getEventId(), event);
        }
        ObjectBoundary calendar = new ObjectBoundary(new ObjectId("super", "internal: 1"), "CALENDAR", "calendar", true, new Location(12.5, 14.5),new CreatedBy(new UserId("ab@gmail.com")), calendar1);

       */
        ObjectBoundary event = new ObjectBoundary(new ObjectId("super", "internal: 1"), "EVENT", "event", true, new Location(12.5, 14.5),new CreatedBy(new UserId("ab@gmail.com")), null);

       // CalendarBoundary calendarBoundary = new CalendarBoundary(eventBoundaries);
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<ObjectBoundary> returnedObjectBoundary = api_interface.createAnObject(event);
        returnedObjectBoundary.enqueue(new Callback<ObjectBoundary>() {
            @Override
            public void onResponse(Call<ObjectBoundary> call, Response<ObjectBoundary> response) {
                Log.d("XX1", "YASS "+response.body());
            }

            @Override
            public void onFailure(Call<ObjectBoundary> call, Throwable t) {
                Log.d("XX1", "damn "+t.getMessage());
            }
        });

    }

    private void createNewUser(String username, String avatar, String email){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.22:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("XX1", "params: "+ username+ " "+avatar+ " " + email);

        NewUserBoundary newUser = new NewUserBoundary(UserRole.MINIAPP_USER.toString(), username, avatar, email);
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
                Log.d("XX1", "oh no :((((" + t.getMessage());
            }
        });
    }



}