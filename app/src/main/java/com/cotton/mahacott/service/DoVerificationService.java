package com.cotton.mahacott.service;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.AllUtils;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

import android.content.Context;

public class DoVerificationService {

	Context context;
	
	public DoVerificationService(Context context){
		
		this.context = context;
	}
	
	public void getBuyerCode(String doNumber,String doDate,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		doDate = AllUtils.getFormattedDateForSqlDashnew(doDate);
		String zoneCode = SaveSharedPreference.getzonecode(context);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=buyerCode&doNum="+doNumber.trim()+"&doDate="+doDate.trim()+"&zoneCode="+zoneCode+"";
		
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("DO Number Verify Response: " + response.toString());
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
