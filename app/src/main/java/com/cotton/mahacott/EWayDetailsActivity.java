package com.cotton.mahacott;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.DateUtilityService;
import com.cotton.mahacott.service.DeliveryOrderService;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EWayDetailsActivity extends AppCompatActivity {

    String buyerCode,contractNo,contractDate,gross,tare,net,
            gateNo,delvDate,lorryNo,doMstId,slotApproveId,slotId,
            noOfBales,lateLiftCharge,sgst,cgst,igst,lotNo,certiNo,
            shortAmt,shortWt,contractPrefix;

    EditText mEdtAddress1;
    EditText mEdtAddress2;
    EditText mEdtCity;
    EditText mEdtPincode;
    EditText mEdtApproxDistance;
    AutoCompleteTextView mActState;
    Button mBtnSave;
    List<String> stateNameList = new ArrayList<>();
    List<String> stateCodeList = new ArrayList<>();
    ArrayAdapter stateAdapter;
    Context mContext;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eway_details);

        mContext = getApplicationContext();
        mEdtAddress1 = findViewById(R.id.edt_address1);
        mEdtAddress2 = findViewById(R.id.edt_address2);
        mEdtCity = findViewById(R.id.edt_city);
        mEdtPincode = findViewById(R.id.edt_pincode);
        mEdtApproxDistance = findViewById(R.id.edt_approx_distance);
        mActState = findViewById(R.id.act_state);
        mBtnSave = findViewById(R.id.btnSave);

        buyerCode = getIntent().getExtras().getString("BUYER_CODE");
        contractNo = getIntent().getExtras().getString("CONTRACT_NO");
        contractDate = getIntent().getExtras().getString("CONTRACT_DATE");
        contractPrefix = getIntent().getExtras().getString("CONTRACT_PRE");
        gross = getIntent().getExtras().getString("GROSS_WT");
        tare = getIntent().getExtras().getString("TARE_WT");
        net = getIntent().getExtras().getString("NET_WT");
        gateNo = getIntent().getExtras().getString("GATE_NO");
        delvDate = getIntent().getExtras().getString("DELV_DATE");
        lorryNo = getIntent().getExtras().getString("LORRY_NO");
        doMstId = getIntent().getExtras().getString("DO_MST_ID");
        slotApproveId = getIntent().getExtras().getString("SLOT_APPROV_ID");
        noOfBales = getIntent().getExtras().getString("NO_OF_BALES");
        lateLiftCharge = getIntent().getExtras().getString("LATE_LIFT_CHARGE");
        sgst = getIntent().getExtras().getString("SGST");
        cgst = getIntent().getExtras().getString("CGST");
        igst = getIntent().getExtras().getString("IGST");
        lotNo = getIntent().getExtras().getString("LOT_NO");
        certiNo = getIntent().getExtras().getString("CERTI_NO");
        shortAmt = getIntent().getExtras().getString("SHORT_AMT");
        shortWt = getIntent().getExtras().getString("SHORT_WT");
        slotId = getIntent().getExtras().getString("SLOT_ID");

        getStateHelp();

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(mEdtAddress1.getText().toString().matches("") ||
               mEdtAddress2.getText().toString().matches("") ||
               mEdtCity.getText().toString().matches("") ||
               mEdtPincode.getText().toString().matches("") ||
               mEdtApproxDistance.getText().toString().matches("")
               )
            {
                Toast.makeText(mContext,"One or more field is empty",Toast.LENGTH_SHORT).show();
            }else{
//  @ashwin not in live             openAlert(getWindow().getDecorView());
            }
            }
        });

        mEdtPincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    DateUtilityService distanceService = new DateUtilityService(mContext);

                    distanceService.getApproxDistance(mEdtPincode.getText().toString(),new HttpResponseListener<JSONObject>() {
                        @Override
                        public void getResponse(JSONObject response) {
                            try {

                                String distance = response.getString("text");
                                String[] mArray =  distance.split(" ");
                                mEdtApproxDistance.setText(mArray[0]);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });


    }

//    public void openAlert(View view) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder
//                .setMessage("Are you sure,You want to save data");
//        alertDialogBuilder.setPositiveButton("yes",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                        int index = stateNameList.indexOf(mActState.getText().toString());
//                        String stateCode = stateCodeList.get(index);
//
//                        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
//                                getApplicationContext());
//
//                        deliveryOrderService.saveDeliveryDetails(buyerCode,
//                                contractNo,
//                                contractDate,
//                                gross,
//                                tare,
//                                net,
//                                gateNo,
//                                delvDate,
//                                lorryNo,
//                                doMstId,
//                                contractPrefix,
//                                slotApproveId,
//                                noOfBales,
//                                lateLiftCharge,
//                                sgst,
//                                cgst,
//                                igst,
//                                lotNo,
//                                certiNo,
//                                shortAmt,
//                                shortWt,
//                                slotId,
//                                mEdtAddress1.getText().toString(),
//                                mEdtAddress2.getText().toString(),
//                                mEdtCity.getText().toString(),
//                                mEdtPincode.getText().toString(),
//                                stateCode,
//                                mEdtApproxDistance.getText().toString(),
//                                new HttpResponseListener<JSONObject>() {
//
//                                    @Override
//                                    public void getResponse(JSONObject response) {
//
//                                        try {
//                                            System.out.println("DATA SAVED"+ response.getString("DeliveryDetailsId").toString());
////                                            Toast.makeText(getApplicationContext(), "Delivery" + response.getString("DeliveryDetailsId").toString(), Toast.LENGTH_LONG)
////                                                    .show();
//                                            pop(response.getString("DeliveryDetailsId").toString());
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                });
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("No",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

    public void getStateHelp(){

        DateUtilityService stateListService = new DateUtilityService(EWayDetailsActivity.this);
        stateListService.getStateList(new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("Information");
                    if (jsonArray.length() < 1) {
                        Toast.makeText(getApplicationContext(),
                                "state list empty", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        JSONObject jsonObj = new JSONObject();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObj = (JSONObject) jsonArray.get(i);
                            String stateCode = jsonObj.getString("STATECODE");
                            String stateName = jsonObj.getString("STATENAME");
                            //stateList.add(donum);
                            stateNameList.add(stateName);
                            stateCodeList.add(stateCode);
                        }

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stateAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.custom_spinner_item, stateNameList);
                mActState .setAdapter(stateAdapter);
                mActState.setThreshold(0);
                mActState.setValidator(new Validator());
            }
        });

    }

    class Validator implements AutoCompleteTextView.Validator {

        @Override
        public boolean isValid(CharSequence text) {
            Log.v("Test", "Checking if valid: "+ text);
            Collections.sort(stateNameList);
            if (Collections.binarySearch(stateNameList, mActState.getText().toString()) > 0) {
                return true;
            }

            return false;
        }

        @Override
        public CharSequence fixText(CharSequence invalidText) {
            Log.v("Test", "Returning fixed text");

            /* I'm just returning an empty string here, so the field will be blanked,
             * but you could put any kind of action here, like popping up a dialog?
             *
             * Whatever value you return here must be in the list of valid words.
             */
            return "";
        }
    }

    public void pop(String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(EWayDetailsActivity.this).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(mContext,HomeActivity.class));
                    }
                });
        alertDialog.show();
    }

    public void showProgress(){
        progress = new ProgressDialog(mContext);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

    }
}
