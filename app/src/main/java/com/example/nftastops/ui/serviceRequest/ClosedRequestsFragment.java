package com.example.nftastops.ui.serviceRequest;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
//import android.support.annotation.Nullable;
import androidx.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nftastops.R;

public class ClosedRequestsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.closed_requests,container,false);
        return v;
    }
}