package com.example.nftastops.ui.ui.login;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nftastops.R;
import com.example.nftastops.model.LoginJwt;
import com.example.nftastops.ui.data.LoginRepository;
import com.example.nftastops.utilclasses.Constants;
import com.example.nftastops.utilclasses.NetworkAPICall;
import com.example.nftastops.utilclasses.SharedPrefUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LoginViewModel extends ViewModel {
    private NetworkAPICall apiCAll;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private Context context;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult(Context context) {
        this.context = context;
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        //Result<LoggedInUser> result = loginRepository.login(context, username, password);
        dologinJwt(context, username, password);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
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
                    loginResult.setValue(new LoginResult(new LoggedInUserView(username)));

                } else {
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }
                //IMPORTANT: set data here and notify
                //Call constructor of ServiceRequestFragment
                //new ServiceRequestFragment(serviceRequests);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorString = "Error in Login";
                loginResult.setValue(new LoginResult(R.string.login_failed));
                Log.d("login", "LogIn Failed");
            }
        });
    }

}
