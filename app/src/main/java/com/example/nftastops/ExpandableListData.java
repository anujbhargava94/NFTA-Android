package com.example.nftastops;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ExpandableListData {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> stops = new ArrayList<String>();
        stops.add("Add stop");
        //stops.add("Remove stop");
        stops.add("Update stop");
        List<String> service = new ArrayList<String>();
        List<String> history = new ArrayList<String>();




        expandableListDetail.put("SERVICE REQUESTS", service);
        expandableListDetail.put("STOPS", stops);
        expandableListDetail.put("VIEW HISTORY", history);
        return expandableListDetail;
    }
}


