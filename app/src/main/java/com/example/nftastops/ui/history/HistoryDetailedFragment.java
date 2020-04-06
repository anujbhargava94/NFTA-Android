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

    private TextView location;
    private TextView direction;
    private TextView route;
    private TextView reason;
    private TextView stopId;
    private TextView additional_information;
    private TextView request_type;


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
        location = root.findViewById(R.id.locationval);
        request_type = root.findViewById(R.id.requesttypeval);
        reason = root.findViewById(R.id.reasonval);
        route = root.findViewById(R.id.routeval);
        additional_information = root.findViewById(R.id.additionalinformationval);
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
        direction.setText(stopTransactions.getDirection());
        location.setText(stopTransactions.getLocation());
        request_type.setText(stopTransactions.getRequest_type());
        reason.setText(stopTransactions.getReason());
        //route.setText(stopTransactions.getRoute());
        additional_information.setText(stopTransactions.getAdditional_information());


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

