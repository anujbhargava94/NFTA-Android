package com.example.nftastops.ui.stops;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StopFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StopFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox shelter;
    private CheckBox advertisement;
    private CheckBox bench;
    private CheckBox bikeRack;
    private CheckBox trashCan;
    private CheckBox timeTable;
    private CheckBox systemMap;
    private TextInputLayout comments;
    private Spinner acroutes;
    private Button submitButton;
    StopTransactions stopTransactions;
    NetworkAPICall apiCAll;
    ImageView locPics;
    private TextView addPhoto;
    ProgressDialog prgDialog;
    String imgPath, fileName;
    Bitmap bitmap;
    String encodedString;
    Bitmap selectedImage;


    public StopFragment2() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StopFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static StopFragment2 newInstance(String param1, String param2) {
        StopFragment2 fragment = new StopFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.stop_fragment2, container, false);
        shelter = root.findViewById(R.id.shelterId);
        advertisement = root.findViewById(R.id.advertisementId);
        bench = root.findViewById(R.id.benchId);
        bikeRack = root.findViewById(R.id.bikeRackId);
        trashCan = root.findViewById(R.id.trashCanId);
        timeTable = root.findViewById(R.id.timetableId);
        systemMap = root.findViewById(R.id.systemMapId);
        comments = root.findViewById(R.id.admin_comments);
        acroutes = root.findViewById(R.id.autocomplete_route);
        submitButton = root.findViewById(R.id.fragment2Next);
        submitButton.setOnClickListener(submitClick);
        locPics = root.findViewById(R.id.mypics);
        addPhoto = root.findViewById(R.id.add_photo);

        addPhoto.setOnClickListener(addPhotoClickListner);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setCancelable(false);


        String[] routes = getResources().getStringArray(R.array.routes);
        ArrayAdapter<String> routesAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, routes);
        acroutes.setAdapter(routesAdapter);

        String stopTransactionJson = getArguments().getString("stopTransaction");
        Gson gson = new Gson();
        stopTransactions = gson.fromJson(stopTransactionJson, StopTransactions.class);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        return root;
    }

    View.OnClickListener submitClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopTransactions.setShelter(shelter.isChecked());
            stopTransactions.setAdvertisement(advertisement.isChecked());
            stopTransactions.setBench(bench.isChecked());
            stopTransactions.setBike_rack(bikeRack.isChecked());
            stopTransactions.setTrash_can(trashCan.isChecked());
            stopTransactions.setTime_table(timeTable.isChecked());
            stopTransactions.setSystem_map(systemMap.isChecked());
            stopTransactions.setAdmin_comments(comments.getEditText().getText().toString());
            //stopTransactions.setRoute(String.valueOf(acroutes.getSelectedItem()));

//            Gson gson = new Gson();
//            String transaction = gson.toJson(stopTransactions);
            // makeApiCall("add", transaction);
            new encodeImage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    };

    View.OnClickListener addPhotoClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage(getActivity());
        }
    };

    private void makeApiCall(String url, String request) {


        apiCAll.makePost(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String results = response;
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

    public void replaceFragment(Fragment someFragment) {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.commit();
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage = (Bitmap) data.getExtras().get("data");
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
                                imgPath = cursor.getString(columnIndex);
                                locPics.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    public class encodeImage extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                if(imgPath!=null )
                {selectedImage = BitmapFactory.decodeFile(imgPath,
                        options);}
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                prgDialog.setMessage("Calling Upload");
                // Put converted Image string into Async Http Post param
                //params.put("image", encodedString);
                // Trigger Image upload
                triggerImageUpload();
            }
    }

    private void triggerImageUpload() {
        new OkHttpHandler().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public class OkHttpHandler extends AsyncTask<String, Void, Void> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected Void doInBackground(String...params) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload_preset", "nftafolder")
                    .addFormDataPart("file", encodedString)
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/nftaproject/image/upload")
                    .method("POST", body)
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
                Log.d("response", "image response"+response.body().string());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            String transaction = gson.toJson(stopTransactions);
            makeApiCall("add", transaction);
        }
    }
}
