package com.example.nftastops.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.CustomExpandableListAdaptor;
import com.example.nftastops.ExpandableListData;
import com.example.nftastops.R;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.ui.history.HistoryFragment;
import com.example.nftastops.ui.serviceRequest.ServiceRequestFragment;
import com.example.nftastops.ui.stops.StopFragment1;
import com.example.nftastops.ui.stops.StopRemoveFragment;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ExpandableListView expandableListView;
    CustomExpandableListAdaptor expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private AppBarConfiguration mAppBarConfiguration;
    public List<ServiceRequests> serviceRequests;
    private NetworkAPICall apiCAll;
    private int openServiceRequests;
    Integer count = new Integer(0);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        expandableListView = (ExpandableListView) root.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdaptor(getActivity(), expandableListTitle, expandableListDetail, count);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                if (expandableListDetail.get(
                        expandableListTitle.get(i)).isEmpty()) {
                    Fragment fragment = null;
                    switch (i) {
                        case 1:
                            fragment = new ServiceRequestFragment();
                            replaceFragment(fragment);
                            break;

                        case 2:
                            fragment = new HistoryFragment();
                            replaceFragment(fragment);
                            break;
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();

                Fragment fragment = null;
                String childName = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition);
                switch (childName) {
                    case "Add stop":
                        fragment = StopFragment1.newInstance("new", "");
                        replaceFragment(fragment);
                        break;

                    case "Remove stop":
                        fragment = StopRemoveFragment.newInstance("remove", "");
                        replaceFragment(fragment);
                        break;

                    case "Update stop":
                        fragment = StopFragment1.newInstance("update", "");
                        replaceFragment(fragment);
                        break;

                }

                return false;
            }
        });

        //serviceRequests = new ArrayList<>();
        apiCAll = NetworkAPICall.getInstance(getActivity());
        makeApiCall("serviceRequest");
        return root;
    }


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void makeApiCall(String url) {
        apiCAll.makeGet(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<ServiceRequests> results = new ArrayList<>();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ServiceRequests>>() {
                    }.getType();
                    results = gson.fromJson(response, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (results != null && !results.isEmpty()) {
                    count = 0;
                    for (ServiceRequests ele : results) {
                        if (ele.getStatus() != null && ele.getStatus().equals(Constants.OPEN)) {
                            count += 1;
                        }
                    }

                }
                expandableListAdapter.setRequestCount(count);
                expandableListAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in showing Service Requests";
                System.out.println(errorString);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}