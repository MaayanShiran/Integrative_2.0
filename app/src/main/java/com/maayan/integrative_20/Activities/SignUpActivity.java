package com.maayan.integrative_20.Activities;

import static com.maayan.integrative_20.Utils.CONSTANTS.retroFitIP;
import static com.maayan.integrative_20.Utils.CONSTANTS.SUPERAPPNAME;

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

import com.maayan.integrative_20.Boundaries.MiniAppCommandBoundary;
import com.maayan.integrative_20.Boundaries.NewUserBoundary;
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Boundaries.UserId;
import com.maayan.integrative_20.Interfaces.API_Interface;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.EventBoundary;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.R;
import com.maayan.integrative_20.SuperAppObjectIdBoundary;
import com.maayan.integrative_20.Utils.ObjectOperations;
import com.maayan.integrative_20.Utils.UserOperations;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private boolean isTaskExecuted = false;
    private MiniAppCommandBoundary searchAllByDate;
    private List<Slide> slideList;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        findViews();
        sliderSetup();

        currentUser = CurrentUser.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = enterUsername.getText().toString();
                String avatar = slideList.get(avatarPos).getImageUrl();
                String email = enterEmail.getText().toString();
                // TestUser();
                try {
                    createNewUser1(username, avatar, email);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        imgSlider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                avatarPos = position;
                Toast.makeText(getApplicationContext(), "Avatar #" + avatarPos + " has chosen", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void sliderSetup() {

        slideList = new ArrayList<>();
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

        int imageResource3 = R.drawable.avatar4;
        Uri imageUri3 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(imageResource2) + '/' + getResources().getResourceTypeName(imageResource2) + '/' + getResources().getResourceEntryName(imageResource3));
        String imagePath3 = imageUri3.toString();
        Slide slide4 = new Slide(3, imagePath3, getResources().getDimensionPixelSize(ir.apend.sliderlibrary.R.dimen.slider_image_corner));
        slideList.add(slide4);

        imgSlider.addSlides(slideList);
    }

    private void findViews() {
        imgSlider = findViewById(R.id.img_slider);
        submit = findViewById(R.id.BTN_signup);
        enterUsername = findViewById(R.id.TXT_username);
        enterEmail = findViewById(R.id.TXT_email);
    }

    private void TestUser() {
        NewUserBoundary newUserBoundary = new NewUserBoundary();
        newUserBoundary.setEmail("Maayan@gmail.com");
        newUserBoundary.setUsername("Maayan");
        newUserBoundary.setRole("ADMIN");
        newUserBoundary.setAvatar("K");

    }

    private void getAllEvents1(String email) {
        ObjectOperations objectOperations = new ObjectOperations();
        objectOperations.getAllEvents(email);
    }

    private void getAllEvents(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(retroFitIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary[]> retrieveAllEvents = api_interface.getAllObjects(SUPERAPPNAME, email, 10, 0);
        retrieveAllEvents.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary[]> call, Throwable t) {

            }
        });
    }

    private void retrieveAnEvent(String eventId, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(retroFitIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary> retrievedEvent = api_interface.retrieveObject(SUPERAPPNAME, eventId, SUPERAPPNAME, email);
        retrievedEvent.enqueue(new Callback<SuperAppObjectBoundary>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {

            }
        });

    }

    private void createAnEvent1(String email, String subject) throws ParseException {
        ObjectOperations objectOperations = new ObjectOperations();
        objectOperations.createAnEvent(email, subject);
    }

    private SuperAppObjectBoundary createAnEvent(String email, String subject) throws ParseException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(retroFitIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        currentUser = CurrentUser.getInstance();
        SuperAppObjectBoundary event = new SuperAppObjectBoundary(new ObjectId("superm", "internal: 5"), "EVENT", "event", true, new Location(12.5, 14.5), new CreatedBy(new UserId("ab@gmail.com")), null);
        String[] particiants = new String[]{"now1913@gmail.com", "now1915@gmail.com"};

        Map<String, Object> objectDetails = new HashMap<>();

        objectDetails.put("date", "15.04.2023");
        objectDetails.put("subject", subject);
        objectDetails.put("startTime", "13:00");
        objectDetails.put("endTime", "20:00");
        objectDetails.put("participants", particiants);
        objectDetails.put("type", EventType.BIRTHDAY);

        SuperAppObjectBoundary event12 = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);

        final SuperAppObjectBoundary[] returnOne = new SuperAppObjectBoundary[1];
        API_Interface api_interface = retrofit.create(API_Interface.class);
        Call<SuperAppObjectBoundary> returnedObjectBoundary = api_interface.createAnObject(event12);
        returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                EventBoundary eventBoundary111 = new EventBoundary();
                returnOne[0] = response.body();
                for (Map.Entry<String, Object> entry : response.body().getObjectDetails().entrySet()) {
                    currentUser.setNewEventDetails(entry.getKey(), entry.getValue());

                }
                currentUser.setNewEventObjectID(response.body().getObjectId().getInternalObjectId());

                event12.setObjectId(new ObjectId(SUPERAPPNAME, response.body().getObjectId().getInternalObjectId()));
                Log.d("XX7771", "HRE");
                inviteParticipants(response.body());
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {
            }
        });

        return returnOne[0];
    }

    private void inviteParticipants1(SuperAppObjectBoundary event) {

    }

    private void inviteParticipants(SuperAppObjectBoundary event) {

        ObjectOperations objectOperations = new ObjectOperations();
        objectOperations.inviteParticipants(event);

    }


    private void bindObject1(SuperAppObjectBoundary event, SuperAppObjectIdBoundary objectIdBoundaryChild) {

        ObjectOperations objectOperations = new ObjectOperations();
        objectOperations.bindObject(event, objectIdBoundaryChild);
    }


    private void createNewUser1(String username, String avatar, String email) throws IOException {

        UserOperations userOperations = new UserOperations();
        Call<UserBoundary> user = userOperations.createNewUser(username, avatar, email);

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

                        try {
                            if (isTaskExecuted) {

                                //createAnEvent1(email, "Maayan's Birthday");
                                createAnEvent1(email, "Important Exam");
                                getAllEvents1(email);
                                commandSearchByDate1();

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
                    }
                } else {
                }
            }
        };

        task.execute();

    }

    private void commandSearchByDate1() {

        ObjectOperations objectOperations = new ObjectOperations();
        //objectOperations.commandSearchByDate();
    }

}

