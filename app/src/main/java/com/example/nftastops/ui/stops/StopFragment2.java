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
import android.os.Environment;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.CloudinaryResponse;
import com.example.nftastops.model.Dropdowns;
import com.example.nftastops.model.ImageItem;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.MultiSpinner;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.example.nftastops.utilclasses.imageRecyclerView.ImageCustomAdapter;
import com.example.nftastops.utilclasses.recyclerView.RVAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    private MultiSpinner acroutes;
    private Button submitButton;
    StopTransactions stopTransactions;
    NetworkAPICall apiCAll;
    private TextView addPhoto;
    ProgressDialog prgDialog;
    String imgPath;
    String encodedString;
    Bitmap selectedImage;
    String currentPhotoPath;
    String cloudImageUrl0;
    String cloudImageUrl1;
    String cloudImageUrl2;
    List<Dropdowns> routesDN;
    private int count = 0;
    LinearLayout picturesLL;
    List<ImageItem> imageItem;
    private RecyclerView imagerv;
    ImageCustomAdapter adapter;
    File photoFile;
    Uri photoURI;

    ImageItem currImage;

    public StopFragment2() {
        // Required empty public constructor

    }

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
        addPhoto = root.findViewById(R.id.add_photo);
        picturesLL = root.findViewById(R.id.pictures);

        addPhoto.setOnClickListener(addPhotoClickListner);
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setCancelable(false);
        imagerv = root.findViewById(R.id.rv_image);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);;
        imagerv.setLayoutManager(llm);
        imageItem = new ArrayList<>();
        adapter = new ImageCustomAdapter(imageItem);
        imagerv.setAdapter(adapter);


        Gson gson = new Gson();
        Type type = new TypeToken<List<Dropdowns>>() {
        }.getType();
        String routesR = SharedPrefUtil.getRawTasksFromSharedPrefs(getActivity(), Constants.ROUTE);

        routesDN = gson.fromJson(routesR, type);
        if (routesDN == null) {
            routesDN = new ArrayList<>();
        }
        routesDN.add(0, new Dropdowns("--Select--"));

//        ArrayAdapter<Dropdowns> routesAdapter = new ArrayAdapter<>
//                (getActivity(), android.R.layout.simple_list_item_1, routesDN);
//        acroutes.setAdapter(routesAdapter);


        acroutes.setItems(routesDN);

//        List<String> routeNames = new ArrayList<String>();
//        for (int i = 0; i < routesDN.size(); i++) {
//            routeNames.add(routesDN.get(i).getDisplay_name());
//        }
        //acroutes.setItems(routeNames);

        String stopTransactionJson = getArguments().getString("stopTransaction");
        stopTransactions = gson.fromJson(stopTransactionJson, StopTransactions.class);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        return root;
    }

    View.OnClickListener submitClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!verifyFields()){
                return;
            }
            submitButton.setEnabled(false);
            stopTransactions.setShelter(shelter.isChecked());
            stopTransactions.setAdvertisement(advertisement.isChecked());
            stopTransactions.setBench(bench.isChecked());
            stopTransactions.setBike_rack(bikeRack.isChecked());
            stopTransactions.setTrash_can(trashCan.isChecked());
            stopTransactions.setTime_table(timeTable.isChecked());
            stopTransactions.setSystem_map(systemMap.isChecked());
            stopTransactions.setAdmin_comments(comments.getEditText().getText().toString());

            stopTransactions.setRoute(acroutes.getSelectedItems());

            stopTransactions.setStatus(Constants.INPROGRESS);
            if (stopTransactions.getRequest_id() != null) {
                ServiceRequests serviceRequests = new ServiceRequests();
                serviceRequests.setRequest_id(stopTransactions.getRequest_id());
                stopTransactions.setWork_request(serviceRequests);
            } else {
                stopTransactions.setWork_request(null);
            }

            if (stopTransactions.getDirection().getDropdown_id() == null) {
                stopTransactions.setDirection(null);
            }
            if (stopTransactions.getRoute() == null
                    || stopTransactions.getRoute().isEmpty()
                    || stopTransactions.getRoute().get(0) == null
                    || stopTransactions.getRoute().get(0).getDropdown_id() == null) {
                stopTransactions.setRoute(null);
            }
            if (stopTransactions.getPosition().getDropdown_id() == null) {
                stopTransactions.setPosition(null);
            }
            if (stopTransactions.getCounty().getDropdown_id() == null) {
                stopTransactions.setCounty(null);
            }
            if (stopTransactions.getFastened_to().getDropdown_id() == null) {
                stopTransactions.setFastened_to(null);
            }
            triggerImageUpload();
        }
    };

    private boolean verifyFields() {
        return true;
    }

    View.OnClickListener addPhotoClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage(getActivity());
        }
    };

    private void makeApiCall(String url, String request) {
        Log.d("add api", "1");

        apiCAll.makePost(getActivity(), url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String results = response;
                HomeFragment homeFragment = new HomeFragment();
                replaceFragment(homeFragment);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null
                        && error.toString() != null
                        && error.toString().equals(("com.android.volley.TimeoutError"))) {
                    HomeFragment homeFragment = new HomeFragment();
                    replaceFragment(homeFragment);
                }
                submitButton.setEnabled(true);
                String errorString = "Error in adding Transaction";
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
        currImage = new ImageItem();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (imageItem.size() < 3) {
            builder.setTitle("Choose your profile picture");
        } else {
            Toast.makeText(
                    getActivity(),
                    "Can't allow more than 3 pictures", Toast.LENGTH_SHORT
            ).show();
            return;
        }

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        try {

                            photoFile = createImageFile(currImage);
                            currImage.setPhotoFile(photoFile);
                        } catch (IOException ex) {
                        }
                        if (photoFile != null) {
                            photoURI = FileProvider.getUriForFile(getActivity(),
                                    "com.example.nftastops.fileprovider",
                                    photoFile);
                            currImage.setPhotoURI(photoURI);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, 0);
                        }
                    }

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                        Bitmap bitmap;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), currImage.getPhotoURI());
                            bitmap = cropAndScale(bitmap, 300); // if you mind scaling
                            currImage.setImgBitmap(bitmap);
                            picturesLL.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Log.d("image", "not working");
                        }
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
                                //locPics[getImageCount()].setImageBitmap(BitmapFactory.decodeFile(imgPath));
                                currImage.setImgBitmap(BitmapFactory.decodeFile(imgPath));
                                picturesLL.setVisibility(View.VISIBLE);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
            imageItem.add(currImage);
            adapter.notifyDataSetChanged();
        }
    }

    public static Bitmap cropAndScale(Bitmap source, int scale) {
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    public class encodeImage extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            if (imgPath != null) {
                selectedImage = BitmapFactory.decodeFile(imgPath,
                        options);
            }
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
            triggerImageUpload();
        }
    }

    private void triggerImageUpload() {
        new OkHttpHandler().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public class OkHttpHandler extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            MediaType mediaType = MediaType.parse("text/plain");
            count = 0;
            for (int i = 0; i < imageItem.size(); i++) {
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("upload_preset", "nftafolder")
                        .addFormDataPart("file", imageItem.get(i).getImageFileName(),
                                RequestBody.create(mediaType, imageItem.get(i).getPhotoFile()))
                        .build();

                Request request = new Request.Builder()
                        .url("https://api.cloudinary.com/v1_1/nftaproject/image/upload")
                        .method("POST", body)
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Gson gson = new Gson();
                    Type type = new TypeToken<CloudinaryResponse>() {
                    }.getType();
                    CloudinaryResponse imageUrl = new CloudinaryResponse();
                    imageUrl = gson.fromJson(response.body().string(), type);
                    if (i == 0) {
                        cloudImageUrl0 = imageUrl.getUrl();
                        stopTransactions.setImage0(cloudImageUrl0);
                    } else if (i == 1) {
                        cloudImageUrl1 = imageUrl.getUrl();
                        stopTransactions.setImage1(cloudImageUrl1);
                    } else {
                        cloudImageUrl2 = imageUrl.getUrl();
                        stopTransactions.setImage2(cloudImageUrl2);
                    }
                    count += 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (count == imageItem.size()) {
                count = 0;
                Gson gson = new Gson();
                String transaction = gson.toJson(stopTransactions);
                makeApiCall("add", transaction);
            }
        }
    }


    private File createImageFile(ImageItem item) throws IOException {
        String stop_id = "";
        if (stopTransactions != null && stopTransactions.getStop_id() != null) {
            stop_id = stopTransactions.getStop_id();
        }
        String imageFileName;
        imageFileName = "Android_JPEG_" + "_" + stop_id;
        imageFileName = imageFileName.replace(" ", "");
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        item.setImageFileName(imageFileName);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
