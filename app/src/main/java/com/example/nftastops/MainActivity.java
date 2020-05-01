package com.example.nftastops;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.model.LoginJwt;
import com.example.nftastops.model.PingModel;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.ui.ui.login.LoginActivity;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.GPSTracker;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private NetworkAPICall apiCAll;

    private static MainActivity instance;
    GPSTracker gpslocation;
    Location location;
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION = 1;
    boolean loginInit = false;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView homeIcon = findViewById(R.id.icHome);
        homeIcon.setOnClickListener(homeOnclickListner);
        //apiCAll = NetworkAPICall.getInstance(this);
        //Passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_stops, R.id.nav_service_request, R.id.nav_history, R.id.nav_stops_remove)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.popBackStack();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (!isLoggedIn()) {
            Intent startupIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(startupIntent, 100);
        }
        String username = SharedPrefUtil.getRawTasksFromSharedPrefs(this, Constants.USERNAMEKEY);
        dologinJwt(this,username,Constants.NFTAPWD);

        gpslocation = new GPSTracker(this);
        location = gpslocation.getLocation();
       // callPingAPI(Constants.PING);
    }

    private void callPingAPI(String url) {
        apiCAll.makeGet(this, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //String results = response;
                Log.d("ping", "history response: " + response);
                PingModel results = new PingModel();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<PingModel>() {
                    }.getType();
                    results = gson.fromJson(response, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (results != null && !results.getResult().equals(Constants.SUCCESS) && results.getError().equals(Constants.UNAUTH)) {
                    if (isLoggedIn()) {openLogin();}
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in Log in";
                System.out.println(errorString);
                if (isLoggedIn()) {
                openLogin();}
            }
        });
    }

    private boolean isLoggedIn() {
        String username = SharedPrefUtil.getRawTasksFromSharedPrefs(this, Constants.USERNAMEKEY);
        return (username != null && !username.isEmpty());
    }


    private class UpdateTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "username=anuj2@gmail.com&password=anuj7");
            Request request = new Request.Builder()
                    .url(Constants.baseURL + "login")
                    .method("POST", body)
                    .build();
            return "";
        }
    }

    private View.OnClickListener homeOnclickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            replaceFragment(new HomeFragment());
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void replaceFragment(Fragment someFragment) {
        int count = 3;
        while (count > 0) {
            this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            count -= 1;
        }
    }

    private void openLogin() {
        Intent startupIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(startupIntent, 100);
    }


    private void getDropDowns(final Context context, final String url) {
        String dropDownUrl = "dropdown?dropdownType=" + url;
        apiCAll.makeGet(this, dropDownUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null || !response.isEmpty()) {
                    SharedPrefUtil.saveTasksToSharedPrefs(context, response, url);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in showing Service Requests";
                System.out.println(errorString);
            }
        });
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.getInstance(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_READ_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("custom","Permission granted");
                    //isGPSEnabled = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    private void dologinJwt(final Context context, final String username, final String password) {
        apiCAll.makeLoginJwt(context, username, password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //String results = response;
                Log.d("jwt", "Login response" + response);
                LoginJwt results = new LoginJwt();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<LoginJwt>() {
                    }.getType();
                    results = gson.fromJson(response, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (results.getToken() != null) {
                    Log.d("login", "Logged In");
                    SharedPrefUtil.saveTasksToSharedPrefs(context, results.getToken(), Constants.TOKEN);
                    getDropDowns(context, Constants.DIRECTION);
                    getDropDowns(context, Constants.POSITION);
                    getDropDowns(context, Constants.FASTENED);
                    getDropDowns(context, Constants.COUNTY);
                    getDropDowns(context, Constants.ROUTE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error!=null && error.toString()!=null && error.toString().equals("com.android.volley.AuthFailureError")){
                    openLogin();
                }
                String errorString = "Error in Login";
                Log.d("login", "LogIn Failed");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  this.getSupportFragmentManager().popBackStackImmediate();
    }
}
