package com.cotton.mahacott.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestConnection {

	private static RequestConnection requestConnection;
    private RequestQueue requestQueue;
    private static Context cntxt;

    private RequestConnection(Context context){

        cntxt = context;
        requestQueue =getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(cntxt.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized RequestConnection getRequestConnection(Context context){

        if(requestConnection==null){
            requestConnection = new RequestConnection(context);
        }
        return requestConnection;
    }

    public<T> void addRequestQue(Request<T> request ){

        requestQueue.add(request);
    }
}
