package com.cotton.mahacott.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;


import org.json.JSONObject;

public class MobileVersionService {

    public void getAppVersion(Context context, final HttpResponseListener<JSONObject> hhtpResponseListener){

        String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getAppVersion";

        System.out.println("URL="+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("getAppVersion-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }
}
