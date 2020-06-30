package com.cotton.mahacott;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.DeliveryOrderService;
import com.cotton.mahacott.service.PendingDOService;
import com.cotton.mahacott.util.AllUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeliveryOrderDetailsActivity extends AppCompatActivity implements HttpResponseListener<JSONObject> {

    static final int CURRENT_DATE_DIALOG_ID = 0;
    Button btnDODate, btnNext;
    TextView lblCommodity, lblContractNo, edtDOQuantity, edtDOBalance, txtSlotWiseDet;
    EditText edtRegion, edtBuyerCode, edtBuyerName;
    AutoCompleteTextView edtTxtDOnumber;
    //Spinner selectSlotWiseDetails;
    Spinner selectSlotWiseDetails;
    String versionName="";


    ArrayList<String> varietyTypeList = new ArrayList<>();
    ArrayList<String> DoNumberlist = new ArrayList<>();
    ArrayList<String> DoDateList = new ArrayList<>();
    ArrayList<String> slotQtyRateList = new ArrayList<>();
    ArrayList<String> slotIdList = new ArrayList<>();
    ArrayList<String> slotDateList = new ArrayList<>();
    ArrayList<String> slotQtyList = new ArrayList<>();
    ArrayList<String> slotRateList = new ArrayList<>();
    Map<String, String> slotIdMap = new HashMap<>();
    Map<String, String> slotDateMap = new HashMap<>();
    Map<String, String> slotQtyMap = new HashMap<>();
    Map<String, String> slotRateMap = new HashMap<>();

    int year, month, date;
    Date cDate = null;

    ArrayAdapter<String> DOTypeAdapter;
    ArrayAdapter<String> slotAdapter;
    String strCommodity = "", strCommodityMstId = "", strContractDate = "",
            strContractNumber = "", strContractPrefix = "", strSeason = "",
            strSeasonMstId = "", strRegionName = "", strRegionCode = "",
            strDODate = "", strBuyerName = "", strBuyerCode = "",
            strSlotDetailsId = "", selectedQty = "", strSlotId = "", currentWorkingDate1,
            strDoQty = "0", StrDeliverdQty = "0", strDoMstId, strAllotmentNo, selectedDoDate, strDeliveryDate;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_order_details);
        btnDODate = (Button) findViewById(R.id.btnDODate);
        btnNext = (Button) findViewById(R.id.btnNext);
        edtTxtDOnumber = (AutoCompleteTextView) findViewById(R.id.edtTxtDOnumber);
        lblCommodity = (TextView) findViewById(R.id.selectCommodity);
        edtRegion = (EditText) findViewById(R.id.edtRegion);
        lblContractNo = (TextView) findViewById(R.id.lblContractNo);
        edtBuyerName = (EditText) findViewById(R.id.edtBuyerName);
        edtBuyerCode = (EditText) findViewById(R.id.edtBuyerCode);
        edtDOQuantity = (TextView) findViewById(R.id.edtDOQuantity);
        edtDOBalance = (TextView) findViewById(R.id.edtDOBalance);
        txtSlotWiseDet = (TextView) findViewById(R.id.txtSlotWiseDet);
        selectSlotWiseDetails = (Spinner) findViewById(R.id.selectSlot);


        currentWorkingDate1 = getIntent().getExtras().getString("CURRENT_DATE");
        currentWorkingDate1 = AllUtils.getFormattedDateForXML(currentWorkingDate1);
        System.out.println("CURRENT DATE======" + currentWorkingDate1);

        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("VERSION=", versionName);

        getDOnumber();



      selectSlotWiseDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedslot = selectSlotWiseDetails.getSelectedItem().toString();
                Log.e("here-1st--------", selectedslot);
                int pos = slotQtyRateList.indexOf(selectedslot);
                String strDate = slotDateList.get(pos);
                String strRate = slotRateList.get(pos);
                selectedQty = slotQtyList.get(pos);
                strSlotId = slotIdList.get(pos);

                strDate = AllUtils.getFormattedDateForAuction(strDate);
                txtSlotWiseDet.setText(" Date:" + strDate);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



 /*      selectSlotWiseDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String selectedslot = selectSlotWiseDetails.getText().toString();
                System.out.println("here-1st--------" + selectSlotWiseDetails.getText().toString());
                int pos = slotQtyRateList.indexOf(selectedslot);

//				Set set=slotIdMap.entrySet();//Converting to Set so that we can traverse
//			    Iterator itr=set.iterator();
//
//			    while(itr.hasNext()){
//			        //Converting to Map.Entry so that we can get key and value separately
//			        Map.Entry entry=(Map.Entry)itr.next();
//			        System.out.println(entry.getKey()+" "+entry.getValue());
//
//
//			    }


                String strDate = slotDateList.get(pos);
                String strRate = slotRateList.get(pos);
                selectedQty = slotQtyList.get(pos);
                strSlotId = slotIdList.get(pos);

                strDate = AllUtils.getFormattedDateForAuction(strDate);
                txtSlotWiseDet.setText(" Date:" + strDate);

                System.out.println("strDate=" + strDate);
                System.out.println("strRate=" + strRate);
                System.out.println("strQty=" + selectedQty);
                System.out.println("strSlotId=" + strSlotId);

            }
        });*/


        edtTxtDOnumber
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        closeKeyBoard();
                        System.out.println("position----" + position);
                        System.out.println("selected Item"
                                + parent.getItemAtPosition(position));

                        edtDOQuantity.setText("");
                        edtDOBalance.setText("");


                        //String SelectedDoNum = DoNumberlist.get(position+1);

                        //By sumit 26-12-2019
                        String SelectedDoNum = edtTxtDOnumber.getText().toString().trim();
                        int pos = DoNumberlist.indexOf(SelectedDoNum);
                        String SelectedDate = DoDateList.get(pos);
                        Log.d("SelectedDate=", SelectedDate);
                        //End-----//
                        SelectedDate = AllUtils
                                .getFormattedDateForAuction(SelectedDate);
                        btnDODate.setText(SelectedDate);
                        System.out.println("SelectedDoNum-------"
                                + SelectedDoNum);
                        // TODO Auto-generated method stub

                        if (SelectedDoNum.length() > 0) {
                            DeliveryOrderService deliveryService = new DeliveryOrderService(
                                    getApplicationContext());
                            deliveryService.getDoDetails(SelectedDoNum,
                                    SelectedDate,versionName,
                                    new HttpResponseListener<JSONObject>() {

                                        @Override
                                        public void getResponse(
                                                JSONObject response) {
                                            // TODO Auto-generated method stub

                                            try {
                                                JSONArray jsonArray = new JSONArray();
                                                jsonArray = response
                                                        .getJSONArray("Information");
                                                JSONObject jsonObj = new JSONObject();

                                                if (jsonArray.length() < 1) {
                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            "No DO-Details available..",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                } else {
                                                    jsonObj = (JSONObject) jsonArray
                                                            .get(0);
                                                    strCommodity = jsonObj
                                                            .getString("COMMODITY_DESC");
                                                    strCommodityMstId = jsonObj
                                                            .getString("COMMODITY_MST_ID");
                                                    strDoMstId = jsonObj
                                                            .getString("DO_MST_ID");
                                                    strContractDate = jsonObj
                                                            .getString("CONTRACT_DATE");
                                                    strContractNumber = jsonObj
                                                            .getString("CONTRACT_NO");
                                                    strContractPrefix = jsonObj
                                                            .getString("CONTRACT_PREFIX");
                                                    strSeason = jsonObj
                                                            .getString("SEASON_DESC");
                                                    strSeasonMstId = jsonObj
                                                            .getString("SEASON_MST_ID");
                                                    strRegionName = jsonObj
                                                            .getString("REGION_NAME");
                                                    strRegionCode = jsonObj
                                                            .getString("REGION_CODE");
                                                    strDODate = jsonObj
                                                            .getString("DO_DATE");
                                                    strSlotDetailsId = jsonObj
                                                            .getString("SLOT_DETAILS_ID");
                                                    strBuyerName = jsonObj
                                                            .getString("CUSTOMER_NAME");
                                                    strBuyerCode = jsonObj
                                                            .getString("TRADER_CODE");
                                                    if (strCommodityMstId
                                                            .equalsIgnoreCase("1"))
                                                        strAllotmentNo = jsonObj
                                                                .getString("ALLOTMENT_NO");

                                                    getQuantityDetails(
                                                            edtTxtDOnumber
                                                                    .getText()
                                                                    .toString(),
                                                            btnDODate.getText()
                                                                    .toString());

                                                    getSlotDetails(
                                                            edtTxtDOnumber
                                                                    .getText()
                                                                    .toString(),
                                                            btnDODate.getText()
                                                                    .toString(),
                                                            strContractNumber,
                                                            strContractDate,
                                                            strContractPrefix);

                                                }

                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }

                                            lblCommodity.setText(strCommodity);
                                            edtRegion.setText(strRegionName);
                                            lblContractNo
                                                    .setText(strContractNumber);
                                            edtBuyerName.setText(strBuyerName);
                                            edtBuyerCode.setText(strBuyerCode);
                                            // edtDOQuantity.setText(text);
                                            // edtDOBalance.setText();

                                        }

                                    });

                        }
                    }
                });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                closeKeyBoard();
                System.out.println("length of do number"
                        + edtTxtDOnumber.getText().toString().length());

                if (edtTxtDOnumber.getText().toString().length() < 1
                        || lblCommodity.getText().toString().length() < 1
                        || edtRegion.getText().toString().length() < 1
                        || lblContractNo.getText().toString().length() < 1
                        || edtBuyerName.getText().toString().length() < 1
                        || edtBuyerCode.getText().toString().length() < 1
                        || edtDOQuantity.getText().toString().length() < 1
                        || edtDOBalance.getText().toString().length() < 1
                        ||selectSlotWiseDetails.getSelectedItem().toString().length()<1) {
                    Toast.makeText(getApplicationContext(),
                            "Fill all the details..", Toast.LENGTH_SHORT)
                            .show();

                } if(Float.parseFloat(edtDOBalance.getText().toString())<1){
                    Toast.makeText(getApplicationContext(),
                            "DO has no balance..", Toast.LENGTH_SHORT)
                            .show();

                }else {

                    System.out.println("BTNNEXT HERE IN ELSE");
                    Intent intent = new Intent(getApplicationContext(),
                            DeliveryLoryActivity.class);
                    Bundle bundle = new Bundle();


                    bundle.putString("SLOT_QTY", selectedQty);
                    bundle.putString("SLOT_ID", strSlotId);
                    bundle.putString("CURRENT_DATE", btnDODate.getText()
                            .toString());
                    bundle.putString("DO_NUMBER", edtTxtDOnumber.getText()
                            .toString());
                    bundle.putString("COMMODITY", lblCommodity.getText()
                            .toString());
                    bundle.putString("REGION", edtRegion.getText().toString());
                    bundle.putString("CONTRACT_NO", lblContractNo.getText()
                            .toString());
                    bundle.putString("BUYER_NAME", edtBuyerName.getText()
                            .toString());
                    bundle.putString("BUYER_CODE", edtBuyerCode.getText()
                            .toString());
                    bundle.putString("DO_QUANTITY", edtDOQuantity.getText()
                            .toString());
                    bundle.putString("DO_BALANCE", edtDOBalance.getText()
                            .toString());
                    bundle.putString("COMMODITY_MST_ID", strCommodityMstId);
                    bundle.putString("CONTRACT_DATE", strContractDate);
                    bundle.putString("CONTRACT_PREFIX", strContractPrefix);

                    bundle.putString("TRADER_CODE", strBuyerCode);
                    bundle.putString("SEASON_DESC", strSeason);
                    bundle.putString("DO_MST_ID", strDoMstId);
                    bundle.putString("ALLOTMENT_NO", strAllotmentNo);
                    bundle.putString("SLOT_DETAILS_ID", strSlotId);
                    bundle.putString("DElIVERY_DATE",currentWorkingDate1);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

            }
        });


    }

    private void getSlotDetails(String doNo, String doDate, String contractNo,
                                String contractDate, String contractPrefix) {

        DeliveryOrderService deliveryOrderService = new DeliveryOrderService(
                getApplicationContext());
        deliveryOrderService.getDoWiseSlot(doNo, doDate, contractNo,
                contractDate, contractPrefix,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        // TODO Auto-generated method stub

                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();

                        slotQtyRateList.clear();
                        slotIdList.clear();
                        slotDateList.clear();
                        slotQtyList.clear();
                        slotRateList.clear();

                        try {
                            jsonArray = response.getJSONArray("Information");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String slotId = jsonObject.getString("SLOT_DETAILS_ID");
                                String SlotRate = jsonObject.getString("PURC_RATE");
                                String SlotQty = jsonObject.getString("SLOT_QTY");

                                String SlotRateQty = jsonObject
                                        .getString("SLOT_QTY")
                                        + "Qtl. for Rs"
                                        + jsonObject.getString("PURC_RATE") + "";
                                String slotDate = jsonObject.getString("SLOT_DATE");

                                slotQtyRateList.add(SlotRateQty);
                                slotIdList.add(slotId);
                                slotDateList.add(slotDate);
                                slotQtyList.add(SlotQty);
                                slotRateList.add(SlotRate);

                                //<K,V>

                                slotDateMap.put(SlotRateQty, slotDate);
                                slotRateMap.put(SlotRateQty, SlotRate);
                                slotIdMap.put(SlotRateQty, slotId);
                                slotQtyMap.put(SlotRateQty, SlotQty);
                            }

                            ArrayAdapter<String> slotAdapter = new ArrayAdapter<>(
                                    getApplicationContext(),
                                    R.layout.custom_spinner_item, slotQtyRateList);
                            selectSlotWiseDetails.setAdapter(slotAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    private void getQuantityDetails(String DoNumber, String doDate) {
        // TODO Auto-generated method stub

        DeliveryOrderService deliveryService = new DeliveryOrderService(
                getApplicationContext());
        deliveryService.getDoQuantity(DoNumber, doDate,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");
                            JSONObject jsonObj = new JSONObject();
                            if (jsonArray.length() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No DO-Quantity available..",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                jsonObj = (JSONObject) jsonArray.get(0);
                                strDoQty = jsonObj.getString("DO_QTY");
                                if (jsonObj.has("DELIVERED_QTY")) {
                                    StrDeliverdQty = jsonObj
                                            .getString("DELIVERED_QTY");
                                    Double DoBalance = Double
                                            .parseDouble(strDoQty)
                                            - Double.parseDouble(StrDeliverdQty);
                                    DecimalFormat df = new DecimalFormat("0.00");

                                    String LblDobalance = df.format(DoBalance);//oBalance.toString();
                                    Log.e("BALANCE=",LblDobalance);
                                    edtDOQuantity.setText(strDoQty);
                                    edtDOBalance.setText(LblDobalance);
                                } else {
                                    edtDOQuantity.setText(strDoQty);
                                    edtDOBalance.setText(strDoQty);
                                }
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });

    }

    // /DoNumber
    public void getDOnumber() {
        PendingDOService pendingdo = new PendingDOService(this);
        pendingdo.getDoHelp(new HttpResponseListener<JSONObject>() {


            @Override
            public void getResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("RES");
                    if (jsonArray.length() < 1) {
                        Toast.makeText(getApplicationContext(),
                                "No veriety available.", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        JSONObject jsonObj = new JSONObject();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObj = (JSONObject) jsonArray.get(i);
                            String donum = jsonObj.getString("DO_NO");
                            String doDate = jsonObj.getString("DO_DATE");
                            DoNumberlist.add(donum);
                            DoDateList.add(doDate);
                        }
                        System.out.println("DoDateList----------" + DoDateList);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                DOTypeAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.custom_spinner_item, DoNumberlist);
                edtTxtDOnumber.setAdapter(DOTypeAdapter);
                edtTxtDOnumber.setThreshold(0);

            }
        });

    }

    private void updateDate() {
        // TODO Auto-generated method stub

        btnDODate.setText(new StringBuilder()
                .append(date < 10 ? ("0" + date) : (date)).append("-")
                .append((month < 9 ? ("0" + (month + 1)) : (month + 1)))
                .append("-").append(year).append(" "));

        selectedDoDate = date + "-" + (month + 1) + "-" + year;

        strDeliveryDate = btnDODate.getText().toString();
        strDeliveryDate = AllUtils
                .getFormattedDateForSqlDashnew(strDeliveryDate);
        System.out.println("DeliveryDate=======after button click --------"+ strDeliveryDate);
        // getInvoiceNumberList();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CURRENT_DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, year, month,
                        date);

        }
        return null;
    }

    public void onClick(View v) {

        String strchequeDate = btnDODate.getText().toString();
        // getInvoiceNumberList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            cDate = (Date) sdf.parse(strDeliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(JSONObject response) {
        // TODO Auto-generated method stub

    }

    //sumit key board

    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}
