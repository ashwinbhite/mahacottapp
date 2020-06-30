package com.cotton.mahacott.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

import android.content.Context;

public class ProductionPercenteService {

	Context context;
	
	public ProductionPercenteService(Context ctx){
	
		context = ctx;
	}
	
	public void getProductionPercent(final HttpResponseListener<JSONObject> hhtpResponseListener){

		String pattern = "dd/MMM/yyyy";
		String dateInString =new SimpleDateFormat(pattern).format(new Date());
		String zone = SaveSharedPreference.getzonecode(context);
		String centerCode = SaveSharedPreference.getBranchCode(context);
		
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getProductionPercent&zoneCode="+zone+"&centerCode="+centerCode+"&Date="+dateInString;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("getProductionPercent-Response: " + response.toString());
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
