package com.cotton.mahacott.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

import android.content.Context;
import android.util.Log;

public class DateUtilityService {

	Context context;
	
	public DateUtilityService(Context context){
		
		this.context = context;	
	}
	
	public void getDate(String versionName,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getdate&versionCode="+versionName;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("CURRENT DATE-Response: " + response.toString());
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

	public void getStateList(final HttpResponseListener<JSONObject> hhtpResponseListener){

		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getstatehelp";

		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("State:Response: " + response.toString());
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

	public void getApproxDistance(String destinationPinCode, final HttpResponseListener<JSONObject> hhtpResponseListener){

	    String centerPin = SaveSharedPreference.getPincode(context);
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+centerPin+"&destinations="+destinationPinCode+"&key=AIzaSyA2zKXQsPqqlQPEBWf3w8__cS_ujHBZgiQ";

		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("State:Response: " + response.toString());
						try{
							JSONArray jsonArray = new JSONArray();
							JSONArray jsonArray2 = new JSONArray();
							JSONObject jsonObj = new JSONObject();
							JSONObject jsonObj2 = new JSONObject();
							JSONObject jsonObjMain = new JSONObject();

							jsonArray= (JSONArray) response.getJSONArray("rows");
							jsonObj = (JSONObject) jsonArray.get(0);

							jsonArray2 = jsonObj.getJSONArray("elements");
							jsonObj2 = (JSONObject) jsonArray2.get(0);

							jsonObjMain = jsonObj2.getJSONObject("distance");
//							Log.e("DIST===",jsonObjMain.getString("text"));
							hhtpResponseListener.getResponse(jsonObjMain);
						}catch (JSONException e){
							e.printStackTrace();
						}


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
