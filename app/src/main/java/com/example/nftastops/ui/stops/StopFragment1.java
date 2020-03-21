package com.example.nftastops.ui.stops;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;

import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.google.android.material.textfield.TextInputEditText;
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
    private TextInputEditText stopIdET;
    private TextInputEditText streetOnET;
    private TextInputEditText nearestCSET;
    private TextInputEditText latET;
    private TextInputEditText longET;
    private StopTransactions stopTransactions;
    private Spinner acdirection;
    private Spinner acposition;
    private Spinner acfastenedTo;
    private Spinner acounty;


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

        String[] directions = getResources().getStringArray(R.array.direction);
        ArrayAdapter<String> directionsAdapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,directions);
        acdirection.setAdapter(directionsAdapter);

        String[] fastenedTo = getResources().getStringArray(R.array.fastenedTo);
        ArrayAdapter<String> fastenedToAdapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,fastenedTo);
        acfastenedTo.setAdapter(fastenedToAdapter);
        String[] acounties = getResources().getStringArray(R.array.county);
        ArrayAdapter<String> acountiesAdapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,acounties);
        acounty.setAdapter(acountiesAdapter);
        String[] positions = getResources().getStringArray(R.array.position);
        ArrayAdapter<String> positionsAdapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,positions);
        acposition.setAdapter(positionsAdapter);

        return root;
    }

    View.OnClickListener nextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTransactions.setStop_id(stopIdET.getEditableText().toString());
            stopTransactions.setStreet_on(streetOnET.getEditableText().toString());
            stopTransactions.setNearest_cross_street(nearestCSET.getEditableText().toString());
            stopTransactions.setLatitude(latET.getEditableText().toString());
            stopTransactions.setLongitude(longET.getEditableText().toString());
            stopTransactions.setDirection(String.valueOf(acdirection.getSelectedItem()));
            stopTransactions.setFastened_to(String.valueOf(acfastenedTo.getSelectedItem()));
            stopTransactions.setCounty(String.valueOf(acounty.getSelectedItem()));
            stopTransactions.setPosition(String.valueOf(acposition.getSelectedItem()));
            Gson gson = new Gson();
            String transaction = gson.toJson(stopTransactions);
            Bundle args = new Bundle();
            args.putString("stopTransaction", transaction);

            StopFragment2 stopFragment2 = new StopFragment2();
            replaceFragment(stopFragment2);
        }
    };

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
