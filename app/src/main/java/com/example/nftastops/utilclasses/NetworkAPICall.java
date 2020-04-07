package com.example.nftastops.utilclasses;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nftastops.model.LoginRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.example.nftastops.utilclasses.Constants.baseURL;

public class NetworkAPICall {
    private static NetworkAPICall mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;


    public NetworkAPICall(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkAPICall getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkAPICall(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static void makeGet(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = baseURL + query;
        Log.d("login", "api called"+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener)
        {
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Log.d("login1", "map");
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization","Bearer "+Constants.token);
            return headers;
        }}
        ;
        NetworkAPICall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void makePost(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener, final String mRequestBody) {
        String url = baseURL + query;
        Log.d("login", "api called"+url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("login1", "map");
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization","Bearer "+Constants.token);
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };

        NetworkAPICall.getInstance(ctx).addToRequestQueue(postRequest);
    }

    public static void makeLoginJwt(Context ctx, final String username, final String password, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = baseURL + "authenticate";
        Log.d("login1", "api called"+url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener) {

            @Override
            public String getBodyContentType() {
                Log.d("login1", "body content type");
                return super.getBodyContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("login1", "map");
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return headers;
            }



            @Override
            public byte[] getBody() throws AuthFailureError {
                String mRequestBody = null;
                try {
                    Log.d("login1", "credentials entered");
                    String un = username;
                    String pw = password;
                    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                        un = "anujbhargava94@gmail.com";
                        pw = "password1";
                    }
                    LoginRequest lin = new LoginRequest();
                    lin.setUsername(un);
                    lin.setPassword(pw);
                    Gson gson = new Gson();
                    String linReq = gson.toJson(lin);
                    return linReq == null ? null : linReq.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        Log.d("login1", "making call");
        NetworkAPICall.getInstance(ctx).addToRequestQueue(postRequest);
    }
}
