package com.cotton.mahacott.service;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.ConstantPath;
import com.cotton.mahacott.util.RequestConnection;

import android.content.Context;

public class LoginService {
	
	Context context;
	String userName;
	public LoginService(Context context,String userName){
		
		this.context = context;
		this.userName = userName;
	}
	
	public void getUsernameDetails(final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=employelogin&loginName="+userName+"";
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("Response: " + response.toString());
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
	
	public void verifyPassword(String centerCode,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=getCenterCode&CenterCode="+centerCode+"";
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("CENTER CODE-Response: " + response.toString());
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
	
public void checkUserStatus(String userName,String zoneCode,final HttpResponseListener<JSONObject> hhtpResponseListener){
		
		String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=proLogin&loginname="+userName+"&Userflag=C&ZoneCode="+zoneCode+"";
		
		System.out.println("URL================"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url,null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println("STATUS-Response: " + response.toString());
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
	

public void forgotPassword(final HttpResponseListener<JSONObject> hhtpResponseListener){
	
	String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=forgotPassword&loginName="+userName;
	
	System.out.println("URL================"+url);
	JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
			Request.Method.GET, url,null, 
			new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					System.out.println("STATUS-Response: " + response.toString());
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

public void updatePassword(String currentPass,String previousPass,String loginName, final HttpResponseListener<JSONObject> hhtpResponseListener){
	
	String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=updatePassword&currentPass="+currentPass+
																											"&loginName="+loginName+
																											"&previousPass="+previousPass;
	
	System.out.println("URL================"+url);
	JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
			Request.Method.GET, url,null, 
			new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					System.out.println("STATUS-Response: " + response.toString());
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
	
public void insertUserLockDet(String userId, final HttpResponseListener<JSONObject> hhtpResponseListener){
	
	String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=insertUserLockDet&userId="+userId;
	
	System.out.println("URL================"+url);
	JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
			Request.Method.GET, url,null, 
			new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					System.out.println("STATUS-Response: " + response.toString());
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


public void logout(String userId, final HttpResponseListener<JSONObject> hhtpResponseListener){
	
	String url = ConstantPath.BASE_URL_NEW+"Mahacott/CallTrackingRequest?action=logout&userId="+userId;
	
	System.out.println("URL================"+url);
	JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
			Request.Method.GET, url,null, 
			new Response.Listener<JSONObject>() {

				@Override
				public void onResponse(JSONObject response) {
					System.out.println("STATUS-Response: " + response.toString());
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
