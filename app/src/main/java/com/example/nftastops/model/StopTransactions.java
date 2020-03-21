package com.example.nftastops.model;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.annotations.Expose;

public class StopTransactions {
    private String device_id;
    private String stop_id;
    private String direction;
    private String street_on;
    private String nearest_cross_street;
    private String position;
    private String fastened_to;
    private String latitude;
    private String longitude;
    private String location;
    private String county;
    private String status;
    private String comments;
    private String route;
    private Boolean shelter;
    private Boolean advertisement;
    private Boolean bench;
    private Boolean bike_rack;
    private Boolean trash_can;
    private Boolean time_table;
    private Boolean system_map;

    //private ServiceRequest work_request;


    public StopTransactions() {
    }

    public StopTransactions(Context context) {

        this.device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.status = "NEW";
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStreet_on() {
        return street_on;
    }

    public void setStreet_on(String street_on) {
        this.street_on = street_on;
    }

    public String getNearest_cross_street() {
        return nearest_cross_street;
    }

    public void setNearest_cross_street(String nearest_cross_street) {
        this.nearest_cross_street = nearest_cross_street;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFastened_to() {
        return fastened_to;
    }

    public void setFastened_to(String fastened_to) {
        this.fastened_to = fastened_to;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getShelter() {
        return shelter;
    }

    public void setShelter(Boolean shelter) {
        this.shelter = shelter;
    }

    public Boolean getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Boolean advertisement) {
        this.advertisement = advertisement;
    }

    public Boolean getBench() {
        return bench;
    }

    public void setBench(Boolean bench) {
        this.bench = bench;
    }

    public Boolean getBike_rack() {
        return bike_rack;
    }

    public void setBike_rack(Boolean bike_rack) {
        this.bike_rack = bike_rack;
    }

    public Boolean getTrash_can() {
        return trash_can;
    }

    public void setTrash_can(Boolean trash_can) {
        this.trash_can = trash_can;
    }

    public Boolean getTime_table() {
        return time_table;
    }

    public void setTime_table(Boolean time_table) {
        this.time_table = time_table;
    }

    public Boolean getSystem_map() {
        return system_map;
    }

    public void setSystem_map(Boolean system_map) {
        this.system_map = system_map;
    }
}
