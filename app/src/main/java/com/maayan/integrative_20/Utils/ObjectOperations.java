package com.maayan.integrative_20.Utils;

import static com.maayan.integrative_20.Utils.CONSTANTS.SUPERAPPNAME;

import android.util.Log;
import com.google.gson.Gson;
import com.maayan.integrative_20.Boundaries.CommandId;
import com.maayan.integrative_20.Boundaries.InvokedBy;
import com.maayan.integrative_20.Boundaries.MiniAppCommandBoundary;
import com.maayan.integrative_20.Boundaries.SuperAppObjectBoundary;
import com.maayan.integrative_20.Boundaries.TargetObject;
import com.maayan.integrative_20.Boundaries.UserBoundary;
import com.maayan.integrative_20.Boundaries.UserId;
import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.CurrentUser;
import com.maayan.integrative_20.Model.Event;
import com.maayan.integrative_20.Model.EventType;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;
import com.maayan.integrative_20.Model.UserRole;
import com.maayan.integrative_20.SuperAppObjectIdBoundary;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectOperations {

    private RetrofitClient retrofitClient;
    private CurrentUser currentUser;
    private MiniAppCommandBoundary searchAllByDate;

    public ObjectOperations() {
        retrofitClient = new RetrofitClient(CONSTANTS.retroFitIP);
        currentUser = CurrentUser.getInstance();
    }

    public SuperAppObjectBoundary createAnEvent(String email, String subject) throws ParseException {

        Log.d("VV22", "enter here with " + subject);
        String[] particiants = new String[]{"now0835@gmail.com"};//MUST BE IN DB FIRST
        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("date", "15.4.2023");
        objectDetails.put("subject", subject);
        objectDetails.put("startTime", "13:00");
        objectDetails.put("endTime", "20:00");
        objectDetails.put("participants", particiants);
        objectDetails.put("type", EventType.BIRTHDAY);
        objectDetails.put("internalObjectId", "");
        objectDetails.put("content", "cont1");

        SuperAppObjectBoundary event12 = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);

        final SuperAppObjectBoundary[] returnOne = new SuperAppObjectBoundary[1];

        //change to SUPERAPP USER - to create object BOUNDARY
        UserBoundary updatedUser = new UserBoundary();
        updatedUser.setUserId(currentUser.getUserId());
        updatedUser.setUsername(currentUser.getChosenUsername());
        updatedUser.setAvatar(currentUser.getChosenAvatar());
        updatedUser.setRole(UserRole.SUPERAPP_USER.toString());
        currentUser.setChosenRole(UserRole.SUPERAPP_USER.toString());

        Call <Void> callback =  retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
        callback.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<SuperAppObjectBoundary> returnedObjectBoundary = retrofitClient.getApi_interface().createAnObject(event12);
                returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
                    @Override
                    public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                        updateEventWithnewInternal(response.body());
                    }

                    @Override
                    public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


/*
 Call<SuperAppObjectBoundary> returnedObjectBoundary = retrofitClient.getApi_interface().createAnObject(event12);
        returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                Log.d("XX199", "YASS " + response.code());//SUSPECT
                returnOne[0] = response.body();
                Log.d("XX171", "" + returnOne[0]);
              //  for (Map.Entry<String, Object> entry : response.body().getObjectDetails().entrySet()) {
                 //   currentUser.setNewEventDetails(entry.getKey(), entry.getValue());


                    // Do something with the key and value
               // }
                //currentUser.setEventInternalId(response.body().getObjectId().getInternalObjectId());

                //currentUser.setNewEventObjectID(response.body().getObjectId().getInternalObjectId());

                //update item with its updated internalId:
                updateEventWithnewInternal(response.body());

                //event12.setObjectId(new ObjectId(CONSTANTS.SUPERAPPNAME, response.body().getObjectId().getInternalObjectId()));
                Log.d("XX5", "" + currentUser.getEvents());

                inviteParticipants(response.body());
            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {
                Log.d("XX1", "damn " + t.getMessage());
            }
        });
 */


        return returnOne[0];
    }

    private void updateEventWithnewInternal(SuperAppObjectBoundary event12) {
        Log.d("MAAYAN123711", ""+event12);

        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("date", event12.getObjectDetails().get("date"));
        objectDetails.put("subject", event12.getObjectDetails().get("subject"));
        objectDetails.put("startTime", event12.getObjectDetails().get("startTime"));
        objectDetails.put("endTime", event12.getObjectDetails().get("endTime"));
        objectDetails.put("participants", event12.getObjectDetails().get("participants"));
        objectDetails.put("type", event12.getObjectDetails().get("type"));
        objectDetails.put("internalObjectId", event12.getObjectId().getInternalObjectId());
        objectDetails.put("content", "check");
        event12.setObjectDetails(objectDetails);
      //  currentUser.setNewEventDetails(event12.getObjectId().toString(), event12);
      //  Log.d("MAAYAN12371", "NOW users events: "+ currentUser.getEvents());

        Call<Void> updateitem = retrofitClient.getApi_interface().updateAnObject(SUPERAPPNAME, event12.getObjectId().getInternalObjectId(), SUPERAPPNAME, currentUser.getUserId().getEmail(), event12);
        updateitem.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("MAAYAN123712", ""+response.code()+ " !!! " + response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void getAllEvents(String email) {

        Call<SuperAppObjectBoundary[]> retrieveAllEvents = retrofitClient.getApi_interface().getAllObjects(SUPERAPPNAME, email, 10, 0);
        retrieveAllEvents.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
                Log.d("XX2!", "" + response.body());
               // currentUser.setEvents(toArray());

            }

            @Override
            public void onFailure(Call<SuperAppObjectBoundary[]> call, Throwable t) {

            }
        });
    }

    public void bindObject(SuperAppObjectBoundary event, SuperAppObjectIdBoundary objectIdBoundaryChild) {
        String superApp = event.getObjectId().getSuperapp();
        String internalObjId = event.getObjectId().getInternalObjectId();
        String userEmail = event.getCreatedBy().getUserId().getEmail();

        Call<Void> returnedObjectBoundary = retrofitClient.getApi_interface().BindAnExistingObjectToExistingChildObject(superApp, internalObjId, objectIdBoundaryChild, superApp, userEmail);
        returnedObjectBoundary.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("XXX7771", "success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void inviteParticipants(SuperAppObjectBoundary event) {
        if (!(event.getObjectDetails().containsKey("participants") && event.getObjectDetails().get("participants") != null)) {
            return;
        }
        Log.d("XX7771", "" + event.getObjectDetails().get("participants"));

        ArrayList<String> participants = (ArrayList<String>) event.getObjectDetails().get("participants");
       // String trimmedString = participants.substring(1, participants.length() - 1);
       // String[] array = trimmedString.split(", ");

// Trim any extra whitespace from each email address
       // for (int i = 0; i < array.length; i++) {
         //   array[i] = array[i].trim();
       // }

        //Log.d("XX777", "" + array);
        for (String part : participants) {
            CreatedBy createdBy = new CreatedBy(new UserId(part));
            createdBy.getUserId().setSuperapp(CONSTANTS.SUPERAPPNAME);
            Log.d("XX777", "" + part);

            String type = event.getType();
            String alias = event.getAlias();
            Location location = event.getLocation();
            // SuperAppObjectBoundary event12 = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);

            SuperAppObjectBoundary child = new SuperAppObjectBoundary(new ObjectId("bla", "blaaa123"), type, alias, true, location, createdBy, new HashMap<>());
            Log.d("XX7772", "" + child);
            //save child to DB
            Call<SuperAppObjectBoundary> returnedObjectBoundary = retrofitClient.getApi_interface().createAnObject(child);
            returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
                @Override
                public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
                    Log.d("XX7778", "this is the code: " + response.code() + " " + response.message());
                    ObjectId childId = response.body().getObjectId();
                    SuperAppObjectIdBoundary objectIdBoundaryChild = new SuperAppObjectIdBoundary(childId.getSuperapp(), childId.getInternalObjectId());
                    bindObject(event, objectIdBoundaryChild);
                }

                @Override
                public void onFailure(Call<SuperAppObjectBoundary> call, Throwable t) {

                }
            });
        }

    }

    public void commandDeleteEvent(String internal) {

        MiniAppCommandBoundary commandBoundary = new MiniAppCommandBoundary(new CommandId(SUPERAPPNAME, "miniAppCalendar", internal), "Remove Event", new TargetObject(new ObjectId(SUPERAPPNAME, internal)), new InvokedBy(currentUser.getUserId()), null);
        Call<Object> invokeComman = retrofitClient.getApi_interface().invokeMiniAppCommand("miniAppCalendar", commandBoundary, false);
        invokeComman.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("MAAYAN987", "r: " + response.body() + " " + response.code());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }


        public void commandSearchByDate(String date) {
Log.d("D23X", "date: " + date);

        //type: dummyObject
        //type: EVENT
        Call<SuperAppObjectBoundary[]> serverObjects = retrofitClient.getApi_interface().searchObjectsByType("dummyObject", currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), 20, 0);
        serverObjects.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
                Log.d("MAAYAN123", "WUT" +response.body() + " " + response.code());
                assert response.body() != null;
                HashMap<String, Object> attributes = new HashMap();
               // attributes.put("date", "15.04.2023");
                attributes.put("date", date);
                Log.d("MAAYAN888", attributes.toString());

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
                        Log.d("MAAYAN12355", ""+response.body()[0].getObjectId().getSuperapp());
                        searchAllByDate = new MiniAppCommandBoundary(new CommandId(response.body()[0].getObjectId().getSuperapp(), "miniAppCalendar", "7"), "Find By Date", new TargetObject(response.body()[0].getObjectId()), new InvokedBy(currentUser.getUserId()), attributes);
                        Log.d("MAAYAN123", "search by date: "+searchAllByDate + " ");

                        Call<Object> invokeComman = retrofitClient.getApi_interface().invokeMiniAppCommand(searchAllByDate.getCommandId().getMiniapp(), searchAllByDate, false);
                        invokeComman.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.d("MAAYAN1237", ""+response.body());
                                Gson gson = new Gson();

// The given string containing the events
                                ArrayList<Event> responseList = (ArrayList<Event>) response.body();
                                ArrayList<Event> eventList = new ArrayList<>();
                                List<Map<String, SuperAppObjectBoundary>> objects = (List<Map<String, SuperAppObjectBoundary>>) response.body();; // Replace ... with the provided list

                                for(Map<String,SuperAppObjectBoundary> map: objects){
                                    Map<String, Object> objectDetails = (Map<String, Object>) map.get("objectDetails");
                                    Log.d("MAAYAN1237", "list: " + objectDetails);
                                    String date = (String) objectDetails.get("date");
                                    String subject = (String) objectDetails.get("subject");
                                    String startTime = (String) objectDetails.get("startTime");
                                    String endTime = (String) objectDetails.get("endTime");
                                    String type = (String) objectDetails.get("type");
                                    String objectId = (String) objectDetails.get("internalObjectId");
                                    ArrayList<String> participants = (ArrayList<String>) objectDetails.get("participants");
                                   String content = (String) objectDetails.get("content");
                                    Log.d("MAAYAN12378", "is it: " + objectId);
                                    Event event = new Event(participants.toArray(new String[participants.size()]), subject, content, startTime, endTime, date, EventType.valueOf(type), objectId);

                                    eventList.add(event);


                                }
                                Log.d("MAAYAN123788", "is it: " + eventList);

                                currentUser.setEvents(eventList);
                                //currentUser.setEvents((ArrayList<Event>) response.body());
//                                ArrayList<Event> objectBoundaries = gson.fromJson((String) response.body(), ArrayList.class);

                              //  List<Event> events = new ArrayList<Event>((Integer) response.body());
                               // for (Event objectBoundary : objectBoundaries) {
                                 //   Map<String, Object> objectDetails = objectBoundary.getObjectDetails();
                                 ///   Log.d("MAMA", ""+objectBoundary);
                                   // String date = (String) objectDetails.get("date");
                                    //String subject = (String) objectDetails.get("subject");
                                    //String startTime = objectDetails.getStartTime();
                                    //String endTime = objectDetails.getEndTime();
                                   // EventType type = (EventType) objectDetails.get("type");
                                    //List<String> participants = objectDetails.getParticipants();

                                    String[]part = new String[]{};
                                  ///  Event event = new Event(part,subject,"","","", date, type, "");
                                   // events.add(event);
                                //}
                             //   currentUser.setEvents(eventList);
                               // Log.d("MAAYAN1239", "current events: " + events);

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

    }

    public void editEvent(String objectid, SuperAppObjectBoundary updatedEvent){
        Log.d("NBNB1", "objID"+ objectid);

        //Change user permission to SUPERAPP USER in order to update object
        UserBoundary updatedUser = new UserBoundary();
        updatedUser.setUserId(currentUser.getUserId());
        updatedUser.setUsername(currentUser.getChosenUsername());
        updatedUser.setAvatar(currentUser.getChosenAvatar());
        updatedUser.setRole(UserRole.SUPERAPP_USER.toString());
        currentUser.setChosenRole(UserRole.SUPERAPP_USER.toString());

        Call <Void> callback =  retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
        callback.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<Void> edit = retrofitClient.getApi_interface().updateAnObject(SUPERAPPNAME, objectid, SUPERAPPNAME, currentUser.getUserId().getEmail(), updatedEvent);
                edit.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("NBNB1", ""+ response.body()+ " " + response.code());//

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
/*
  Call<Void> edit = retrofitClient.getApi_interface().updateAnObject(SUPERAPPNAME, objectid, SUPERAPPNAME, currentUser.getUserId().getEmail(), updatedEvent);
        edit.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("NBNB1", ""+ response.body()+ " " + response.code());//
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
 */

    }
}
