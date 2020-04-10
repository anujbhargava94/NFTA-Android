package com.example.nftastops.ui.serviceRequest;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import android.support.annotation.Nullable;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.stops.StopFragment1;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.example.nftastops.utilclasses.recyclerView.RVAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClosedRequestsFragment extends Fragment {
    public List<StopTransactions> stopTransactions;
    RVAdapter adapter;
    private NetworkAPICall apiCAll;
    private List<ServiceRequests> serviceRequests;
    RecyclerView rv;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ClosedRequestsFragment(List<StopTransactions> mStopTransactions) {
        //super(fm);

        this.stopTransactions = mStopTransactions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.closed_requests, container, false);

        rv = root.findViewById(R.id.rv);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        rv.setLayoutManager(llm);
//        stopTransactions = new ArrayList<>();
//        serviceRequests = new ArrayList<>();
      //  getClosedTransactions();
//        adapter = new RVAdapter(stopTransactions, onItemClickListener, "serviceRequest");
//        rv.setAdapter(adapter);
//        apiCAll = NetworkAPICall.getInstance(getActivity());
//        makeApiCall("serviceRequest");

        return root;
    }

    private void getClosedTransactions() {
        String results = SharedPrefUtil.getRawTasksFromSharedPrefs(getActivity(), Constants.CLOSESRKEY);
        stopTransactions = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<List<StopTransactions>>() {
        }.getType();
        List<StopTransactions> closeTransaction = gson.fromJson(results, type);
        if (closeTransaction != null) stopTransactions.addAll(closeTransaction);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new RVAdapter(stopTransactions, onItemClickListener, "serviceRequest");
        rv.setAdapter(adapter);
        adapter.setData(stopTransactions);
    }

//    private void makeApiCall(String url) {
//        apiCAll.makeGet(getActivity(), url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                //String results = response;
//                List<ServiceRequests> results = new ArrayList<>();
//                try {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<ServiceRequests>>() {
//                    }.getType();
//                    results = gson.fromJson(response, type);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (results == null || results.isEmpty()) {
//                    results = new ArrayList<>();
//                    ServiceRequests e = new ServiceRequests();
//                    e.setRequest_id(0);
//                    e.setAdmin_user_id(0);
//                    e.setRequested_user("Requested User");
//                    e.setAdditional_information("Additional Info");
//                    e.setDirection("Direction");
//                    e.setReason("Reason");
//                    e.setLocation("Location");
//                    e.setRoute("Route");
//                    e.setStopId(0);
//                    results.add(e);
//                }
//                //IMPORTANT: set data here and notify
//                serviceRequests.addAll(results);
//                castToStopTransaction(serviceRequests);
//                adapter.notifyDataSetChanged();
//                //Call constructor of ServiceRequestFragment
//                //new ServiceRequestFragment(serviceRequests);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String errorString = "Error in showing Service Requests";
//                System.out.println(errorString);
//            }
//        });
//    }

//    private void castToStopTransaction(List<ServiceRequests> serviceRequests) {
//        //Map serviceRequests with stopTransactions RVadaptor
//        for (ServiceRequests serviceRequest : serviceRequests) {
//            StopTransactions stopTransaction = new StopTransactions();
//            if (serviceRequest != null
//                    && serviceRequest.getStatus() != null
//                    && serviceRequest.getStatus().equals("resolved")) {
//                stopTransaction.setStop_id(serviceRequest.getStopId().toString());
//                stopTransaction.setRequest_type(serviceRequest.getRequest_type());
//                stopTransaction.setLocation(serviceRequest.getLocation());
//                stopTransaction.setDirection(serviceRequest.getDirection());
//                stopTransaction.setRequest_id(serviceRequest.getRequest_id());
//                stopTransaction.setAdmin_user_id(serviceRequest.getAdmin_user_id());
//                stopTransaction.setRequested_user(serviceRequest.getRequested_user());
//                //stopTransaction.setRoute(serviceRequest.getRoute());
//                stopTransaction.setReason(serviceRequest.getReason());
//                stopTransaction.setAdditional_information(serviceRequest.getAdditional_information());
//                stopTransaction.setStatus(serviceRequest.getStatus());
//                stopTransactions.add(stopTransaction);
//            }
//        }
//    }

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

    @Override
    public void onResume() {
        super.onResume();
        getClosedTransactions();
        Log.d("lifecycle", "On resume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("lifecycle", "On Start");
    }

    public void setData(List<StopTransactions> transactions) {
        this.stopTransactions = transactions;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("lifecycle", "On view");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("lifecycle", "On attach");
    }
    //    public void notifyCustomAdapterChange() {
//        this.adapter.notifyDataSetChanged();
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getClosedTransactions();
        } else {
        }
    }
}