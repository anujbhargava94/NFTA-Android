package com.example.nftastops.ui.serviceRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.nftastops.R;

public class ServiceRequestFragment extends Fragment {
    //private SendViewModel sendViewModel;
    Toolbar toolbar;
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Events"};
    int Numboftabs =2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        sendViewModel =
//                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
       // final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar = root.findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        //adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);
        adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,Numboftabs);



        // Assigning ViewPager View and setting the adapter
        //view_pager = (ViewPager) findViewById(R.id.pager);
        view_pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        //tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs =  root.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // TODO::Setting Custom Color for the Scroll bar indicator of the Tab View
        //tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
        //    @Override
        //    public int getIndicatorColor(int position) {
        //        return getResources().getColor(R.color.tabsScrollColor);
        //    }
        //});

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);
        return root;
    }
}
