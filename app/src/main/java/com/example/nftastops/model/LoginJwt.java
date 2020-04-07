package com.example.nftastops.model;

public class LoginJwt extends BaseResponse {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
