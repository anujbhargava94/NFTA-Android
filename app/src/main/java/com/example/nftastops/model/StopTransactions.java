package com.example.nftastops.model;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.SharedPrefUtil;

import java.util.List;

public class StopTransactions extends BaseResponse {
    private String device_id;
    private String stop_id;
    private Dropdowns direction;
    private String street_on;
    private String nearest_cross_street;
    private Dropdowns position;
    private Dropdowns fastened_to;
    private double latitude;
    private double longitude;
    private String location;
    private Dropdowns county;
    private String status;
    private String admin_comments;
    private List<Dropdowns> route;
    private boolean shelter;
    private boolean advertisement;
    private boolean bench;
    private boolean bike_rack;
    private boolean trash_can;
    private boolean time_table;
    private boolean system_map;
    //private int transaction_no;
    private String request_type;
    private String transaction_type;
    private String date;
    private List<Dropdowns> dropdowns;
    private String image0;
    private String image1;
    private String image2;
    private String deviceName;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImage0() {
        return image0;
    }

    public void setImage0(String image0) {
        this.image0 = image0;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public ServiceRequests getWork_request() {
        return work_request;
    }

    public void setWork_request(ServiceRequests work_request) {
        this.work_request = work_request;
    }

    private ServiceRequests work_request;

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    private Integer request_id;

    public Integer getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Integer request_id) {
        this.request_id = request_id;
    }

    public Integer getAdmin_user_id() {
        return admin_user_id;
    }

    public void setAdmin_user_id(Integer admin_user_id) {
        this.admin_user_id = admin_user_id;
    }

    public String getRequested_user() {
        return requested_user;
    }

    public void setRequested_user(String requested_user) {
        this.requested_user = requested_user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

//    public Integer getStop_id() {
//        return stop_id;
//    }
//
//    public void setStop_id(Integer stop_id) {
//        this.stop_id = stop_id;
//    }

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    private Integer admin_user_id;
    private String requested_user;
    ;
    private String reason;
    private Integer stopId;
    private String additional_information;


    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //private ServiceRequest work_request;


    public StopTransactions() {
    }

    public StopTransactions(Context context) {

        this.deviceName = SharedPrefUtil.getRawTasksFromSharedPrefs(context, Constants.USERNAMEKEY);
        this.device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        this.status = "In Progress";
        Toast.makeText(
                context,
                this.device_id, Toast.LENGTH_SHORT
        ).show();
    }


    public String getAdmin_comments() {
        return admin_comments;
    }

    public void setAdmin_comments(String admin_comments) {
        this.admin_comments = admin_comments;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getShelter() {
        return shelter;
    }

    public void setShelter(boolean shelter) {
        this.shelter = shelter;
    }

    public boolean getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(boolean advertisement) {
        this.advertisement = advertisement;
    }

    public boolean getBench() {
        return bench;
    }

    public void setBench(boolean bench) {
        this.bench = bench;
    }

    public boolean getBike_rack() {
        return bike_rack;
    }

    public void setBike_rack(boolean bike_rack) {
        this.bike_rack = bike_rack;
    }

    public boolean getTrash_can() {
        return trash_can;
    }

    public void setTrash_can(boolean trash_can) {
        this.trash_can = trash_can;
    }

    public boolean getTime_table() {
        return time_table;
    }

    public void setTime_table(boolean time_table) {
        this.time_table = time_table;
    }

    public boolean getSystem_map() {
        return system_map;
    }

    public void setSystem_map(boolean system_map) {
        this.system_map = system_map;
    }

    public Dropdowns getDirection() {
        return direction;
    }

    public void setDirection(Dropdowns direction) {
        this.direction = direction;
    }

    public Dropdowns getPosition() {
        return position;
    }

    public void setPosition(Dropdowns position) {
        this.position = position;
    }

    public Dropdowns getFastened_to() {
        return fastened_to;
    }

    public void setFastened_to(Dropdowns fastened_to) {
        this.fastened_to = fastened_to;
    }

    public Dropdowns getCounty() {
        return county;
    }

    public void setCounty(Dropdowns county) {
        this.county = county;
    }

    public List<Dropdowns> getRoute() {
        return route;
    }

    public void setRoute(List<Dropdowns> route) {
        this.route = route;
    }

    public boolean isShelter() {
        return shelter;
    }

    public boolean isAdvertisement() {
        return advertisement;
    }

    public boolean isBench() {
        return bench;
    }

    public boolean isBike_rack() {
        return bike_rack;
    }

    public boolean isTrash_can() {
        return trash_can;
    }

    public boolean isTime_table() {
        return time_table;
    }

    public boolean isSystem_map() {
        return system_map;
    }

    public Integer getStopId() {
        return stopId;
    }

    public void setStopId(Integer stopId) {
        this.stopId = stopId;
    }

    public List<Dropdowns> getDropdowns() {
        return dropdowns;
    }

    public void setDropdowns(List<Dropdowns> dropdowns) {
        this.dropdowns = dropdowns;
    }

    public String getRoutesString(){
        String result = "";
        if(route!=null && !route.isEmpty()){
            for(Dropdowns ele:route){
                result+=ele+",";
            }
        }
        return result;
    }
}

