package com.example.nftastops.ui.serviceRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ServiceRequestFragment extends Fragment {
    //private SendViewModel sendViewModel;
    Toolbar toolbar;
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Open", "Closed"};
    int Numboftabs = 2;
    private NetworkAPICall apiCAll;
    private List<ServiceRequests> serviceRequests;
    private List<StopTransactions> stopTransactions;
    private List<StopTransactions> openStopTransactions;
    private List<StopTransactions> closedStopTransactions;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ServiceRequestFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_service_request, container, false);

        stopTransactions = new ArrayList<>();
        openStopTransactions = new ArrayList<>();
        closedStopTransactions = new ArrayList<>();
        adapter = new ViewPagerAdapter(getFragmentManager(), Titles, Numboftabs, stopTransactions);

        // Assigning ViewPager View and setting the adapter
        view_pager = root.findViewById(R.id.pager);
        view_pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        //tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs = root.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setViewPager(view_pager);
        serviceRequests = new ArrayList<>();

        return root;
    }

    private void makeApiCall(String url) {
        apiCAll.makeGet(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //String results = response;
                List<ServiceRequests> results = new ArrayList<>();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ServiceRequests>>() {
                    }.getType();
                    results = gson.fromJson(response, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (results == null || results.isEmpty()) {
                    results = new ArrayList<>();
                    ServiceRequests e = new ServiceRequests();
                    e.setRequest_id(0);
                    e.setAdmin_user_id(0);
                    e.setRequested_user("Requested User");
                    e.setAdditional_information("Additional Info");
                    e.setReason("Reason");
                    e.setLocation("Location");
                    results.add(e);
                }
                //IMPORTANT: set data here and notify
                serviceRequests.addAll(results);
                castToStopTransaction(serviceRequests);

                Gson gson = new Gson();
                String serviceRequests_str = gson.toJson(serviceRequests);
                String openStopTransactions_str = gson.toJson(openStopTransactions);
                String closedStopTransactions_str = gson.toJson(closedStopTransactions);

                SharedPrefUtil.saveTasksToSharedPrefs(getActivity(), serviceRequests_str, Constants.SRKEY);
                SharedPrefUtil.saveTasksToSharedPrefs(getActivity(), openStopTransactions_str, Constants.OPENSRKEY);
                SharedPrefUtil.saveTasksToSharedPrefs(getActivity(), closedStopTransactions_str, Constants.CLOSESRKEY);
                //adapter.notifyCustomAdapterChange();
                //adapter.notifyDataSetChanged();
                //Call constructor of ServiceRequestFragment
                //new ServiceRequestFragment(serviceRequests);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in showing Service Requests";
                System.out.println(errorString);
            }
        });
    }

    private void castToStopTransaction(List<ServiceRequests> serviceRequests) {
        //Map serviceRequests with stopTransactions RVadaptor
        for (ServiceRequests serviceRequest : serviceRequests) {
            StopTransactions stopTransaction = new StopTransactions(getActivity());
            if(serviceRequest.getStop_id() != null){
            stopTransaction.setStop_id(serviceRequest.getStop_id().toString());}
            stopTransaction.setRequest_type(serviceRequest.getRequest_type());
            stopTransaction.setLocation(serviceRequest.getLocation());
            stopTransaction.setDirection(serviceRequest.getDirection());
            stopTransaction.setRequest_id(serviceRequest.getRequest_id());
            stopTransaction.setAdmin_user_id(serviceRequest.getAdmin_user_id());
            stopTransaction.setRequested_user(serviceRequest.getRequested_user());
            stopTransaction.setRoutes(serviceRequest.getRoutes());
            stopTransaction.setReason(serviceRequest.getReason());
            stopTransaction.setAdditional_information(serviceRequest.getAdditional_information());
            stopTransaction.setStatus(serviceRequest.getStatus());
            stopTransaction.setImage0(serviceRequest.getImage0());
            stopTransaction.setImage1(serviceRequest.getImage1());
            stopTransaction.setImage2(serviceRequest.getImage2());
            stopTransaction.setPosition(serviceRequest.getPosition());
            stopTransaction.setFastened_to(serviceRequest.getFastened_to());
            stopTransaction.setCounty(serviceRequest.getCounty());

            if (serviceRequest != null
                    && serviceRequest.getStatus() != null
                    && serviceRequest.getStatus().equals(Constants.OPEN)) {
                openStopTransactions.add(stopTransaction);
            } else {
                closedStopTransactions.add(stopTransaction);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        apiCAll = NetworkAPICall.getInstance(getActivity());
        makeApiCall("serviceRequest");
        Log.d("lifecycle", "On resume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle", "On Start");
    }
}
