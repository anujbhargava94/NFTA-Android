package com.example.nftastops.model;

import java.util.List;

public class ServiceRequests extends BaseResponse{
    private Integer request_id;
    private Integer admin_user_id;
    private String requested_user;
    private String location;
    private Dropdowns direction;
    private List<Dropdowns> routes;
    private String reason;
    private String stop_id;
    private String additional_information;
    private String status;
    private String request_type;
    private Dropdowns position;
    private Dropdowns fastened_to;
    private Dropdowns county;

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public Dropdowns getDirection() {
        return direction;
    }

    public void setDirection(Dropdowns direction) {
        this.direction = direction;
    }

    public List<Dropdowns> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Dropdowns> routes) {
        this.routes = routes;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdditional_information() {
        return additional_information;
    }

    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

}
