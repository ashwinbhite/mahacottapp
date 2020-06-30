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

public class SeedStockEntryService {

    Context context;

    public SeedStockEntryService(Context context) {

        this.context = context;
    }

    public void getGinningHelp(final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String strZoneCode = SaveSharedPreference.getzonecode(context);

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=GinningHelp&zoneCode=" + strZoneCode + "";

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GINNING HELP RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);


    }

    public void getVerietyHelp(final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=varietyhelp";

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("VERIETY HELP-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void getSeasonHelp(final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=seasonHelp";

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("SEASON HELP-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void getGradeHelp(final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=gradehelp";

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GRADE HELP-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void submitSeedStockEntry(String PressingDataId, String stockEntryDate, String CommoMstId, String varietyMstID, String gradeId, String qty, String seasonMstId, String oilPercent, String moisture, String heapNo, String saleType, final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String zone = SaveSharedPreference.getzonecode(context);
        String userid = SaveSharedPreference.getUserCode(context);

        seasonMstId=SaveSharedPreference.getSeasonMstId(context);
        String centerName = SaveSharedPreference.getBranchName(context);
        System.out.println("CENTER NAME=====" + centerName);
        String centerCode = SaveSharedPreference.getBranchCode(context);
        stockEntryDate = AllUtils.getFormattedDateForSql(stockEntryDate);
        centerName = centerName.replace(" ", "_");
        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=SaveTempCode&PressingDataId=" + PressingDataId + "&ZoneCode=" + zone + "&userid=" + userid + "&StockEntryDate=" + stockEntryDate + "&COMMODITY_MST_ID=" + CommoMstId + "&COMMO_VARIETY_MST_ID=" + varietyMstID + "&COMMO_GRADE=" + gradeId + "&NO_OF_BALES=" + qty + "&SEASON_MST_ID=" + seasonMstId + "&OIL_PERCENT=" + oilPercent + "&MOISTURE_PERCENT=" + moisture + "&JINNING_NAME=" + centerName + "&JINNING_CITY=" + centerName.trim() + "&CENTER_CODE=" + centerCode + "&HEAP_NO=" + heapNo + "&SALE_TYPE=" + saleType + "";

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("AFTER SUBMIT-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void getSeedStockEntry(String strDate,String saleType, final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String userId = SaveSharedPreference.getUserCode(context);
        String centerCode = SaveSharedPreference.getBranchCode(context);
        strDate = AllUtils.getFormattedDateForSql(strDate);

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=getSeedStockEntry&stockEntryDate=" + strDate + "&userId=" + userId + "&centerCode=" + centerCode + "&saleType="+saleType;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GET SEED STOCK RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);


    }

    public void getSeedStockQty(String stockType, String strDate, String commoMstId,
                                String commoVarMstId, String commoGrade,
                                final HttpResponseListener<JSONObject> hhtpResponseListener) {

        strDate = AllUtils.getFormattedDateForSql(strDate);
        String zone = SaveSharedPreference.getzonecode(context);
        String centerCode = SaveSharedPreference.getBranchCode(context);

        String url = ConstantPath.BASE_URL_NEW
                + "Mahacott/CallTrackingRequest?action=getSeedStock&FLAG=" + stockType + "&COMMO_MST_ID="
                + commoMstId + "&COMMO_VAR_MST_ID=" + commoVarMstId
                + "&COMMO_GRADE=" + commoGrade + "&ZONE=" + zone
                + "&CENTER_CODE=" + centerCode + "&DATE=" + strDate
                + "&START_DATE=01/Apr/2019&END_DATE=30/Mar/2020";

        System.out.println("URL==" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GET SEED STOCK QTY RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);


    }

    public void getIntimationDetails(String strgradeId, String strCommoMstId, String strVarMstId, final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String zone = SaveSharedPreference.getzonecode(context);
        String centerCode = SaveSharedPreference.getBranchCode(context);
        //String seasonMstID = "3";
        String seasonMstID = SaveSharedPreference.getSeasonMstId(context);

        String url = ConstantPath.BASE_URL_NEW
                + "Mahacott/CallTrackingRequest?action=getIntimationDetails&SEASON_MST_ID=" + seasonMstID + "&CENTER_CODE="
                + centerCode + "&ZONE=" + zone + "&COMMO_MST_ID=" + strCommoMstId + "&COMMO_VAR_MST_ID=" + strVarMstId + "&GRADE=" + strgradeId;

        System.out.println("URL==" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GET SEED STOCK QTY RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void sendIntimation(String slotDetailId, String intimatedQty, String traderCode, String contractString, String contractDate, final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String ZoneCode = SaveSharedPreference.getzonecode(context);
        String UserId = SaveSharedPreference.getUserCode(context);

        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=sendIntimation&zoneCode=" + ZoneCode + "&userId=" + UserId + "&traderCode=" + traderCode + "&quantity=" + intimatedQty + "&slotDetailsId=" + slotDetailId + "&contractString=" + contractString + "&contractDate=" + contractDate;

        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GRADE HELP-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void getReadyStockQuantity(String CommodityMstId, String varietyMstID,
                                      String gradeId, String selectSaleType, String CurrentDate,
                                      final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String zone = SaveSharedPreference.getzonecode(context);
        String centerCode = SaveSharedPreference.getBranchCode(context);
        CurrentDate = AllUtils.getFormattedDateForSql(CurrentDate);

        //zonecode,centerCode,procDate,ginningName,GinningCenter,commoMst,commoVarietyMst,commoGrade,qty,seasonMst,user
        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=getSeedStockQuantity&ZoneCode=" + zone +
                "&CenterCode=" + centerCode +
                "&CommodityMstId=2" +
                "&commoVarietyMstId=" + varietyMstID +
                "&commoGrade=" + gradeId +
                "&SaleType=S" +
                "&CurrentDate=" + CurrentDate + "";


        System.out.println("URL================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("AFTER SUBMIT-Response: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });
        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }


    public void getHeapCount(String CommoVarietyMstId,
                             String Grade,
                             String HeapNo,
                             String SeedSaleType,
                             final HttpResponseListener<JSONObject> hhtpResponseListener) {

        String centerCode = SaveSharedPreference.getBranchCode(context);
        String SeasonMstId = SaveSharedPreference.getSeasonMstId(context);
        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=getHeapNoCount&CommodityMstId=2"
                + "&CommoVarietyMstId=" + CommoVarietyMstId
                + "&Grade=" + Grade
                + "&CenterCode=" + centerCode
                + "&SeasonMstId=" + SeasonMstId
                + "&HeapNo=" + HeapNo
                + "&SeedSaleType=" + SeedSaleType + "";

        System.out.println("URL==" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GET SEED STOCK QTY RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });

        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }

    public void getSeedEntryCount(String mVarietyMstID,
                             String mGradeId,String CurrentDate,
                             String HeapNo,

                             final HttpResponseListener<JSONObject> hhtpResponseListener) {

       // String centerCode = SaveSharedPreference.getBranchCode(context);
        //String SeasonMstId = SaveSharedPreference.getSeasonMstId(context);
        CurrentDate = AllUtils.getFormattedDateForSql(CurrentDate);
        String url = ConstantPath.BASE_URL_NEW + "Mahacott/CallTrackingRequest?action=getSeedEntryCount&CommodityMstId=2"
                + "&CommoVarietyMstId=" + mVarietyMstID
                + "&Grade=" + mGradeId
                + "&PressingDate=" +CurrentDate
                + "&HeapNo=" + HeapNo + "";

        System.out.println("URL==" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("GET SEED STOCK QTY RESPONSE: " + response.toString());
                        hhtpResponseListener.getResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                error.printStackTrace();
            }
        });

        RequestConnection.getRequestConnection(context).addRequestQue(jsonObjectRequest);

    }
}
