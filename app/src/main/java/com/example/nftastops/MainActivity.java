package com.example.nftastops;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.model.LoginJwt;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //apiCAll = NetworkAPICall.getInstance(this);
        //Passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_stops, R.id.nav_service_request, R.id.nav_history, R.id.nav_stops_remove)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        dologinJwt(this);
    }

    private void dologinJwt(final Context context){
        apiCAll.makeLoginJwt(this, "","", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //String results = response;
                Log.d("jwt","Login response"+response);
                LoginJwt results = new LoginJwt();
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<LoginJwt>() {
                    }.getType();
                    results = gson.fromJson(response, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(results.getToken()!=null) {
                    Log.d("login","Logged In");
                    Constants.token = results.getToken();
                    Toast.makeText(
                            context,
                            "Logged In", Toast.LENGTH_SHORT
                    ).show();
                }else {
                    Log.d("login","Logged In unsuccess");
                    Toast.makeText(
                            context,
                            "Log In unsuccessful", Toast.LENGTH_SHORT
                    ).show();
                }
                //IMPORTANT: set data here and notify
                //Call constructor of ServiceRequestFragment
                //new ServiceRequestFragment(serviceRequests);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in Login";
                System.out.println(errorString);
            }
        });
    }

    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "username=anuj2@gmail.com&password=anuj7");
            Request request = new Request.Builder()
                    .url(Constants.baseURL + "login")
                    .method("POST", body)
                    .build();
//            try {
////                Response response = client.newCall(request).execute();
////                response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        return "";
        }
    }

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


//    @Override public void onBackPressed() {
//        //Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_service_request);
//        /**Fragment value is null**/
//        Fragment fragment  = getSupportFragmentManager().findFragmentByTag("service_request_detailed_fragment");
//        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
//            super.onBackPressed();
//        }
//    }

}
