package com.example.nftastops.utilclasses;

import com.example.nftastops.model.StopTransactions;

import java.util.List;

//make the class non-extendable by adding final
public final class Constants {

    public static final String OPEN = "Open";
    public static final String SRKEY = "SRKEY";
    public static final String OPENSRKEY = "OPENSRKEY";
    public static final String CLOSESRKEY = "CLOSESRKEY";
    public static final String TOKEN = "TOKEN";
    public static final String UNAUTH = "Unauthorized";
    public static final String  PING = "ping";
    public static final String SUCCESS = "success" ;

    private Constants() {
    }

    public static String baseURL = "http://192.168.1.78:8080/";
    //public static String baseURL = "https://nfta-app.herokuapp.com/";
    //public static String baseURL = "https://nftainventory.herokuapp.com/";
    public static String token;
}