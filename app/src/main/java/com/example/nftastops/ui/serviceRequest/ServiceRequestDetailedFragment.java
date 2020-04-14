package com.example.nftastops.ui.serviceRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.stops.StopFragment1;
import com.example.nftastops.ui.stops.StopRemoveFragment;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.IOnBackPressed;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * Use the {@link ServiceRequestDetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceRequestDetailedFragment extends Fragment implements IOnBackPressed {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    StopTransactions stopTransactions;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView location;
    private TextView direction;
    private TextView route;
    private TextView reason;
    private TextView stopId;
    private TextView additional_information;
    private TextView request_type;
    private Button proceedButton;


    public ServiceRequestDetailedFragment() {
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
    public static ServiceRequestDetailedFragment newInstance(String param1, String param2) {
        ServiceRequestDetailedFragment fragment = new ServiceRequestDetailedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceRequestDetailedFragment fragment = new ServiceRequestDetailedFragment();
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.add(fragment, "service_request_detailed_fragment");
        //FragmentTransaction.add(ServiceRequestDetailedFragment fragment, String tag );
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_service_request_detailed, container, false);
        stopId = root.findViewById(R.id.times);
        direction = root.findViewById(R.id.directionval);
        location = root.findViewById(R.id.locationval);
        request_type = root.findViewById(R.id.requesttypeval);
        reason = root.findViewById(R.id.reasonval);
        route = root.findViewById(R.id.routeval);
        additional_information = root.findViewById(R.id.additionalinformationval);
        proceedButton = root.findViewById(R.id.proceedbutton);
        proceedButton.setOnClickListener(proceedOnClick);
        String serviceRequest = getArguments().getString("serviceRequest");

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<StopTransactions>() {
            }.getType();
            stopTransactions = gson.fromJson(serviceRequest, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        stopId.setText(stopTransactions.getStop_id());
        if (stopTransactions.getDirection() != null) {
            direction.setText(stopTransactions.getDirection().getDisplay_name());
        }
        location.setText(stopTransactions.getLocation());
        request_type.setText(stopTransactions.getRequest_type());
        reason.setText(stopTransactions.getReason());
        route.setText(stopTransactions.getRoutesString());
        additional_information.setText(stopTransactions.getAdditional_information());

        if (stopTransactions.getStatus() != null
                && stopTransactions.getStatus().equals(Constants.OPEN)) {
            proceedButton.setVisibility(View.VISIBLE);
        }


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_service_request_detailed, container, false);
        return root;
    }

    View.OnClickListener proceedOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String request_type = stopTransactions.getRequest_type();
            Fragment fragment = null;
            Gson gson = new Gson();
            String transactionItem = gson.toJson(stopTransactions);
            switch (request_type) {
                case "New":
                //case "new":
                    //fragment = StopFragment1.newInstance("new", "");
                    //replaceFragment(fragment);
                    //Gson gson = new Gson();
                    //String transactionItem = gson.toJson(stopTransactions);
                    fragment = StopFragment1.newInstance("new", transactionItem);
                    replaceFragment(fragment);
                    break;

                case "Remove":
                //case "remove":
                    fragment = StopRemoveFragment.newInstance("remove", transactionItem);
                    replaceFragment(fragment);

                    break;

                case "Update":
                //case "update":
                    fragment = StopFragment1.newInstance("update", transactionItem);
                    replaceFragment(fragment);
                    break;
            }

        }
    };

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
