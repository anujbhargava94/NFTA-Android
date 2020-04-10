package com.example.nftastops.utilclasses;

//make the class non-extendable by adding final
public final class Constants {

    public static final String OPEN = "Open";
    public static final String SRKEY = "SRKEY";
    public static final String OPENSRKEY = "OPENSRKEY";
    public static final String CLOSESRKEY = "CLOSESRKEY";

    private Constants() {
    }

    public static String baseURL = "http://192.168.1.78:8080/";
    //public static String baseURL = "https://nfta-app.herokuapp.com/";
    //public static String baseURL = "https://nftainventory.herokuapp.com/";
    public static String token;
}