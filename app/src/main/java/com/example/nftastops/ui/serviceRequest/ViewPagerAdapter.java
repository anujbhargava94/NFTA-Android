package com.example.nftastops.ui.serviceRequest;

//import android.support.v4.app.Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nftastops.model.StopTransactions;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private List<StopTransactions> stopTransactions; //serviceRequests to be passed to open and closed Requests Fragments
    private List<StopTransactions> openStopTransactions; //serviceRequests to be passed to open and closed Requests Fragments
    private List<StopTransactions> closedStopTransactions; //serviceRequests to be passed to open and closed Requests Fragments
    private OpenRequestsFragment tab1;
    private ClosedRequestsFragment tab2;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, List<StopTransactions> mStopTransaction) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.stopTransactions = mStopTransaction;
        openStopTransactions = new ArrayList<>();
        closedStopTransactions = new ArrayList<>();
        tab1 = new OpenRequestsFragment(openStopTransactions);
        tab2 = new ClosedRequestsFragment(closedStopTransactions);
        //filterTransactions();

    }

//    private void filterTransactions() {
//        if (stopTransactions != null)
//            for (StopTransactions transaction : stopTransactions) {
//                StopTransactions stopTransaction = new StopTransactions();
//                if (transaction != null
//                        && transaction.getStatus() != null) {
//                    if (transaction.getStatus().equals(Constants.OPEN)) {
//                        openStopTransactions.add(transaction);
//                    } else {
//                        closedStopTransactions.add(transaction);
//                    }
//                }
//            }
//    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            tab1 = new OpenRequestsFragment(openStopTransactions);
            return tab1;
        } else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            tab2 = new ClosedRequestsFragment(closedStopTransactions);
            return tab2;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public void notifyCustomAdapterChange() {
        //filterTransactions();
       // this.tab1.setData(openStopTransactions);
        //this.tab2.setData(closedStopTransactions);
    }

}