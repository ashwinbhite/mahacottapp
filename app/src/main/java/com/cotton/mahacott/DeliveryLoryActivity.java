package com.cotton.mahacott;


import android.app.DatePickerDialog;
import android.text.InputFilter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.DeliveryOrderService;
import com.cotton.mahacott.service.ReportsService;
import com.cotton.mahacott.util.AllUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DeliveryLoryActivity extends AppCompatActivity implements
        HttpResponseListener<JSONObject> {

    private String currentWorkingDate, strNetWeight, strGrossWeight,
            strTareWeight;
    EditText edttxtGatePassNumber, edtTxtLorryNo, edtGrossWeight, edtNoOfBales,
            edtTareWeight, edtNetWeight, edtAllotmentNumber, edtTxtLotNo;
    Button btnDeliveryDate, btnBack, btnSave;
    TextView txtcgst, txtsgst, txtigst, txttotal;
    int year, month, date;
    String selectedDoDate;
    String certiNo = "", shortAmt = "", shortWt = "";
    String flagvalue = "";
    Double netWt,balnceQty,dobalnceQty;

    private String strDeliveryDate, strLateLiftingCharges, strCgst, strSgst, strIgst, StrTotalLateLifting;
    Date cDate = null;
    TextView txtQtyApproved, txtNoOfBales, txtLotNo, txtAllotmentNumber, txtLateLift, txtLotwiseRemainingQty;
    static final int CURRENT_DATE_DIALOG_ID = 0;
    String strAllotmentNo = "", strCommodity = "", strCommoMstId = "",
            strDOMstId = "", strDoNumber = "", strDoBalance = "",
            strDoQty = "", strContractPrefix = "", strContractno = "",
            strContractDate = "", strRegion = "", strBuyerName = "",
            strBuyerCode = "", strDoBalanceQty = "0", strSlotDetsilsId = "", strSlotQty = "", strSlotId = "";
    String strQuantityApproved = "0", strSlotAprrovalId = "0", strNoOfBales = "0", lotBalQty = "0";
    String myear,mMonth,mDate;

    EditText EdttxtCerti, EdttxtShortAmt, EdttxtShortWt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_lory);

        Log.d("date condn--",String.valueOf(System.currentTimeMillis()));
        Log.d("date 1000--",String.valueOf(System.currentTimeMillis()-1000));

        edttxtGatePassNumber = (EditText) findViewById(R.id.edttxtGatePassNumber);
        edtTxtLorryNo = (EditText) findViewById(R.id.edtTxtLorryNo);
        edtGrossWeight = (EditText) findViewById(R.id.edtGrossWeight);
        edtTareWeight = (EditText) findViewById(R.id.edtTareWeight);
        edtTxtLotNo = (EditText) findViewById(R.id.edtTxtLotNo);
        edtAllotmentNumber = (EditText) findViewById(R.id.edtAllotmentNumber);

        EdttxtCerti = (EditText) findViewById(R.id.txtCerti);
        EdttxtShortAmt = (EditText) findViewById(R.id.txtShortAmt);
        EdttxtShortWt = (EditText) findViewById(R.id.txtShortWt);

        txtNoOfBales = (TextView) findViewById(R.id.txtNoOfBales);
        txtLotNo = (TextView) findViewById(R.id.txtLotNo);
        txtLotwiseRemainingQty = (TextView) findViewById(R.id.txtLotwiseRemainingQty);
        edtNoOfBales = (EditText) findViewById(R.id.edtNoOfBales);
        edtNetWeight = (EditText) findViewById(R.id.edtNetWeight);
        btnDeliveryDate = (Button) findViewById(R.id.btnDeliveryDate);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtQtyApproved = (TextView) findViewById(R.id.txtQtyApproved);
        txtAllotmentNumber = (TextView) findViewById(R.id.txtAllotmentNumber);
        txtLateLift = (TextView) findViewById(R.id.txtLateLift);
        txtcgst = (TextView) findViewById(R.id.txtcgst);
        txtsgst = (TextView) findViewById(R.id.txtsgst);
        txtigst = (TextView) findViewById(R.id.txtigst);
        txttotal = (TextView) findViewById(R.id.txttotal);
        currentWorkingDate = getIntent().getExtras().getString("CURRENT_DATE");
        String[] arrDate= currentWorkingDate.split("/");
        myear  =  arrDate[2];
        mMonth = arrDate[1];
        mDate  = arrDate[0];
        Log.d("sumit working date",myear
                +"/"+mMonth
                +"/"+mDate );
        edtTxtLorryNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        strCommodity = getIntent().getExtras().getString("COMMODITY");
        strCommoMstId = getIntent().getExtras().getString("COMMODITY_MST_ID");
        System.out.println("strCommoMstId===========check====" + strCommoMstId);
        strDOMstId = getIntent().getExtras().getString("DO_MST_ID");
        strDoNumber = getIntent().getExtras().getString("DO_NUMBER");
        strDoBalance = getIntent().getExtras().getString("DO_BALANCE");
        strDoQty = getIntent().getExtras().getString("DO_QUANTITY");

        strContractPrefix = getIntent().getExtras()
                .getString("CONTRACT_PREFIX");
        strContractno = getIntent().getExtras().getString("CONTRACT_NO");
        strContractDate = getIntent().getExtras().getString("CONTRACT_DATE");
        strRegion = getIntent().getExtras().getString("REGION");
        strBuyerName = getIntent().getExtras().getString("BUYER_NAME");
        strBuyerCode = getIntent().getExtras().getString("TRADER_CODE");
        strSlotDetsilsId = getIntent().getExtras().getString("SLOT_DETAILS_ID");
        strSlotQty = getIntent().getExtras().getString("SLOT_QTY");
        strSlotId = getIntent().getExtras().getString("SLOT_ID");
        strDeliveryDate=getIntent().getExtras().getString("DElIVERY_DATE");


        //edtAllotmentNumber.setText(strAllotmentNo);


        //Check Fields on CommodityMstId:
        System.out.println("MST ID................." + strCommoMstId);
        if (!strCommoMstId.equals("") || strCommoMstId != "") {
            if (strCommoMstId.equalsIgnoreCase("2")) {
                txtLotNo.setVisibility(View.GONE);
                txtNoOfBales.setVisibility(View.GONE);
                edtNoOfBales.setVisibility(View.GONE);
                edtTxtLotNo.setVisibility(View.GONE);
                edtAllotmentNumber.setVisibility(View.GONE);
                txtAllotmentNumber.setVisibility(View.GONE);
            } else {
                strAllotmentNo = getIntent().getExtras().getString("ALLOTMENT_NO");
                edtAllotmentNumber.setText(strAllotmentNo);
            }
        }



        //sumit 16-01-2020
       /* currentWorkingDate = AllUtils
                .getFormattedDateForXML(currentWorkingDate);
        System.out.println("CURRENT DATE BEFORE======" + currentWorkingDate);*/

        strDeliveryDate = AllUtils
                .getFormattedDateForXML(strDeliveryDate);
        System.out.println("CURRENT DATE======" + currentWorkingDate);




        btnDeliveryDate.setText(strDeliveryDate);

        btnDeliveryDate.setEnabled(false);

        getcheckDateValue();




        btnDeliveryDate.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                showDialog(CURRENT_DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

        if (currentWorkingDate != "") {

            btnDeliveryDate.setText(strDeliveryDate);
            selectedDoDate = btnDeliveryDate.getText().toString();

        } else {
            updateDate();
        }




        edtGrossWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    edtNetWeight.setEnabled(true);
                }
                //				if(!hasFocus){
                //					edtNetWeight.setEnabled(false);
                //				}
            }
        });

        edtTareWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (edtGrossWeight.getText().toString().length() < 1
                            || edtTareWeight.getText().toString().length() < 1) {

                        Toast.makeText(getApplicationContext(),
                                "Please enter weight..", Toast.LENGTH_SHORT).show();
                    } else {
                        strGrossWeight = edtGrossWeight.getText().toString();
                        strTareWeight = edtTareWeight.getText().toString();

                         netWt = Double.parseDouble(strGrossWeight)
                                - Double.parseDouble(strTareWeight);
                        DecimalFormat df = new DecimalFormat("0.00");
                        netWt = Double.parseDouble(df.format(netWt));

                         dobalnceQty=Double.parseDouble(strDoBalance);
                         balnceQty = Double.parseDouble(strSlotQty);
                         Log.e("bal & slot qty",dobalnceQty.toString()+","+balnceQty.toString()+","+netWt.toString());

                        if (netWt > balnceQty && strCommoMstId.equalsIgnoreCase("2")) {

                            Toast.makeText(getApplicationContext(), "Net wt should not be more than Slot qty", Toast.LENGTH_LONG).show();
                            edtNetWeight.setText("");
                        }
                        else if(netWt > dobalnceQty && strCommoMstId.equalsIgnoreCase("2"))
                        {
                            Toast.makeText(getApplicationContext(), "Net wt should not be more than Do balance qty", Toast.LENGTH_LONG).show();
                            edtNetWeight.setText("");
                        }



                        else {
                            //DecimalFormat df = new DecimalFormat("0.00");
                            if(netWt<1){
                                Toast.makeText(getApplicationContext(),"Net wt Cannot be zero",Toast.LENGTH_SHORT).show();

                                edtGrossWeight.setText("");
                                edtTareWeight.setText("");
                            }else{
                                strNetWeight = df.format(netWt);
                            }

                            edtNetWeight.setText(strNetWeight);
                            edtNetWeight.setEnabled(false);
                            edtGrossWeight.setEnabled(false);
                            edtTareWeight.setEnabled(false);

                            DeliveryOrderService deliveryOrderService = new DeliveryOrderService(getApplicationContext());
                            deliveryOrderService.getLateLiftingCharges(
                                    strContractDate,
                                    strContractno,
                                    edtNetWeight.getText().toString().trim(),
                                    strSlotId, strBuyerCode,btnDeliveryDate.getText().toString(),
                                    new HttpResponseListener<JSONObject>() {

                                        @Override
                                        public void getResponse(JSONObject response) {

                                            try {
                                                strLateLiftingCharges = response.getString("lateLiftingCharge");
                                                strCgst = response.getString("Cgst");
                                                strSgst = response.getString("Sgst");
                                                strIgst = response.getString("Igst");
                                                txtLateLift.setText(strLateLiftingCharges);
                                                txtcgst.setText(strCgst);
                                                txtsgst.setText(strSgst);
                                                txtigst.setText(strIgst);

                                                Double Total = Double.parseDouble(strLateLiftingCharges) + Double.parseDouble(strCgst) + Double.parseDouble(strSgst) + Double.parseDouble(strIgst);
                                                StrTotalLateLifting = String.valueOf(Total);
                                                txttotal.setText(StrTotalLateLifting);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    }
                }
            }
        });

        edtTxtLotNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (edtTxtLotNo.getText().toString().length() < 0) {
                        Toast.makeText(getApplicationContext(),
                                "Please enter LotNo..", Toast.LENGTH_SHORT)
                                .show();
                    } else {


                        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
                                getApplicationContext());


                        deliveryOrderService.validateLot(strContractDate, strContractno, edtTxtLotNo.getText().toString().trim(),
                                strDoNumber, new HttpResponseListener<JSONObject>() {

                                    @Override
                                    public void getResponse(JSONObject response) {
                                        // TODO Auto-generated method stub
                                        String strLotFlag;
                                        try {
                                            strLotFlag = response.getString("lotValidateFlag");

                                            if (strLotFlag.equalsIgnoreCase("1")) {

                                                DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
                                                        getApplicationContext());
                                                deliveryOrderService.getSlotDetails(strContractDate, strContractno,
                                                        edtTxtLotNo.getText().toString().trim(),
                                                        new HttpResponseListener<JSONObject>() {

                                                            @Override
                                                            public void getResponse(JSONObject response) {
                                                                // TODO Auto-generated method stub
                                                                System.out.println("SLOT RES SUCCESS"
                                                                        + response);
                                                                try {

                                                                    JSONArray jsonArray = new JSONArray();
                                                                    jsonArray = response
                                                                            .getJSONArray("Information");
                                                                    JSONObject jsonObj = new JSONObject();
                                                                    // System.out.println("SIZE==========="+jsonArray.length());

                                                                    if (jsonArray.length() < 1) {

                                                                        Toast.makeText(getApplicationContext(),
                                                                                "No Slot Approval Id..",
                                                                                Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        jsonObj = (JSONObject) jsonArray.get(0);
                                                                        strQuantityApproved = jsonObj.getString("QTY_APPROVED");
                                                                        txtQtyApproved.setText("Approved Bales:" + strQuantityApproved);

                                                                        System.out.println("strCommoMstId===--------" + strCommoMstId);
                                                                        if (strCommoMstId.equalsIgnoreCase("1")) {
                                                                            System.out.println("in if*************");
                                                                            strSlotAprrovalId = jsonObj.getString("SLOT_APPROVAL_ID");

                                                                        } else {
                                                                            System.out.println("in else*************");
                                                                            strSlotAprrovalId = "0";
                                                                        }


                                                                        getLotwiseRemainingQty();
                                                                    }

                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Invalid Lot", Toast.LENGTH_SHORT).show();
                                                edtTxtLotNo.setText("");
                                                txtLotwiseRemainingQty.setVisibility(View.GONE);
                                                txtQtyApproved.setVisibility(View.GONE);
                                            }
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Send Data Back
                Intent intent = new Intent(getApplicationContext(), DeliveryOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", btnDeliveryDate.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (strCommoMstId.equalsIgnoreCase("1")) {
                    if (edttxtGatePassNumber.getText().toString().length() < 1
                            || edtTxtLorryNo.getText().toString().length() < 1
                            || edtGrossWeight.getText().toString().length() < 1
                            || edtTareWeight.getText().toString().length() < 1
                            || edtNetWeight.getText().toString().length() < 1
                            || edtTxtLotNo.getText().toString().length() < 1) {
                        Toast.makeText(getApplicationContext(),
                                "Fill all the details..", Toast.LENGTH_SHORT)
                                .show();

                    }

                 /*   if (netWt > balnceQty && strCommoMstId.equalsIgnoreCase("2")) {

                        Toast.makeText(getApplicationContext(), "Net wt should not be more than Slot qty", Toast.LENGTH_LONG).show();
                        edtNetWeight.setText("");
                    }
                    else if(netWt > dobalnceQty && strCommoMstId.equalsIgnoreCase("2"))
                    {
                        Toast.makeText(getApplicationContext(), "Net wt should not be more than Do balance qty", Toast.LENGTH_LONG).show();
                        edtNetWeight.setText("");
                    }*/


                    else {
                        System.out.println("BALES=============" + edtNoOfBales.getText().toString());
                        System.out.println("DO BALANCE======" + strDoBalance);
                        if (Double.parseDouble(edtNoOfBales.getText().toString()) > Double.parseDouble(lotBalQty)) {
                            Toast.makeText(getApplicationContext(), "No of bales can't be more than lot balance qty.", Toast.LENGTH_LONG).show();
                            edtNoOfBales.setText("");
                        }




                        else if (Double.parseDouble(edtNoOfBales.getText().toString()) < Double.parseDouble(lotBalQty)) {
                            openAlertPartialdelivery(getWindow().getDecorView());
                        } else
                        if(netWt > dobalnceQty){
                            Toast.makeText(getApplicationContext(),
                                    "Net qty cannot be more than balance qty", Toast.LENGTH_SHORT)
                                    .show();
                        }else {
                            openAlert(getWindow().getDecorView());
                        }
                    }
                }



                else {
                    if (edttxtGatePassNumber.getText().toString().length() < 1
                            || edtTxtLorryNo.getText().toString().length() < 1
                            || edtGrossWeight.getText().toString().length() < 1
                            || edtTareWeight.getText().toString().length() < 1
                            || edtNetWeight.getText().toString().length() < 1
                    ) {
                        Toast.makeText(getApplicationContext(),
                                "Fill all the details..", Toast.LENGTH_SHORT)
                                .show();

                    }


                    else {

                        if(netWt > dobalnceQty){
                            Toast.makeText(getApplicationContext(),
                                    "Net qty cannot be more than balance qty", Toast.LENGTH_SHORT)
                                    .show();
                        }else{
                            openAlert(getWindow().getDecorView());
                        }

                    }
                }
            }
        });
    }

    private void updateDate() {


        btnDeliveryDate.setText(new StringBuilder()
                .append(date < 10 ? ("0" + date) : (date)).append("-")
                .append((month < 9 ? ("0" + (month + 1)) : (month + 1)))
                .append("-").append(year).append(" "));

        selectedDoDate = date + "-" + (month + 1) + "-" + year;

        strDeliveryDate = btnDeliveryDate.getText().toString();
        strDeliveryDate = AllUtils
                .getFormattedDateForSqlDashnew(strDeliveryDate);
        System.out.println("DeliveryDate=======after button click --------"
                + strDeliveryDate);

    }

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            System.out.println("DatePicker: inside from date condition"
                    + view.getId());
            year = year;
            month = monthOfYear;
            date = dayOfMonth;


            updateDate();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CURRENT_DATE_DIALOG_ID:
                DatePickerDialog dateDialog=new DatePickerDialog(this, dateSetListener, year, month, date);
                dateDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                String myDate = myear+"/"+mMonth+"/"+mDate+" 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millis = date.getTime();

                dateDialog.getDatePicker().setMinDate(millis);
                Log.d("date condn--",String.valueOf(System.currentTimeMillis()));
                return  dateDialog;
        }
        return null;
    }

    public void onClick(View v) {

        String strchequeDate = btnDeliveryDate.getText().toString();
        // getInvoiceNumberList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            cDate = (Date) sdf.parse(strDeliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to save & proceed");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // ------------------------SAVE
                        // DATA-----------------------------

                        if (strCommoMstId.equalsIgnoreCase("2")) {
                            //COTTON SEEDS
                            strNoOfBales = "0";
                            strSlotAprrovalId = "0";
                        } else
                            strNoOfBales = edtNoOfBales.getText().toString();
                        certiNo = EdttxtCerti.getText().toString();
                        shortAmt = EdttxtShortAmt.getText().toString();
                        shortWt = EdttxtShortWt.getText().toString();

        //Note:This code is not in current(1.4) live Mahacott version

//                        Intent intent = new Intent(DeliveryLoryActivity.this,EWayDetailsActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("BUYER_CODE", strBuyerCode);
//                        bundle.putString("CONTRACT_NO", strContractno);
//                        bundle.putString("CONTRACT_DATE", strContractDate);
//                        bundle.putString("GROSS_WT", edtGrossWeight.getText().toString());
//                        bundle.putString("TARE_WT", edtTareWeight.getText().toString());
//                        bundle.putString("NET_WT", edtNetWeight.getText().toString());
//                        bundle.putString("GATE_NO", edttxtGatePassNumber.getText().toString());
//                        bundle.putString("DELV_DATE", btnDeliveryDate.getText().toString());
//                        bundle.putString("LORRY_NO",  edtTxtLorryNo.getText().toString());
//                        bundle.putString("DO_MST_ID", strDOMstId);
//                        bundle.putString("CONTRACT_PRE", strContractPrefix);
//                        bundle.putString("SLOT_APPROV_ID", strSlotAprrovalId);
//                        bundle.putString("NO_OF_BALES", strNoOfBales);
//                        bundle.putString("LATE_LIFT_CHARGE", txtLateLift.getText().toString().trim());
//                        bundle.putString("SGST", txtsgst.getText().toString().trim());
//                        bundle.putString("CGST", txtcgst.getText().toString().trim());
//                        bundle.putString("IGST", txtigst.getText().toString().trim());
//                        bundle.putString("LOT_NO", edtTxtLotNo.getText().toString());
//                        bundle.putString("CERTI_NO", certiNo);
//                        bundle.putString("SHORT_AMT", shortAmt);
//                        bundle.putString("SHORT_WT", shortWt);
//                        bundle.putString("SLOT_ID", strSlotId);
//                        intent.putExtras(bundle);
//                        startActivity(intent);




                         DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
                                getApplicationContext());

                        deliveryOrderService.saveDeliveryDetails(strBuyerCode,
                                strContractno, strContractDate, edtGrossWeight
                                        .getText().toString(), edtTareWeight
                                        .getText().toString(), edtNetWeight
                                        .getText().toString(),
                                edttxtGatePassNumber.getText().toString(),
                                btnDeliveryDate.getText().toString(),
                                edtTxtLorryNo.getText().toString(), strDOMstId,
                                strContractPrefix, strSlotAprrovalId,
                                strNoOfBales, txtLateLift.getText().toString().trim(),
                                txtsgst.getText().toString().trim(),
                                txtcgst.getText().toString().trim(),
                                txtigst.getText().toString().trim(),
                                edtTxtLotNo.getText().toString(),
                                certiNo, shortAmt, shortWt, strSlotId,
                                new HttpResponseListener<JSONObject>() {

                                    @Override
                                    public void getResponse(JSONObject response) {

                                        try {
                                            System.out.println("DATA SAVED"
                                                    + response.getString("DeliveryDetailsId").toString());
                                            Toast.makeText(getApplicationContext(), "Delivery" + response.getString("DeliveryDetailsId").toString(), Toast.LENGTH_LONG)
                                                    .show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        clear();
                                    }
                                });
                        // -------------------------------------------------------------------------------
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void openAlertPartialdelivery(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Do You Want to allow partial delivery ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // ------------------------SAVE
                        // DATA-----------------------------

                        if (strCommoMstId.equalsIgnoreCase("2")) {
                            //COTTON SEEDS
                            strNoOfBales = "0";
                            strSlotAprrovalId = "0";
                        } else
                            strNoOfBales = edtNoOfBales.getText().toString();
                        certiNo = EdttxtCerti.getText().toString();
                        shortAmt = EdttxtShortAmt.getText().toString();
                        shortWt = EdttxtShortWt.getText().toString();

                        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
                                getApplicationContext());
                        deliveryOrderService.saveDeliveryDetails(strBuyerCode,
                                strContractno, strContractDate, edtGrossWeight
                                        .getText().toString(), edtTareWeight
                                        .getText().toString(), edtNetWeight
                                        .getText().toString(),
                                edttxtGatePassNumber.getText().toString(),
                                btnDeliveryDate.getText().toString(),
                                edtTxtLorryNo.getText().toString(), strDOMstId,
                                strContractPrefix, strSlotAprrovalId,
                                strNoOfBales, txtLateLift.getText().toString().trim(),
                                txtsgst.getText().toString().trim(),
                                txtcgst.getText().toString().trim(),
                                txtigst.getText().toString().trim(),
                                edtTxtLotNo.getText().toString(),
                                certiNo, shortAmt, shortWt, strSlotId,
                                new HttpResponseListener<JSONObject>() {

                                    @Override
                                    public void getResponse(JSONObject response) {

                                        try {
                                            System.out.println("DATA SAVED"
                                                    + response.getString("DeliveryDetailsId").toString());
                                            Toast.makeText(getApplicationContext(), "Delivery" + response.getString("DeliveryDetailsId").toString(), Toast.LENGTH_LONG)
                                                    .show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        clear();
                                    }
                                });
                        // -------------------------------------------------------------------------------
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void getResponse(JSONObject response) {
        // TODO Auto-generated method stub

    }

    public void clear() {
        edtTxtLotNo.setText("");
        edttxtGatePassNumber.setText("");
        edttxtGatePassNumber.setText("");
        edtTxtLorryNo.setText("");
        edtGrossWeight.setText("");
        edtTareWeight.setText("");
        edtTxtLotNo.setText("");
        edtAllotmentNumber.setText("");
        edtNoOfBales.setText("");
        edtNetWeight.setText("");
        txtQtyApproved.setText("");

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DeliveryOrderDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("CURRENT_DATE", btnDeliveryDate.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getLotwiseRemainingQty() {

        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(getApplicationContext());
        deliveryOrderService.getLotwiseRemainingQty(strContractno, strContractDate, edtTxtLotNo.getText().toString(), strDoNumber, new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                // TODO Auto-generated method stub

                try {
                    lotBalQty = response.getString("BalQty");
                    System.out.println("-------------------BALANCE QTY--" + lotBalQty);
                    txtLotwiseRemainingQty.setText("Lot's Remaining Qty:" + lotBalQty);

                    //					if(Integer.parseInt(edtNoOfBales.getText().toString().trim())>balQty){
                    //						edtNoOfBales.setText("");
                    //						Toast.makeText(getApplicationContext(), "No of bales should be less than Lot's Remaining Qty", Toast.LENGTH_SHORT).show();
                    //					}

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }


    public void getcheckDateValue() {
        DeliveryOrderService deliveryOrderService=new DeliveryOrderService(getApplicationContext());
        deliveryOrderService.getCheckDate(new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {
                JSONArray jsonArray = new JSONArray();
                try {

                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    System.out.println("length of array is " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObj = (JSONObject) jsonArray.get(i);
                        flagvalue = jsonObj.getString("FLAGVALUE");


                    }
                    if(flagvalue.equalsIgnoreCase("Y"))
                    {
                        Log.d("Flagvaluechecked",flagvalue);
                        btnDeliveryDate.setEnabled(true);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}