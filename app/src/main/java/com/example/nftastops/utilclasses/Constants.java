package com.example.nftastops.utilclasses;

import com.example.nftastops.model.StopTransactions;

import java.util.List;

//make the class non-extendable by adding final
public final class Constants {

    public static final String OPEN = "Open";
    public static final String INPROGRESS = "In Progress";
    public static final String SRKEY = "SRKEY";
    public static final String OPENSRKEY = "OPENSRKEY";
    public static final String CLOSESRKEY = "CLOSESRKEY";
    public static final String TOKEN = "TOKEN";
    public static final String UNAUTH = "Unauthorized";
    public static final String  PING = "ping";
    public static final String SUCCESS = "success" ;
    public static final String DIRECTION = "direction";
    public static final String POSITION = "position";
    public static final String FASTENED = "fastenedTo";
    public static final String COUNTY = "county";
    public static final String ROUTE = "route";
    public static final int MY_SOCKET_TIMEOUT_MS = 5000;
    public static final String NFTAPWD = "NFTADevice";
    public static final String USERNAMEKEY = "USERNAMEKEY";
    public static final String DECIMALPATTERN = "###.#######";

    private Constants() {
    }

    public static String baseURL = "http://192.168.1.78:8080/";
    //public static String baseURL = "https://nfta-app.herokuapp.com/";
    //public static String baseURL = "https://nftainventory.herokuapp.com/";
    public static String token;
}