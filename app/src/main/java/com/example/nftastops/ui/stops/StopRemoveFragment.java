package com.example.nftastops.ui.stops;

import android.app.Fragment;

public class StopRemoveFragment extends Fragment {
}

/*

For opening the fragment based on id
    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.aboutusButton:
                fragment = new AboutFragment();
                replaceFragment(fragment);
                break;

            case R.id.phbookButton:
                fragment = new PhoneBookFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    */