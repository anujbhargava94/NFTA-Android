package com.example.nftastops.ui.serviceRequest;


import android.os.Bundle;
//import android.support.annotation.Nullable;
import androidx.annotation.Nullable;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.stops.StopFragment1;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.recyclerView.RVAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OpenRequestsFragment extends Fragment {
    public List<StopTransactions> stopTransactions;
    RVAdapter adapter;
    private NetworkAPICall apiCAll;
    public List<ServiceRequests> serviceRequests;



    // Build a Constructor and assign the passed Values to appropriate values in the class
    public OpenRequestsFragment(List<StopTransactions> mstopTransactions) {
        //super(fm);

        this.stopTransactions = mstopTransactions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.open_requests,container,false);

        RecyclerView rv = root.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        stopTransactions = new ArrayList<>();
        serviceRequests = new ArrayList<>();
        adapter = new RVAdapter(stopTransactions,onItemClickListener);
        rv.setAdapter(adapter);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        makeApiCall("serviceRequest");

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
                if (results.isEmpty()) {
                    ServiceRequests e = new ServiceRequests();
                    e.setRequest_id(0);
                    e.setAdmin_user_id(0);
                    e.setRequested_user("Requested User");
                    e.setAdditional_information("Additional Info");
                    e.setDirection("Direction");
                    e.setReason("Reason");
                    e.setLocation("Location");
                    e.setRoute("Route");
                    e.setStopId(0);
                    results.add(e);
                }
                //IMPORTANT: set data here and notify
                serviceRequests.addAll(results);
                castToStopTransaction(serviceRequests);
                adapter.notifyDataSetChanged();
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

    private void castToStopTransaction(List<ServiceRequests> serviceRequests){
        //Map serviceRequests with stopTransactions RVadaptor
        for (ServiceRequests serviceRequest : serviceRequests){
            StopTransactions stopTransaction = new StopTransactions();
            if(serviceRequest.getStatus().equals("open")) {
                stopTransaction.setStop_id(serviceRequest.getStopId().toString());
                stopTransaction.setRequest_type(serviceRequest.getRequest_type());
                stopTransaction.setLocation(serviceRequest.getLocation());
                stopTransaction.setDirection(serviceRequest.getDirection());
                stopTransaction.setRequest_id(serviceRequest.getRequest_id());
                stopTransaction.setAdmin_user_id(serviceRequest.getAdmin_user_id());
                stopTransaction.setRequested_user(serviceRequest.getRequested_user());
                //stopTransaction.setRoute(serviceRequest.getRoute());
                stopTransaction.setReason(serviceRequest.getReason());
                stopTransaction.setAdditional_information(serviceRequest.getAdditional_information());
                stopTransactions.add(stopTransaction);
            }
        }
    }

    RVAdapter.OnItemClickListener onItemClickListener = new RVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(StopTransactions transaction) {

            Gson gson = new Gson();
            String transactionItem = gson.toJson(transaction);
            Bundle args = new Bundle();
            args.putString("serviceRequest", transactionItem);

            ServiceRequestDetailedFragment serviceRequestDetailedFragment = new ServiceRequestDetailedFragment();
            serviceRequestDetailedFragment.setArguments(args);
            replaceFragment(serviceRequestDetailedFragment);

        }
    };


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}



