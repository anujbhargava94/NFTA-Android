package com.example.nftastops.ui.stops;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.utilclasses.GPSTracker;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class StopRemoveFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    private TextInputLayout stopIdET;
    private TextInputLayout latET;
    private TextInputLayout longET;
    private StopTransactions stopTransactions;
    private TextInputLayout reason;
    private ImageView ivLat;
    private ImageView ivLong;
    private Button fetchButton;
    GPSTracker gpslocation;
    ImageView locPics;
    private TextView addPhoto;
    NetworkAPICall apiCAll;


    public StopRemoveFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static StopRemoveFragment newInstance(String param1, String param2) {
        StopRemoveFragment fragment = new StopRemoveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_remove_stop, container, false);
        Button nextButton = root.findViewById(R.id.fragmentSubmit);
        nextButton.setOnClickListener(nextOnClick);
        stopIdET = root.findViewById(R.id.stop_id);
        latET = root.findViewById(R.id.latitude);
        longET = root.findViewById(R.id.longitude);
        stopTransactions = new StopTransactions(getActivity());
        ivLat = root.findViewById(R.id.icLat);
        ivLong = root.findViewById(R.id.icLong);
        //fetchButton = root.findViewById(R.id.fetchButton);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        ivLat.setOnClickListener(latOnclickListner);
        ivLong.setOnClickListener(longOnclickListner);
        reason = root.findViewById(R.id.reason);
        locPics = root.findViewById(R.id.mypics);
        addPhoto = root.findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(addPhotoClickListner);
        //fetchButton.setOnClickListener(fetchOnClickListner);

        gpslocation = new GPSTracker(getActivity());

        latET.getEditText().setText(String.valueOf(gpslocation.getLatitude()));
        longET.getEditText().setText(String.valueOf(gpslocation.getLongitude()));

        return root;
    }

    View.OnClickListener nextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTransactions.setStop_id(stopIdET.getEditText().getText().toString());
            stopTransactions.setLatitude(Double.valueOf(latET.getEditText().getText().toString()));
            stopTransactions.setLongitude(Double.valueOf(longET.getEditText().getText().toString()));
            stopTransactions.setAdmin_comments(reason.getEditText().getText().toString());
            stopTransactions.setTransaction_type("remove");

            Gson gson = new Gson();
            String transaction = gson.toJson(stopTransactions);
            makeApiCall("add", transaction);

            Log.d("custom","Submit button pressed");
        }
    };

    View.OnClickListener latOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Location loc = null;
            double latitude = 0.0;
            if (gpslocation.canGetLocation()) {


                latitude = gpslocation.getLatitude();
            } else {
                gpslocation.showSettingsAlert();
            }
            latET.getEditText().setText(String.valueOf(latitude));
        }
    };

    View.OnClickListener longOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Location loc = null;
            double longitude = 0.0;
            if (gpslocation.canGetLocation()) {
                longitude = gpslocation.getLongitude();
            } else {
                gpslocation.showSettingsAlert();
            }

            longET.getEditText().setText(String.valueOf(longitude));
        }
    };

    View.OnClickListener addPhotoClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage(getActivity());
        }
    };

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void replaceFragment(androidx.fragment.app.Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void makeApiCall(String url, String request) {
        apiCAll.makePost(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String results = response;
                Log.d("response remove",response);
                Toast.makeText(
                        getContext(),
                        "Transaction added successfully", Toast.LENGTH_SHORT
                ).show();
                HomeFragment homeFragment = new HomeFragment();
                replaceFragment(homeFragment);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in adding Transaction";
                Toast.makeText(
                        getContext(),
                        errorString, Toast.LENGTH_SHORT
                ).show();
                System.out.println(errorString);
            }
        }, request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        locPics.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                locPics.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

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