package com.example.nftastops.ui.stops;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.CloudinaryResponse;
import com.example.nftastops.model.ImageItem;
import com.example.nftastops.model.ServiceRequests;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.GPSTracker;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.imageRecyclerView.ImageCustomAdapter;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class StopRemoveFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputLayout stopIdET;
    private TextInputLayout latET;
    private TextInputLayout longET;
    private StopTransactions stopTransactions;
    private TextInputLayout reason;
    private ImageView ivLat;
    private ImageView ivLong;
    GPSTracker gpslocation;
    private TextView addPhoto;
    NetworkAPICall apiCAll;
    Button nextButton;
    String imgPath;
    String currentPhotoPath;
    String cloudImageUrl0;
    String cloudImageUrl1;
    String cloudImageUrl2;
    private int count = 0;
    LinearLayout picturesLL;
    List<ImageItem> imageItem;
    private RecyclerView imagerv;
    ImageCustomAdapter adapter;
    File photoFile;
    Uri photoURI;
    ImageItem currImage;

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

        View root = inflater.inflate(R.layout.fragment_remove_stop, container, false);
        nextButton = root.findViewById(R.id.fragmentSubmit);
        nextButton.setOnClickListener(nextOnClick);
        stopIdET = root.findViewById(R.id.stop_id);
        latET = root.findViewById(R.id.latitude);
        longET = root.findViewById(R.id.longitude);
        stopTransactions = new StopTransactions(getActivity());
        ivLat = root.findViewById(R.id.icLat);
        ivLong = root.findViewById(R.id.icLong);
        apiCAll = NetworkAPICall.getInstance(getActivity());
        ivLat.setOnClickListener(latOnclickListner);
        ivLong.setOnClickListener(longOnclickListner);
        reason = root.findViewById(R.id.reason);
        addPhoto = root.findViewById(R.id.add_photo);
        picturesLL = root.findViewById(R.id.pictures_sr);
        addPhoto = root.findViewById(R.id.add_photo);
        addPhoto.setOnClickListener(addPhotoClickListner);
        imagerv = root.findViewById(R.id.rv_image_sr);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        imagerv.setLayoutManager(llm);
        imageItem = new ArrayList<>();
        adapter = new ImageCustomAdapter(imageItem);
        imagerv.setAdapter(adapter);

        gpslocation = new GPSTracker(getActivity());

        latET.getEditText().setText(String.valueOf(gpslocation.getLatitude()));
        longET.getEditText().setText(String.valueOf(gpslocation.getLongitude()));
        Gson gson = new Gson();

        if (mParam2 != "") {
            String stopTransaction = mParam2;

            try {
                Type type2 = new TypeToken<StopTransactions>() {
                }.getType();
                stopTransactions = gson.fromJson(stopTransaction, type2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            stopIdET.getEditText().setText(stopTransactions.getStop_id());

        }

        return root;
    }

    View.OnClickListener nextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nextButton.setEnabled(false);
            stopTransactions.setStop_id(stopIdET.getEditText().getText().toString());
            stopTransactions.setLatitude(Double.valueOf(latET.getEditText().getText().toString()));
            stopTransactions.setLongitude(Double.valueOf(longET.getEditText().getText().toString()));
            stopTransactions.setAdmin_comments(reason.getEditText().getText().toString());
            stopTransactions.setStatus(Constants.INPROGRESS);
            stopTransactions.setTransaction_type("remove");

            if (stopTransactions.getRequest_id() != null) {
                ServiceRequests serviceRequests = new ServiceRequests();
                serviceRequests.setRequest_id(stopTransactions.getRequest_id());
                stopTransactions.setWork_request(serviceRequests);
            } else {
                stopTransactions.setWork_request(null);
            }

            triggerImageUpload();
            Log.d("custom", "Submit button pressed");
        }
    };

    View.OnClickListener latOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            double latitude = 0.0;
            double longitude = 0.0;
            if (gpslocation.canGetLocation()) {


                latitude = gpslocation.getLatitude();
                longitude = gpslocation.getLongitude();
            } else {
                gpslocation.showSettingsAlert();
            }
            latET.getEditText().setText(String.valueOf(latitude));
            longET.getEditText().setText(String.valueOf(longitude));
        }
    };

    View.OnClickListener longOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            double latitude = 0.0;
            double longitude = 0.0;
            if (gpslocation.canGetLocation()) {


                latitude = gpslocation.getLatitude();
                longitude = gpslocation.getLongitude();
            } else {
                gpslocation.showSettingsAlert();
            }
            latET.getEditText().setText(String.valueOf(latitude));
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
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, photoURI);
                        startActivityForResult(pickPhoto, 1);
                    }
                }else if (options[item].equals("Cancel")) {
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

                Log.d("response remove", response);
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
                nextButton.setEnabled(true);
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
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), currImage.getPhotoURI());
                            bitmap = cropAndScale(bitmap, 500); // if you mind scaling
                            currImage.setImgBitmap(bitmap);
                            picturesLL.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Log.d("image", "not working");
                        }
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            bitmap = cropAndScale(bitmap, 500);
                            currImage.setImgBitmap(bitmap);
                            ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(imageUri, "r");
                            InputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                            File file = new File(getActivity().getCacheDir(), currImage.getImageFileName());
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.copyStream(inputStream,fileOutputStream);
                            currImage.setPhotoFile(file);
                            picturesLL.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
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
        Date date = new Date();
        long time = date.getTime();
        imageFileName = "Android_JPEG_" + "_" + stop_id+time;
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