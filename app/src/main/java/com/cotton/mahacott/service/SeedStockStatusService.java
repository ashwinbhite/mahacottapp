package com.cotton.mahacott.service;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.AllUtils;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;
import com.cotton.mahacott.util.SaveSharedPreference;

public class SeedStockStatusService {
	
	
Context context;
	
	public SeedStockStatusService(Context context){
		
		this.context = context;
	}
	
	public void getStockStatus(String CenterCode,String CommodityMstId,String CurrentDate,String SeasonStartDate,String SeasonEndDate,  final HttpResponseListener<JSONObject> hhtpResponseListener){
	
		String centerCode = SaveSharedPreference.getBranchCode(context);
		CurrentDate = AllUtils.getFormattedDateForSql(CurrentDate);
		System.out.println("CurrentDate---------Sumit"+CurrentDate);
		SeasonStartDate = AllUtils.getFormattedDateForSqlForSeasonDate(SeasonStartDate);
		SeasonEndDate = AllUtils.getFormattedDateForSqlForSeasonDate(SeasonEndDate);
		 
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getSeedStockStatus&CenterCode="+centerCode+"&CommodityMstId=2&CurrentDate="+CurrentDate+"&SeasonStartDate="+SeasonStartDate+"&SeasonEndDate="+SeasonEndDate+"";
		
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
	
	public void getSeasonDate(String CurrentDate,  final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		
		CurrentDate = AllUtils.getFormattedDateForSql(CurrentDate);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getSeasonDate&CurrentDate="+CurrentDate+"";
		
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
