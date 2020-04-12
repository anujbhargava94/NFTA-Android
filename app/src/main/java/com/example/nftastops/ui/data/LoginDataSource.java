package com.example.nftastops.ui.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.model.LoginJwt;
import com.example.nftastops.ui.data.model.LoggedInUser;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private NetworkAPICall apiCAll;
    Context context;

    public Result<LoggedInUser> login(Context context, String username, String password) {
    this.context = context;
        try {
            // TODO: handle loggedInUser authentication

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private void dologinJwt(final Context context, final String username, final String password){
        apiCAll.makeLoginJwt(context, username,password, new Response.Listener<String>() {
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

}
