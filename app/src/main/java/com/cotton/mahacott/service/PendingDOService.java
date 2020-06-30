package com.cotton.mahacott.service;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

public class PendingDOService {
	
	Context context;
	public PendingDOService(Context context){
		this.context=context;
	}
	
	
public void getDoHelp(final HttpResponseListener<JSONObject> hhtpResponseListener){
	String zoneCode = SaveSharedPreference.getzonecode(context);
	String centerCode=SaveSharedPreference.getBranchCode(context);
	String commoMstId="2";
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getPendingDOhelp&centerCode="+centerCode+"&zoneCode="+zoneCode+"&commoMstId="+commoMstId;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null,  
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("VERIETY HELP-Response: " + response.toString());
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
