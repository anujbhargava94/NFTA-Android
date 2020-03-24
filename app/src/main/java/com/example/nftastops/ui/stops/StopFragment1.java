package com.example.nftastops.ui.stops;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.utilclasses.GPSTracker;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StopFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputLayout stopIdET;
    private TextInputLayout streetOnET;
    private TextInputLayout nearestCSET;
    private TextInputLayout latET;
    private TextInputLayout longET;
    private StopTransactions stopTransactions;
    private Spinner acdirection;
    private Spinner acposition;
    private Spinner acfastenedTo;
    private Spinner acounty;
    private ImageView ivLat;
    private ImageView ivLong;
    private Button fetchButton;
    GPSTracker gpslocation;
    NetworkAPICall apiCAll;


    public StopFragment1() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static StopFragment1 newInstance(String param1, String param2) {
        StopFragment1 fragment = new StopFragment1();
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

        View root = inflater.inflate(R.layout.stop_fragment1, container, false);
        Button nextButton = root.findViewById(R.id.fragment1Next);
        nextButton.setOnClickListener(nextOnClick);
        stopIdET = root.findViewById(R.id.stopId);
        streetOnET = root.findViewById(R.id.streetOn);
        nearestCSET = root.findViewById(R.id.nearestCrossStreet);
        latET = root.findViewById(R.id.latitude);
        longET = root.findViewById(R.id.longitude);
        acdirection = root.findViewById(R.id.autocomplete_direction);
        acfastenedTo = root.findViewById(R.id.autocomplete_fastened_to);
        acounty = root.findViewById(R.id.autocomplete_county);
        acposition = root.findViewById(R.id.autocomplete_position);
        stopTransactions = new StopTransactions(getActivity());
        ivLat = root.findViewById(R.id.icLat);
        ivLong = root.findViewById(R.id.icLong);
        fetchButton = root.findViewById(R.id.fetchButton);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        ivLat.setOnClickListener(latOnclickListner);
        ivLong.setOnClickListener(longOnclickListner);
        fetchButton.setOnClickListener(fetchOnClickListner);


        String[] directions = getResources().getStringArray(R.array.direction);
        ArrayAdapter<String> directionsAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, directions);
        acdirection.setAdapter(directionsAdapter);

        String[] fastenedTo = getResources().getStringArray(R.array.fastenedTo);
        ArrayAdapter<String> fastenedToAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, fastenedTo);
        acfastenedTo.setAdapter(fastenedToAdapter);
        String[] acounties = getResources().getStringArray(R.array.county);
        ArrayAdapter<String> acountiesAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, acounties);
        acounty.setAdapter(acountiesAdapter);
        String[] positions = getResources().getStringArray(R.array.position);
        ArrayAdapter<String> positionsAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, positions);
        acposition.setAdapter(positionsAdapter);
        gpslocation = new GPSTracker(getActivity());

        latET.getEditText().setText(String.valueOf(gpslocation.getLatitude()));
        longET.getEditText().setText(String.valueOf(gpslocation.getLongitude()));
        if (mParam1 == null || mParam1.isEmpty() || mParam1.equals("new")) {
            fetchButton.setVisibility(View.GONE);
        }

        return root;
    }

    View.OnClickListener nextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTransactions.setStop_id(stopIdET.getEditText().getText().toString());
            stopTransactions.setStreet_on(streetOnET.getEditText().getText().toString());
            stopTransactions.setNearest_cross_street(nearestCSET.getEditText().getText().toString());
            stopTransactions.setLatitude(latET.getEditText().getText().toString());
            stopTransactions.setLongitude(longET.getEditText().getText().toString());
            stopTransactions.setDirection(String.valueOf(acdirection.getSelectedItem()));
            stopTransactions.setFastened_to(String.valueOf(acfastenedTo.getSelectedItem()));
            stopTransactions.setCounty(String.valueOf(acounty.getSelectedItem()));
            stopTransactions.setPosition(String.valueOf(acposition.getSelectedItem()));
            stopTransactions.setTransaction_type(mParam1);

            Gson gson = new Gson();
            String transaction = gson.toJson(stopTransactions);
            Bundle args = new Bundle();
            args.putString("stopTransaction", transaction);

            StopFragment2 stopFragment2 = new StopFragment2();
            stopFragment2.setArguments(args);
            replaceFragment(stopFragment2);
        }
    };

    View.OnClickListener latOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Location loc = null;
            double latitude = 0.0;
            if (gpslocation.canGetLocation()) {


                latitude = gpslocation.getLatitude();
            } else {
                gpslocation.showSettingsAlert();
            }
            latET.getEditText().setText(String.valueOf(latitude));
        }
    };

    View.OnClickListener longOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Location loc = null;
            double longitude = 0.0;
            if (gpslocation.canGetLocation()) {
                longitude = gpslocation.getLongitude();
            } else {
                gpslocation.showSettingsAlert();
            }

            longET.getEditText().setText(String.valueOf(longitude));
        }
    };

    View.OnClickListener fetchOnClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String stopIdFetched = stopIdET.getEditText().getText().toString();
            makeApiCall("transaction", stopIdFetched);
        }
    };


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void makeApiCall(String url, String request) {
        String finalRequest = url + "?id=" + request;
        apiCAll.makeGet(getActivity(), finalRequest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String results = response;
                Toast.makeText(
                        getContext(),
                        "Transaction added successfully", Toast.LENGTH_SHORT
                ).show();
                HomeFragment homeFragment = new HomeFragment();
                replaceFragment(homeFragment);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in adding Transaction";
                Toast.makeText(
                        getContext(),
                        errorString, Toast.LENGTH_SHORT
                ).show();
                System.out.println(errorString);
            }
        });
    }

}
