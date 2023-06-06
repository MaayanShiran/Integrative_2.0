package com.maayan.integrative_20.Boundaries;



import android.util.Log;

import com.maayan.integrative_20.Model.CreatedBy;
import com.maayan.integrative_20.Model.Location;
import com.maayan.integrative_20.Model.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class SuperAppObjectBoundary {

    private ObjectId objectId;
    private String type;
    private String alias;
    private Boolean active;
    private String creationTimestamp;
    private Location location;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;


    public SuperAppObjectBoundary() {
    }

    public SuperAppObjectBoundary(ObjectId objectId, String type, String alias, Boolean active, Location location,
                                  CreatedBy createdBy, Map<String, Object> objectDetails) {

        this.objectId = objectId;
        this.type = type;
        this.alias = alias;
        this.active = active;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));//was UTC
        Date date = new Date();
        creationTimestamp = dateFormat.format(date);
        Log.d("XX1", "time: " + creationTimestamp);
        this.location = location;
        this.createdBy = createdBy;
        this.objectDetails = objectDetails;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }
    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }
    @Override
    public String toString() {
        return "ObjectBoundary [objectId=" + objectId + ", type=" + type + ", alias=" + alias + ", active=" + active
                + ", creationTimestamp=" + creationTimestamp + ", location=" + location + ", createdBy=" + createdBy
                + ", objectDetails=" + objectDetails + "]";
    }


}
