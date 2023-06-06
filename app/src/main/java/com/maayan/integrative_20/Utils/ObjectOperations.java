package com.maayan.integrative_20.Utils;

import static com.maayan.integrative_20.Utils.CONSTANTS.SUPERAPPNAME;


import androidx.annotation.NonNull;

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
import com.maayan.integrative_20.Boundaries.SuperAppObjectIdBoundary;

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

    public SuperAppObjectBoundary createAnEvent(String subject, String content, String startTime, String endTime, String type, String[] participants, String date) throws ParseException {

        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("date", date);
        objectDetails.put("subject", subject);
        objectDetails.put("startTime", startTime);
        objectDetails.put("endTime", endTime);
        objectDetails.put("participants", participants);//MUST BE IN DB FIRST
        objectDetails.put("type", type);
        objectDetails.put("internalObjectId", "");
        objectDetails.put("content", content);

        SuperAppObjectBoundary event12 = new SuperAppObjectBoundary(new ObjectId("hh", "1"), "EVENT", "event", true, new Location(55.0, 60.5), new CreatedBy(currentUser.getUserId()), objectDetails);

        final SuperAppObjectBoundary[] returnOne = new SuperAppObjectBoundary[1];

        //change to SUPERAPP USER - to create object BOUNDARY
        UserBoundary updatedUser = changeUserRole(UserRole.SUPERAPP_USER);

        Call<Void> callback = retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
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


        return returnOne[0];
    }

    private void updateEventWithnewInternal(SuperAppObjectBoundary event12) {

        Map<String, Object> objectDetails = new HashMap<>();
        objectDetails.put("date", event12.getObjectDetails().get("date"));
        objectDetails.put("subject", event12.getObjectDetails().get("subject"));
        objectDetails.put("startTime", event12.getObjectDetails().get("startTime"));
        objectDetails.put("endTime", event12.getObjectDetails().get("endTime"));
        objectDetails.put("participants", event12.getObjectDetails().get("participants"));
        objectDetails.put("type", event12.getObjectDetails().get("type"));
        objectDetails.put("internalObjectId", event12.getObjectId().getInternalObjectId());
        objectDetails.put("content", event12.getObjectDetails().get("content"));
        event12.setObjectDetails(objectDetails);

        UserBoundary updatedUser = changeUserRole(UserRole.SUPERAPP_USER);
        Call<Void> callback = retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
        callback.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<Void> updateitem = retrofitClient.getApi_interface().updateAnObject(SUPERAPPNAME, event12.getObjectId().getInternalObjectId(), SUPERAPPNAME, currentUser.getUserId().getEmail(), event12);
                updateitem.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        inviteParticipants(event12);

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

    }

    public void getAllEvents(String email) {

        Call<SuperAppObjectBoundary[]> retrieveAllEvents = retrofitClient.getApi_interface().getAllObjects(SUPERAPPNAME, email, 10, 0);
        retrieveAllEvents.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {

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

        ArrayList<String> participants = (ArrayList<String>) event.getObjectDetails().get("participants");

        for (String part : participants) {
            CreatedBy createdBy = new CreatedBy(new UserId(part));
            createdBy.getUserId().setSuperapp(CONSTANTS.SUPERAPPNAME);

            String type = event.getType();
            String alias = event.getAlias();
            Location location = event.getLocation();

            SuperAppObjectBoundary child = new SuperAppObjectBoundary(new ObjectId("bla", "blaaa123"), type, alias, true, location, createdBy, new HashMap<>());
            //save child to DB
            Call<SuperAppObjectBoundary> returnedObjectBoundary = retrofitClient.getApi_interface().createAnObject(child);
            returnedObjectBoundary.enqueue(new Callback<SuperAppObjectBoundary>() {
                @Override
                public void onResponse(Call<SuperAppObjectBoundary> call, Response<SuperAppObjectBoundary> response) {
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
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }


    public void commandSearchByDate(String date) {

        Call<SuperAppObjectBoundary[]> serverObjects = retrofitClient.getApi_interface().searchObjectsByType("dummyObject", currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), 20, 0);
        serverObjects.enqueue(new Callback<SuperAppObjectBoundary[]>() {
            @Override
            public void onResponse(Call<SuperAppObjectBoundary[]> call, Response<SuperAppObjectBoundary[]> response) {
                assert response.body() != null;
                HashMap<String, Object> attributes = new HashMap();
                attributes.put("date", date);

                UserBoundary updatedUser = changeUserRole(UserRole.MINIAPP_USER);

                Call<Void> callback = retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
                callback.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response1) {

                        searchAllByDate = new MiniAppCommandBoundary(new CommandId(response.body()[0].getObjectId().getSuperapp(), "miniAppCalendar", "7"), "Find By Date", new TargetObject(response.body()[0].getObjectId()), new InvokedBy(currentUser.getUserId()), attributes);

                        Call<Object> invokeComman = retrofitClient.getApi_interface().invokeMiniAppCommand(searchAllByDate.getCommandId().getMiniapp(), searchAllByDate, false);
                        invokeComman.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Gson gson = new Gson();

                                ArrayList<Event> eventList = new ArrayList<>();
                                List<Map<String, SuperAppObjectBoundary>> objects = (List<Map<String, SuperAppObjectBoundary>>) response.body();
                                if (objects != null) {
                                    for (Map<String, SuperAppObjectBoundary> map : objects) {
                                        Map<String, Object> objectDetails = (Map<String, Object>) map.get("objectDetails");
                                        String date = (String) objectDetails.get("date");
                                        String subject = (String) objectDetails.get("subject");
                                        String startTime = (String) objectDetails.get("startTime");
                                        String endTime = (String) objectDetails.get("endTime");
                                        String type = (String) objectDetails.get("type");
                                        String objectId = (String) objectDetails.get("internalObjectId");
                                        String content = (String) objectDetails.get("content");

                                        if (objectDetails.get("participants").getClass() == ArrayList.class) {
                                            ArrayList<String> participants = (ArrayList<String>) objectDetails.get("participants");
                                            Event event = new Event(participants.toArray(new String[participants.size()]), subject, content, startTime, endTime, date, EventType.valueOf(type), objectId);
                                            eventList.add(event);

                                        }


                                    }

                                    currentUser.setEvents(eventList);
                                }


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

    @NonNull
    private UserBoundary changeUserRole(UserRole miniappUser) {
        UserBoundary updatedUser = new UserBoundary();
        updatedUser.setUserId(currentUser.getUserId());
        updatedUser.setUsername(currentUser.getChosenUsername());
        updatedUser.setAvatar(currentUser.getChosenAvatar());
        updatedUser.setRole(miniappUser.toString());
        currentUser.setChosenRole(miniappUser.toString());
        return updatedUser;
    }

    public void editEvent(String objectid, SuperAppObjectBoundary updatedEvent) {

        //Change user permission to SUPERAPP USER in order to update object
        UserBoundary updatedUser = changeUserRole(UserRole.SUPERAPP_USER);

        Call<Void> callback = retrofitClient.getApi_interface().updateUserDetails(currentUser.getUserId().getSuperapp(), currentUser.getUserId().getEmail(), updatedUser);
        callback.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Call<Void> edit = retrofitClient.getApi_interface().updateAnObject(SUPERAPPNAME, objectid, SUPERAPPNAME, currentUser.getUserId().getEmail(), updatedEvent);
                edit.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

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


    }
}
