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

public class SaveOpeningStockService {
	
	Context context;
	public SaveOpeningStockService(Context context){
		this.context=context;
	}
	
	
	
	public void submitSaveOpeningStock(
			String CommoMstId,String varietyMstID,String gradeId,String openingdate,String procQty,String totdeliveryQty,
			String totSbnd,String totReadyStock,String totAuctionQty,String desc,String futureAucQty,String futureDeliveryQty,
			  boolean flag, final HttpResponseListener<JSONObject> hhtpResponseListener){
			
		  
			String zone = SaveSharedPreference.getzonecode(context);
			String userid =SaveSharedPreference.getUserCode(context);
			
			
			String centerName = SaveSharedPreference.getBranchName(context);
			System.out.println("CENTER NAME====="+centerName);
			String centerCode = SaveSharedPreference.getBranchCode(context);
			if(flag==true){
				openingdate = AllUtils.getFormattedDateForSql(openingdate);
			}
			
			centerName=centerName.replace(" ", "_");
			//zonecode,centerCode,procDate,ginningName,GinningCenter,commoMst,commoVarietyMst,commoGrade,qty,seasonMst,user
			String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=saveOpeningStock&CENTER_CODE="+centerCode+
																									   "&ZONE="+zone+
																									   "&COMMO_MST_ID="+CommoMstId+
																									   "&COMMO_VAR_MST_ID="+varietyMstID+
																									   "&COMMO_GRADE="+gradeId+
																									   "&STATUS_DATE="+openingdate+
																									   "&PORC_VALUE="+procQty+
																									   "&TOT_DELV_VALUE="+totdeliveryQty+
																									   "&TOT_SBND="+totSbnd+
																									   "&TOT_READY_STOCK="+totReadyStock+
																									   "&TOT_AUCTION="+totAuctionQty+
																									   "&USER_ID="+userid+
																									   "&DESC="+desc+"" +
																								   		"&FUTURE_DELIVERY_QTY="+futureDeliveryQty+
																								   		"&FUTURE_AUCTION_QTY="+futureAucQty;
			
			
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
