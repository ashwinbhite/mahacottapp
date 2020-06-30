package com.cotton.mahacott.service;

import org.json.JSONObject;

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

public class ProcurementService {
	Context context;
	public ProcurementService(Context context){
		this.context=context;
	}
	
	
public void submitProcuremententry(String procDataId,
		String procurementDate,String CommoMstId,
		   String varietyMstID,String gradeId,String qty,String seasonMstId,
		   final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String zone = SaveSharedPreference.getzonecode(context);
		String userid =SaveSharedPreference.getUserCode(context);
	    String seasonmstId=SaveSharedPreference.getSeasonMstId(context);
	Log.d("SeasonMStId sUMIT",seasonMstId);
		
		
		String centerName = SaveSharedPreference.getBranchName(context);
		System.out.println("CENTER NAME====="+centerName);
		String centerCode = SaveSharedPreference.getBranchCode(context);
		procurementDate = AllUtils.getFormattedDateForSql(procurementDate);
		
		centerName=centerName.replace(" ", "_");
		//zonecode,centerCode,procDate,ginningName,GinningCenter,commoMst,commoVarietyMst,commoGrade,qty,seasonMst,user
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=SaveProcurement&ProcId="+procDataId+"&ZoneCode="+zone+
																								   "&CENTER_CODE="+centerCode+
																								   "&PROC_DATE="+procurementDate+
																								   "&GINNING_NAME="+centerName.trim()+
																								   "&GINNING_CENTER="+centerName.trim()+
																								   "&COMMODITY_MST_ID="+CommoMstId+
																								   "&COMMO_VARIETY_MST_ID="+varietyMstID+
																								   "&COMMO_GRADE="+gradeId+
																								   "&QTY="+qty+
																								   "&SEASON_MST_ID="+seasonmstId+
																								   "&USER_ID="+userid+"";
		
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("AFTER SUBMIT-Response: " + response.toString());
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

public void getProcurementData(
		String procurementDate,String ZoneCode,
		String CenterCode,
		String varietyMstID,String gradeId,
		   final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String zone = SaveSharedPreference.getzonecode(context);
		String userid =SaveSharedPreference.getUserCode(context);
		
		
		String centerName = SaveSharedPreference.getBranchName(context);
		System.out.println("CENTER NAME====="+centerName);
		String centerCode = SaveSharedPreference.getBranchCode(context);
		procurementDate = AllUtils.getFormattedDateForSql(procurementDate);
		
		centerName=centerName.replace(" ", "_");
		//zonecode,centerCode,procDate,ginningName,GinningCenter,commoMst,commoVarietyMst,commoGrade,qty,seasonMst,user
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getProcurementData&procDate="+procurementDate+
				                                                                                   "&ZoneCode="+zone+
																								   "&CenterCode="+centerCode+
																								   "&commoVarietyMstId="+varietyMstID+
																								   "&commoGrade="+gradeId+"";
																								  
		
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("AFTER SUBMIT-Response: " + response.toString());
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
