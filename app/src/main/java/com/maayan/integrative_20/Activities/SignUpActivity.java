package com.maayan.integrative_20.Activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maayan.integrative_20.Boundaries.CommandId;
import com.maayan.integrative_20.Boundaries.InvokedBy;
import com.maayan.integrative_20.Boundaries.MiniAppCommandBoundary;
import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Boundaries.TargetObject;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Boundaries.UserId;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.EventBoundary;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.Model.UserRole;
import com.maayan.integrative_20.R;
import com.maayan.integrative_20.Utils.RetrofitClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
    private CurrentUser currentUser;
    private RetrofitClient retrofitClient;
    private boolean isTaskExecuted = false;
    private MiniAppCommandBoundary searchAllByDate;

    private final String SUPERAPPNAME = "2023b.Liran.Sorokin-Student4U";

    private final String PHONECONNECT = "http://192.168.43.111:8084";
    private final String MEONOT = "http://172.16.0.117:8084";
    private final String PHOME = "http://10.0.0.35:8084";


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

        currentUser = CurrentUser.getInstance();

        retrofitClient = new RetrofitClient(PHONECONNECT);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enterUsername.getText().toString();
                String avatar = slideList.get(avatarPos).getImageUrl();
                String email = enterEmail.getText().toString();
                TestUser();
                try {
                    createNewUser(username, avatar, email);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //     currentUser = CurrentUser.getInstance();


            }
        });


        imgSlider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                avatarPos = position;
                Toast.makeText(getApplicationContext(), "Avatar #" + avatarPos + " has chosen", Toast.LENGTH_SHORT).show();
                Log.d("123", "" + avatarPos);

            }
        });


    }

    private void TestUser() {
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setEmail("Maayan@gmail.com");
        newUserBoundary.setUsername("Maayan");
        newUserBoundary.setRole("ADMIN");
        newUserBoundary.setAvatar("K");


    }

    private void getAllEvents(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHONECONNECT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary[]> retrieveAllEvents = api_interface.getAllObjects(SUPERAPPNAME, email, 10, 0);
        retrieveAllEvents.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
                Log.d("XX2", "" + response.body());
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary[]> call, Throwable t) {

            }
        });
    }

    private void retrieveAnEvent(String eventId, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHONECONNECT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary> retrievedEvent = api_interface.retrieveObject(SUPERAPPNAME, eventId, SUPERAPPNAME, email);
        retrievedEvent.enqueue(new Callback<SuperAppObjectBoundary>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                Log.d("XX17", "trying retrieving - success " + response.body());
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {
                Log.d("XX1", "trying retrieving - fail " + t.getMessage());

            }
        });

    }

    private SuperAppObjectBoundary createAnEvent(String email, String subject) throws ParseException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHONECONNECT)
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
        currentUser = CurrentUser.getInstance();
        SuperAppObjectBoundary event = new SuperAppObjectBoundary(new ObjectId("superm", "internal: 5"), "EVENT", "event", true, new Location(12.5, 14.5), new CreatedBy(new UserId("ab@gmail.com")), null);
        UserId[] particiants = new UserId[]{new UserId("maayanShiran@gmail.com"), new UserId("Johnny@gmail.com")};
        UserId[] particiants2 = new UserId[]{new UserId("hana@gmail.com")};
        EventBoundary[] eventDetails = new EventBoundary[]{new EventBoundary("1234", "subject121", "start", "end", particiants, EventType.BIRTHDAY), new EventBoundary("1997", "subject2", "start2", "end2", particiants2, EventType.HOLIDAY)};
        Map<String, Object> details = new HashMap<>();
        for (EventBoundary event1 : eventDetails) {
            details.put(event1.getEventId(), event1);
        }

        EventBoundary eventDetails1 = new EventBoundary("15041997", subject, "13:00", "17:00", particiants, EventType.BIRTHDAY);
        Map<String, Object> details133 = new HashMap<>();
        details133.put(eventDetails1.getEventId(), eventDetails1);

        Map<String, Object> objectDetails = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));//was UTC
        Date date = new Date(123, 3, 13);
        //String creationTimestamp = dateFormat.format(date);
       // objectDetails.put("date", date);
       // objectDetails.put("date", new Date(123, 3, 13));
        objectDetails.put("date", "15.04.2023");

        objectDetails.put("subject", subject);
        objectDetails.put("startTime", "13:00");
        objectDetails.put("endTime", "20:00");
        objectDetails.put("participants", particiants);
        objectDetails.put("type", EventType.BIRTHDAY);

        Log.d("YASS", "current user: " + currentUser.getUserId());
        SuperAppObjectBoundary event12 = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);


        final SuperAppObjectBoundary[] returnOne = new SuperAppObjectBoundary[1];
        // CalendarBoundary calendarBoundary = new CalendarBoundary(eventBoundaries);
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary> returnedObjectBoundary = api_interface.createAnObject(event12);
        returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                Log.d("XX199", "YASS " + response.code());
                EventBoundary eventBoundary111 = new EventBoundary();
                returnOne[0] = response.body();
                Log.d("XX171", "" + returnOne[0]);
                for (Map.Entry<String, Object> entry : response.body().getObjectDetails().entrySet()) {
                    currentUser.setNewEventDetails(entry.getKey(), entry.getValue());

                    // Do something with the key and value
                }
                currentUser.setNewEventObjectID(response.body().getObjectId().getInternalObjectId());

                Log.d("XX5", "" + currentUser.getEvents());
                event12.setObjectId(new ObjectId(SUPERAPPNAME, response.body().getObjectId().getInternalObjectId()));
                // currentUser.setEvents(response.body().getObjectDetails());
                // currentUser.setEvents(event);
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {
                Log.d("XX1", "damn " + t.getMessage());
            }
        });

        return returnOne[0];
    }

    private void createNewUser(String username, String avatar, String email) throws IOException {
        /*
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PHONECONNECT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                 API_Interface api_interface = retrofit.create(API_Interface.class);

         */

        Log.d("XX1", "params: " +
                "" + username + " " + avatar + " " + email);

        NewUserBoundary newUser = new NewUserBoundary(UserRole.SUPERAPP_USER.toString(), username, avatar, email);
        Log.d("XX1222", newUser.toString());
        Call<UserBoundary> user = retrofitClient.getApi_interface().createANewUser(newUser);
        Log.d("XX124", user.request().toString());

        AsyncTask<Void, Void, Response<UserBoundary>> task = new AsyncTask<Void, Void, Response<UserBoundary>>() {
            @Override
            protected Response<UserBoundary> doInBackground(Void... voids) {
                try {
                    return user.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Response<UserBoundary> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        currentUser = CurrentUser.getInstance();
                        currentUser.setUserId(response.body().getUserId());
                        currentUser.setChosenAvatar(response.body().getAvatar());
                        currentUser.setChosenUsername(response.body().getUsername());
                        currentUser.setChosenRole(response.body().getRole());
                        isTaskExecuted = true;
                        Log.d("XX33", "" + currentUser);

                        try {
                            if (isTaskExecuted) {
                                Log.d("XX33", "now this" + currentUser.getUserId());

                                SuperAppObjectBoundary event = createAnEvent(email, "Maayan's Birthday");
                                Log.d("XX17", "this is the event: " + event);
//                                retrieveAnEvent(event.getObjectId().getInternalObjectId(),email);
                                createAnEvent(email, "Important Exam");
                                getAllEvents(email);
                                commandSearchByDate();

                                Intent newIntent = new Intent(SignUpActivity.this, MainActivity.class);

// Start the new Intent
                                startActivity(newIntent);

// Close the current Activity
                                finish();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Log.d("XX12", "problem " + response.code());
                    }
                } else {
                    Log.d("XX1", "oh no :((((");
                }
            }
        };

        task.execute();

        /*
          user.enqueue(new Callback<UserBoundary>() {
            @Override
            public void onResponse(Call<UserBoundary> call, Response<UserBoundary> response) {
                //Log.d("XX12", "problem1 " + response.body().toString() +"\n"+ response.message());
                Log.d("XX14", "YASS2 "+response.code());

                if(response.isSuccessful()){
                    Log.d("XX122", "success "+response.code());
                    currentUser = CurrentUser.getInstance();
                    currentUser.setUserId(response.body().getUserId());
                    currentUser.setChosenAvatar(response.body().getAvatar());
                    currentUser.setChosenUsername(response.body().getUsername());
                    currentUser.setChosenRole(response.body().getRole());
                    Log.d("XX33", "" + currentUser);

                }
                Log.d("XX12", "problem " + response.code());

            }

            @Override
            public void onFailure(Call<UserBoundary> call, Throwable t) {
                Log.d("XX1", "oh no :((((" + t.getMessage());
            }
        });
         */

    }

    private void commandSearchByDate() {
        Log.d("MAAYAN1234", "check");

        //type: dummyObject
        //type: EVENT
        Call<SuperAppObjectBoundary[]> serverObjects = retrofitClient.getApi_interface().searchObjectsByType("dummyObject", currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), 10, 0);
        serverObjects.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
                Log.d("MAAYAN123", response.body() + " " + response.code());
                assert response.body() != null;
                HashMap<String, Object> attributes = new HashMap();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));//was UTC
                Date date = new Date(123, 3, 13);
              //  String creationTimestamp = dateFormat.format(date);
              // attributes.put("date", date);
                attributes.put("date", "15.04.2023");
                Log.d("MAAYAN888", attributes.toString());

               // currentUser.setChosenRole(UserRole.MINIAPP_USER.toString());
                UserBoundary updatedUser = new UserBoundary();
                updatedUser.setUserId(currentUser.getUserId());
                updatedUser.setUsername(currentUser.getChosenUsername());
                updatedUser.setAvatar(currentUser.getChosenAvatar());
                updatedUser.setRole(UserRole.MINIAPP_USER.toString());
                currentUser.setChosenRole(UserRole.MINIAPP_USER.toString());

                Call <Void> callback =  retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
                callback.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response1) {
                        searchAllByDate = new MiniAppCommandBoundary(new CommandId(response.body()[0].getObjectId().getSuperapp(), "miniAppCalendar", "7"), "Find By Date", new TargetObject(response.body()[0].getObjectId()), new InvokedBy(currentUser.getUserId()), attributes);
                        Log.d("MAAYAN123", "search by date: "+searchAllByDate + " ");

                        Call<Object> invokeComman = retrofitClient.getApi_interface().invokeMiniAppCommand(searchAllByDate.getCommandId().getMiniapp(), searchAllByDate, false);
                        invokeComman.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.d("MAAYAN1237", ""+response.body());
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary[]> call, Throwable t) {

            }
        });



/*
  Call<Object> invokeComman = retrofitClient.getApi_interface().invokeMiniAppCommand(searchAllByDate.getCommandId().getMiniapp(), searchAllByDate, false);
        invokeComman.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("XX121", response.body().toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
 */
        }
    }

