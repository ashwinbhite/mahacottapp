package com.cotton.mahacott.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.AllUtils;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONObject;

public class ReportsService {

    Context context;

    public ReportsService(Context context){

        this.context = context;
    }

    public void getAutionDetails(String fromDate,String toDate,final HttpResponseListener<JSONObject> hhtpResponseListener){

        String centerCode = SaveSharedPreference.getBranchCode(context);
        Log.d("before",fromDate);

        fromDate = AllUtils.getUserFormatedDateForDisplay(fromDate);
        toDate = AllUtils.getUserFormatedDateForDisplay(toDate);
        Log.d("after=",fromDate);
        String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=GetAuctionDetailsReports&CenterCode="+centerCode+"&FromDate="+fromDate+"&ToDate="+toDate+"";

        System.out.println("URL===GetAuctionDetail============="+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GINNING HELP RESPONSE: " + response.toString());
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

    public void getPendingDoDetails(String fromDate,String toDate,final HttpResponseListener<JSONObject> hhtpResponseListener){

        String centerCode = SaveSharedPreference.getBranchCode(context);

        fromDate = AllUtils.getUserFormatedDateForDisplay(fromDate);
        toDate = AllUtils.getUserFormatedDateForDisplay(toDate);

        String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=GetPendingDoReports&CenterCode="+centerCode+"&FromDate="+fromDate+"&ToDate="+toDate+"";

        System.out.println("URL================"+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GINNING HELP RESPONSE: " + response.toString());
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
