package com.example.nftastops.utilclasses;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        NetworkAPICall.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    public static void makePost(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener, final String mRequestBody) {
        String url = baseURL + query;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
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


//            @Override
//            protected Map<String, String> getParams() {
////                Map<String, String>  params = new HashMap<String, String>();
////                params.put("name", "Alif");
////                params.put("domain", "http://itsalif.info");
//
//                return params;
//            }

        NetworkAPICall.getInstance(ctx).addToRequestQueue(postRequest);
    }

    public static void makeLogin(Context ctx, final String username, final String password, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = baseURL + "login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                listener, errorListener) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                String mRequestBody = null;
                try {
                    String un = username;
                    String pw = password;
                    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                        un = "anuj2@gmail.com";
                        pw = "anuj7";
                    }
                    mRequestBody = "username=" + un + "&password=" + pw;
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        NetworkAPICall.getInstance(ctx).addToRequestQueue(postRequest);
    }
}
