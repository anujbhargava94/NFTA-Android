package com.example.nftastops.utilclasses;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    public static void make(Context ctx, String query, Response.Listener<String>
            listener, Response.ErrorListener errorListener) {
        String url = baseURL + query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                listener, errorListener);
        NetworkAPICall.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
