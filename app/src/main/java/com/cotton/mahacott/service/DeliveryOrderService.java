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

public class DeliveryOrderService {

	Context context;
	
	public DeliveryOrderService(Context context){
		
		this.context=context;
	}
	
	public void getDoDetails(String DoNumber,String doDate,String versionName,final HttpResponseListener<JSONObject> hhtpResponseListener){
		doDate=AllUtils.getFormattedDatenew(doDate);
		String zoneCode = SaveSharedPreference.getzonecode(context);
		String centerCode = SaveSharedPreference.getBranchCode(context);

		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getdoDetails&zoneCode="+zoneCode+"&doNumber="+DoNumber+"&doDate="+doDate+"&centerCode="+centerCode+"&versionCode="+versionName;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("Do Details-Response: " + response.toString());
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
	
	public void getDoQuantity(String DoNumber,String doDate,final HttpResponseListener<JSONObject> hhtpResponseListener){
		doDate=AllUtils.getFormattedDatenew(doDate);
		String zoneCode = SaveSharedPreference.getzonecode(context);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getdoQty&zoneCode="+zoneCode+"&doNumber="+DoNumber+"&doDate="+doDate+"";
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("DO QUANTITY-Response: " + response.toString());
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
	
	
	public void saveDeliveryDetails(String traderCode,String contractNo,String cntractDate,
			String grossWt,String tareWt,String netWt,String gatePassNo,
			String deliveryDate,String LorryNo,String doMstId,String contractPrefix,
			String strSlotAprrovalId,String strNoOfBales,String lateLiftigCharges,
			String sgst,String cgst,String igst,String lotNo,
			String certiNo,String shortAmt,String shortWt,String strSlotDetsilsId,
//			String address1,String address2,String city,String pincode,String stateCode,String approxDistance,
			final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String userId = SaveSharedPreference.getUserCode(context);
		cntractDate = AllUtils.getFormattedDateForCurrentDate(cntractDate.trim());
		deliveryDate =  AllUtils.getFormattedDateForSqlDashnew(deliveryDate.trim());
		
		String url = ConstantPath.BASE_URL_NEW+
				"Mahacott/CallTrackingRequest?action=SaveDeliveryDetails&traderCode="+traderCode+
				"&contractNo="+contractNo+"&ContractDate="+cntractDate.trim()+"&grossWt="+grossWt+
				"&tareWt="+tareWt+"&netWt="+netWt+"&gatePassNo="+gatePassNo+"&delivaryDate="+
				deliveryDate.trim()+"&LorryNo="+LorryNo+"&userId="+userId+"&doMstId="+doMstId+
				"&NoOfBales="+strNoOfBales+"&contractPrefix="+contractPrefix.trim()+
				"&slotId="+strSlotAprrovalId+"&lateLiftingCharge="+lateLiftigCharges+"&sgst="+sgst+"&cgst="+cgst+"&igst="+igst+"&lotNo="+lotNo
				+"&shortWeight="+shortWt+"&shortAmt="+shortAmt+"&shortCertiNo="+certiNo+"&slotDtlsId="+strSlotDetsilsId;
//				+"&address1="+address1+"&address2="+address2+"&city="+city+"&pincode="+pincode+"&stateid="+stateCode+"&approxDistance="+approxDistance;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("SAVED-Response: " + response.toString());
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
	
	public void getSlotDetails(String contractDate,String contractNo,String strLotNo,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		contractDate = AllUtils.getFormattedDateForCurrentDate(contractDate);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getSlotId&contractDate="+contractDate.trim()+"&contractNo="+contractNo+"&lotNo="+strLotNo;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("SLOT Details-Response: " + response.toString());
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
	
public void getLateLiftingCharges(String contractDate,String contractNo,String netWt,String slotDetailsId,String traderCode,String deliveryDate, final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		contractDate = AllUtils.getFormattedDateForCurrentDate(contractDate);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getLateLiftingCharge&traderCode="+traderCode+"&contractNo="+contractNo+"&contractDate="+contractDate.trim()+"&slotDetailId="+slotDetailsId+"&netWt="+netWt+"&deliveryDate="+deliveryDate+"";
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("LATE LIFTING CHARGE-Response: " + response.toString());
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
	
	public void getLotwiseRemainingQty(String contractNo,String contractDate,String lotNo,String strDoNo, final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		contractDate = AllUtils.getFormattedDateForCurrentDate(contractDate);
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getLotwiseRemainingQty&contractNo="+contractNo+"&contractDate="+contractDate+"&lotNo="+lotNo+"&doNo="+strDoNo;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {
	
					@Override
					public void onResponse(JSONObject response) {
						System.out.println("getLotwiseRemainingQty-Response: " + response.toString());
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
	
	public void validateLot(String contractDate,String contractNo,String strLotNo,String strDoNo,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		contractDate = AllUtils.getFormattedDateForCurrentDate(contractDate);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=validateLot&contractNo="+contractNo+"&contractDate="+contractDate+"&lotNo="+strLotNo+"&doNo="+strDoNo+"";
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("SLOT Details-Response: " + response.toString());
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
	 
	
	public void getDoWiseSlot(String doNo,
			String doDate,
			String contractNo,
			String contractDate,
			String contractPrefix,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		contractDate = AllUtils.getFormattedDateForCurrentDate(contractDate);
		String centerCode = SaveSharedPreference.getBranchCode(context);
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getDoWiseSlot&doNo="
		+doNo+"&doDate="+doDate+"&contractNo="+contractNo+"&centerCode="+centerCode+"&contractDate="+contractDate+"&contractPrefix="+contractPrefix;
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("SLOT Details-Response: " + response.toString());
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

	public void getCheckDate(final HttpResponseListener<JSONObject> hhtpResponseListener){



		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=checkDateFlag";


		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("SLOT Details-Response: " + response.toString());
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
