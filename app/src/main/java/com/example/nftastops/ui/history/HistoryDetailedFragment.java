package com.example.nftastops.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.utilclasses.IOnBackPressed;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * A simple {@link Fragment} subclass.

 * Use the {@link com.example.nftastops.ui.serviceRequest.ServiceRequestDetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryDetailedFragment extends Fragment implements IOnBackPressed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    StopTransactions stopTransactions;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView direction;
    private TextView position;
    private TextView nearest_cross_street;
    private TextView stopId;
    private TextView fastened_to;
    private TextView street_on;
    private TextView county;
    private TextView status;
    private TextView shelter;
    private TextView advertisement;
    private TextView bench;
    private TextView bike_rack;
    private TextView trash_can;
    private TextView time_table;
    private TextView system_map;
    private TextView route;

    public HistoryDetailedFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceRequestDetailedFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static com.example.nftastops.ui.history.HistoryDetailedFragment newInstance(String param1, String param2) {
        com.example.nftastops.ui.history.HistoryDetailedFragment fragment = new com.example.nftastops.ui.history.HistoryDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.nftastops.ui.history.HistoryDetailedFragment fragment = new com.example.nftastops.ui.history.HistoryDetailedFragment();
        FragmentTransaction t = getFragmentManager().beginTransaction();
        //t.add(fragment,"service_request_detailed_fragment");
        //FragmentTransaction.add(ServiceRequestDetailedFragment fragment, String tag );
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history_detailed, container, false);
        stopId = root.findViewById(R.id.times);
        direction = root.findViewById(R.id.directionval);
        street_on = root.findViewById(R.id.streetonval);
        nearest_cross_street = root.findViewById(R.id.nearestcrossstreetval);
        position = root.findViewById(R.id.positionval);
        fastened_to = root.findViewById(R.id.fastenedtoval);

        county = root.findViewById(R.id.countyval);
        status = root.findViewById(R.id.statusval);
        shelter = root.findViewById(R.id.shelterval);
        advertisement = root.findViewById(R.id.advertisementval);
        bench = root.findViewById(R.id.benchval);
        bike_rack = root.findViewById(R.id.bike_rackval);
        trash_can = root.findViewById(R.id.trash_canval);
        time_table = root.findViewById(R.id.time_tableval);
        system_map = root.findViewById(R.id.system_mapval);
        route = root.findViewById(R.id.routeval);

        String stopTransaction = getArguments().getString("stopTransaction");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<StopTransactions>() {
            }.getType();
            stopTransactions = gson.fromJson(stopTransaction, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopId.setText(stopTransactions.getStop_id());
        if(stopTransactions.getDirection()!=null)
            direction.setText(stopTransactions.getDirection().getDisplay_name());
        if(stopTransactions.getStreet_on() != null){
        street_on.setText(stopTransactions.getStreet_on());}
        if(stopTransactions.getNearest_cross_street() != null){
        nearest_cross_street.setText(stopTransactions.getNearest_cross_street());}
        if(stopTransactions.getPosition()!=null)
            position.setText(stopTransactions.getPosition().getDisplay_name());
        if(stopTransactions.getFastened_to()!=null)
            fastened_to.setText(stopTransactions.getFastened_to().getDisplay_name());
        if(stopTransactions.getCounty()!=null)
            county.setText(stopTransactions.getCounty().getDisplay_name());
        if(stopTransactions.getStatus() != null){
        status.setText(stopTransactions.getStatus());}
        shelter.setText(Boolean.toString(stopTransactions.getShelter()));
        advertisement.setText(Boolean.toString(stopTransactions.getAdvertisement()));
        bench.setText(Boolean.toString(stopTransactions.getBench()));
        bike_rack.setText(Boolean.toString(stopTransactions.getBike_rack()));
        trash_can.setText(Boolean.toString(stopTransactions.getTrash_can()));
        time_table.setText(Boolean.toString(stopTransactions.getTime_table()));
        system_map.setText(Boolean.toString(stopTransactions.getSystem_map()));
        if(stopTransactions.getRoutes()!=null && !stopTransactions.getRoutes().isEmpty())
            route.setText(stopTransactions.getRoutesString());


        if(stopTransactions.getTransaction_type()!= null  && stopTransactions.getTransaction_type().equals("remove")){
                shelter.setVisibility(View.GONE);
                advertisement.setVisibility(View.GONE);
                bench.setVisibility(View.GONE);
                bike_rack.setVisibility(View.GONE);
                trash_can.setVisibility(View.GONE);
                time_table.setVisibility(View.GONE);
                system_map.setVisibility(View.GONE);
        }


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_service_request_detailed, container, false);
        return root;
    }

    @Override
    public boolean onBackPressed() {
        //if (myCondition) {
        //action not popBackStack
        Log.d("custom", "onBackPressed");
        //   return true;
        //} else {
        //    return false;
        //}
        return true;
    }


}

