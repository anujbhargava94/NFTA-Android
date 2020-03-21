package com.example.nftastops.utilclasses;

//make the class non-extendable by adding final
public final class Constants {

    private Constants(){}
    public static String baseURL = "http://192.168.1.78:8080/";
    public static String autoCompleteEndPoint="autosuggest?keyword=";
    public static String searchEndPoint="test-event?";
    public static String CATEGORY = "category";
    public static String DISTANCE = "distance";
    public static String UNIT = "unit";
    public static String FROMLOCATION = "fromLocation";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String KEYWORD = "keyword";
}