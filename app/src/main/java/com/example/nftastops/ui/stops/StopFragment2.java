package com.example.nftastops.ui.stops;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StopFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox shelter;
    private CheckBox advertisement;
    private CheckBox bench;
    private CheckBox bikeRack;
    private CheckBox trashCan;
    private CheckBox timeTable;
    private CheckBox systemMap;
    private TextInputEditText comments;
    private AutoCompleteTextView acroutes;
    private Button submitButton;
    StopTransactions stopTransactions;
    NetworkAPICall apiCAll;

    public StopFragment2() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static StopFragment2 newInstance(String param1, String param2) {
        StopFragment2 fragment = new StopFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.stop_fragment2, container, false);
        shelter = root.findViewById(R.id.shelterId);
        advertisement = root.findViewById(R.id.advertisementId);
        bench = root.findViewById(R.id.benchId);
        bikeRack = root.findViewById(R.id.bikeRackId);
        trashCan = root.findViewById(R.id.trashCanId);
        timeTable = root.findViewById(R.id.timetableId);
        systemMap = root.findViewById(R.id.systemMapId);
        comments = root.findViewById(R.id.comments);
        acroutes = root.findViewById(R.id.autocomplete_route);
        submitButton = root.findViewById(R.id.fragment2Next);
        submitButton.setOnClickListener(submitClick);

        String[] routes = getResources().getStringArray(R.array.routes);
        ArrayAdapter<String> routesAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, routes);
        acroutes.setAdapter(routesAdapter);

        String stopTransactionJson = getArguments().getString("stopTransaction");
        Gson gson = new Gson();
        stopTransactions = gson.fromJson(stopTransactionJson, StopTransactions.class);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        return root;
    }

    View.OnClickListener submitClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTransactions.setShelter(shelter.isChecked());
            stopTransactions.setAdvertisement(advertisement.isChecked());
            stopTransactions.setBench(bench.isChecked());
            stopTransactions.setBike_rack(bikeRack.isChecked());
            stopTransactions.setTrash_can(trashCan.isChecked());
            stopTransactions.setTime_table(timeTable.isChecked());
            stopTransactions.setSystem_map(systemMap.isChecked());
            stopTransactions.setComments(comments.getText().toString());
            stopTransactions.setRoute(acroutes.getText().toString());

            Gson gson = new Gson();
            String transaction = gson.toJson(stopTransactions);
            makeApiCall("add", transaction);
        }
    };

    private void makeApiCall(String url, String request) {
        apiCAll.makePost(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String results = new String();
                results = response;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in fetching results");
            }
        }, request);
    }

}
