package com.example.nftastops.ui.serviceRequest;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nftastops.R;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
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
        //transaction.addToBackStack(null);
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getClosedTransactions();
        } else {
        }
    }
}