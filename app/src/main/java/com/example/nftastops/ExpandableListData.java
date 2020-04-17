package com.example.nftastops;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;


public class ExpandableListData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();

        List<String> stops = new LinkedList<>();
        stops.add("Add stop");
        stops.add("Remove stop");
        stops.add("Update stop");
        List<String> service = new LinkedList<>();
        List<String> history = new LinkedList<String>();




        expandableListDetail.put("STOPS", stops);
        expandableListDetail.put("SERVICE REQUESTS", service);
        expandableListDetail.put("VIEW HISTORY", history);
        return expandableListDetail;
    }
}


