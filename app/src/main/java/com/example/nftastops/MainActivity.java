package com.example.nftastops;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.model.BaseResponse;
import com.example.nftastops.model.LoginJwt;
import com.example.nftastops.model.PingModel;
import com.example.nftastops.model.StopTransactions;
import com.example.nftastops.ui.home.HomeFragment;
import com.example.nftastops.ui.ui.login.LoginActivity;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.CertificatePinner;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private NetworkAPICall apiCAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //dologinJwt(this);
        callPingAPI(Constants.PING);
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
                    openLogin();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in Log in";
                System.out.println(errorString);
                openLogin();
            }
        });
    }

    private boolean isLoggedIn() {
        String token = SharedPrefUtil.getRawTasksFromSharedPrefs(this, Constants.TOKEN);
        return (token != null && !token.isEmpty());
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
}
