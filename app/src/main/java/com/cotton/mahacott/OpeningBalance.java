package com.cotton.mahacott;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.ProductionPercenteService;
import com.cotton.mahacott.service.SaveOpeningStockService;
import com.cotton.mahacott.service.SeedStockEntryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OpeningBalance extends AppCompatActivity {

    Spinner spinCommodity,spinVariety,spinGrade;
    Button btnDate,btnsave;
    String pattern;
    EditText edtTotProcQty,edtTotDeliveryQty,edtTotSbnd,edtRedyStock,edtAuctionQty,edtFutureAuctionedQty,edtFutureDeliveryQty;
    String procQty="0";
    String totdeliveryQty="0";
    String totSbnd="0";
    String totReadyStock="0";
    String totAuctionQty="0";
    TextView ReadyStock;
    int readyAuctionQty=0;
    DatePickerDialog datePickerDialog;
    List<String> varietyTypeList = new ArrayList<>();
    Map<String, String> varietyTypeMap = new HashMap<>();
    List<String> gradeTypeList = new ArrayList<>();
    Map<String, String> gradeTypeMap = new HashMap<>();
    ArrayList<String> CommoList=new ArrayList<String>();
    int prodPercent =0;
    int totSbndQty =0;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_balance);
        spinCommodity=(Spinner)findViewById(R.id.txtViewCommodity);
        spinVariety=(Spinner)findViewById(R.id.autoTxtVariety);
        spinGrade=(Spinner)findViewById(R.id.auutoGrade);

        edtTotProcQty=(EditText)findViewById(R.id.edtQuantity);
        edtTotDeliveryQty=(EditText)findViewById(R.id.edtDeliveredQty);
        edtTotSbnd=(EditText)findViewById(R.id.edtSbndQty);
        edtRedyStock=(EditText)findViewById(R.id.edtReadystock);
        edtAuctionQty=(EditText)findViewById(R.id.edtAuctionedQty);
        edtFutureAuctionedQty=(EditText)findViewById(R.id.edtFutureAuctionedQty);
        edtFutureDeliveryQty=(EditText)findViewById(R.id.edtFutureDeliveryQty);
        ReadyStock=(TextView)findViewById(R.id.txtReadyAuction);

        btnDate=(Button)findViewById(R.id.inputDate);
        btnsave=(Button)findViewById(R.id.btnSubmit);
        pattern = "dd/MMM/yyyy";
        final String dateInString =new SimpleDateFormat(pattern).format(new Date());
        btnDate.setText(dateInString);


        btnDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(OpeningBalance.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                btnDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                flag=true;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });

//		CommoList.add("Fp Bales");
        CommoList.add("Cotton Seeds");

        ArrayAdapter<String> CommodityAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.custom_spinner_item, CommoList);
        spinCommodity.setAdapter(CommodityAdapter);

        SeedStockEntryService seedStockEntryService = new SeedStockEntryService(
                this);
        seedStockEntryService
                .getVerietyHelp(new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");
                            if (jsonArray.length() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No veriety available.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject jsonObj = new JSONObject();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObj = (JSONObject) jsonArray.get(i);
                                    varietyTypeMap.put(
                                            jsonObj.getString("VARIETY"),
                                            jsonObj.getString("VARIETYKEY"));
                                    varietyTypeList.add(jsonObj
                                            .getString("VARIETY"));
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> varietyTypeAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.custom_spinner_item, varietyTypeList);
                        spinVariety.setAdapter(varietyTypeAdapter);

                    }
                });

        seedStockEntryService
                .getGradeHelp(new HttpResponseListener<JSONObject>() {
                    @Override
                    public void getResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");

                            if (jsonArray.length() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No grade available.",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                JSONObject jsonObj = new JSONObject();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObj = (JSONObject) jsonArray.get(i);
                                    // GARDE KEY YET TO BE DISSCUSSED
                                    gradeTypeMap.put(
                                            jsonObj.getString("GRADE"),
                                            jsonObj.getString("GRADEKEY"));
                                    gradeTypeList.add(jsonObj
                                            .getString("GRADE"));
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> gradeTypeAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.custom_spinner_item, gradeTypeList);
                        spinGrade.setAdapter(gradeTypeAdapter);

                    }
                });


        edtFutureAuctionedQty.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(edtAuctionQty.getText().toString().trim().length()<=0)
                    {
                        edtAuctionQty.setText("0");
                    }

                    if(edtFutureAuctionedQty.getText().toString().trim().length()<=0)
                    {
                        edtFutureAuctionedQty.setText("0");
                    }
                    int totalAuction=Integer.parseInt(edtAuctionQty.getText().toString());
                    int totFutureAuction=Integer.parseInt(edtFutureAuctionedQty.getText().toString());
                    readyAuctionQty=totalAuction-totFutureAuction;
                    ReadyStock.setText("Ready Auc Qty:"+String.valueOf(readyAuctionQty));
                }

            }
        });

        edtTotDeliveryQty.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    if(edtAuctionQty.getText().toString().trim().length()<=0)
                    {
                        edtAuctionQty.setText("0");
                    }

                    if(edtTotDeliveryQty.getText().toString().trim().length()<=0)
                    {
                        edtTotDeliveryQty.setText("0");
                    }

                    int totAuction = Integer.parseInt(edtAuctionQty.getText().toString());
                    int totDelivered =Integer.parseInt(edtTotDeliveryQty.getText().toString());
                    totSbndQty = totAuction-totDelivered;
                    edtTotSbnd.setText(String.valueOf(totSbndQty));
                }
            }
        });



        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeKeyBoard();

                if(edtTotProcQty.getText().toString().length()<=0)
                {
                    edtTotProcQty.setText("0");
                }

                if (edtTotProcQty.getText().toString().length() < 1
                        || btnDate.getText().toString().length() < 1
                        || edtTotDeliveryQty.getText().toString().length() < 1
                        || edtTotDeliveryQty.getText().toString().length() < 1
                        || edtTotSbnd.getText().toString().length() < 1
                        ||edtRedyStock.getText().toString().length()<1
                        ||edtAuctionQty.getText().toString().length()<1
                        || edtFutureDeliveryQty.getText().toString().length()<1
                ) {



                    Toast.makeText(getApplicationContext(),
                            "Fill all the details..", Toast.LENGTH_SHORT)
                            .show();
                    System.out.println("ProcQty"+edtTotProcQty.getText().toString());
                    System.out.println("DAte"+btnDate.getText().toString());
                    System.out.println("DeliveryQty"+edtTotDeliveryQty.getText().toString());

                    System.out.println("totSbnd"+edtTotSbnd.getText().toString());
                    System.out.println("ReadyStock"+edtRedyStock.getText().toString());
                    System.out.println("AuctionQty"+edtAuctionQty.getText().toString());
                    System.out.println("FutureAuctionQty"+edtFutureAuctionedQty.getText().toString());
                    System.out.println("DeliveryAuctionQty"+edtFutureDeliveryQty.getText().toString());


                }
                else if(Integer.parseInt(edtAuctionQty.getText().toString())>(Integer.parseInt(edtTotProcQty.getText().toString())*0.01*prodPercent))
                {
                    System.out.println("1-----"+edtAuctionQty.getText().toString());
                    System.out.println("prodPercent-----------"+prodPercent);
                    System.out.println(":4"+edtTotProcQty.getText().toString());
                    System.out.println("2-----"+Integer.parseInt(edtTotProcQty.getText().toString())*0.01*prodPercent);

                    Toast.makeText(getApplicationContext(), "Total Auction Qty must be less than Procured Qty ratio", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(edtTotDeliveryQty.getText().toString())>Integer.parseInt(edtAuctionQty.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Total Delivered Qty must be less than total Auction Qty", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(edtTotSbnd.getText().toString())>(Integer.parseInt(edtAuctionQty.getText().toString())-Integer.parseInt(edtTotDeliveryQty.getText().toString())))
                {
                    Toast.makeText(getApplicationContext(), "Total Sbnd Qty must be less than or equal SBND Qty", Toast.LENGTH_LONG).show();
                }

                else if(Integer.parseInt(edtFutureAuctionedQty.getText().toString())>Integer.parseInt(edtAuctionQty.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Total Future Auction Qty can not be more than total Auction Qty", Toast.LENGTH_LONG).show();
                }

                else if(Integer.parseInt(edtFutureDeliveryQty.getText().toString())>Integer.parseInt(edtTotDeliveryQty.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Total Future Delivery Qty can not be more than total Delivery Qty", Toast.LENGTH_LONG).show();
                }

                else if(Integer.parseInt(edtRedyStock.getText().toString())>((Integer.parseInt(edtTotProcQty.getText().toString())*0.01*prodPercent)-(Integer.parseInt(edtTotDeliveryQty.getText().toString()))))
                {
                    Toast.makeText(getApplicationContext(), "Ready Stock  can not be more than Remaining Procured Qty", Toast.LENGTH_LONG).show();
                }




                else {


                    String	commoMstId="";

                    String 	strVariety = spinVariety.getSelectedItem().toString();
                    String strGrade = spinGrade.getSelectedItem().toString();
                    String selectedCommodity=spinCommodity.getSelectedItem().toString();

                    if(selectedCommodity.equalsIgnoreCase("Cotton Seeds"))
                    {
                        commoMstId="2";
                    }
                    else
                    {
                        commoMstId="1";
                    }


                    String varietyMstID = varietyTypeMap
                            .get(strVariety);
                    String gradeId = gradeTypeMap.get(strGrade);
                    String seasonMstId = "3";



                    String openingdate=btnDate.getText().toString();
                    procQty=edtTotProcQty.getText().toString().trim();
                    totdeliveryQty=edtTotDeliveryQty.getText().toString().trim();
                    totSbnd=edtTotSbnd.getText().toString().trim();
                    totReadyStock=edtRedyStock.getText().toString().trim();

                    totAuctionQty=edtAuctionQty.getText().toString().trim();
                    String futureAucQty = edtFutureAuctionedQty.getText().toString().trim();
                    String futureDeliveryQty = edtFutureDeliveryQty.getText().toString().trim();
                    String desc="Mob";


                    saveopeningBalance(commoMstId, varietyMstID, gradeId, openingdate, procQty, totdeliveryQty, totSbnd, totReadyStock, totAuctionQty, desc,futureAucQty,futureDeliveryQty);


                    clear();
                    // TODO Auto-generated method stub
                }
            }
        });

        ProductionPercenteService productionPercenteService = new ProductionPercenteService(getApplicationContext());
        productionPercenteService.getProductionPercent(new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                System.out.println("PROD %==="+response);
                // TODO Auto-generated method stub
                try {

                    JSONArray jsonArray = new JSONArray();
                    jsonArray=response.getJSONArray("RES");
                    if (jsonArray.length() < 1) {
                        Toast.makeText(getApplicationContext(),
                                "No Production % available.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj = (JSONObject) jsonArray.get(0);
                        prodPercent =  Integer.parseInt(jsonObj.getString("PROD_PERCENTAGE")) ;
                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }


    void saveopeningBalance(String CommoMstId,String varietyMstID,String  gradeId,String openingdate,String procQty,String totdeliveryQty,String totSbnd, String totReadyStock,String totAuctionQty,String desc,String futureAucQty,String futureDeliveryQty)
    {
        SaveOpeningStockService saveopening = new SaveOpeningStockService(
                this);
        saveopening
                .submitSaveOpeningStock(CommoMstId, varietyMstID, gradeId, openingdate, procQty, totdeliveryQty, totSbnd, totReadyStock, totAuctionQty, desc,futureAucQty,futureDeliveryQty,flag, new HttpResponseListener<JSONObject>() {
                    @Override
                    public void getResponse(JSONObject response) {
                        try {
                            if(response.getString("code").equalsIgnoreCase("1") || response.getString("msg").equalsIgnoreCase("SUCCESS"))
                            {
                                Toast.makeText(getApplicationContext(), "Opening stock has saved successfully..", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Opening stock entry has failed..", Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> gradeTypeAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.custom_spinner_item, gradeTypeList);
                        spinGrade.setAdapter(gradeTypeAdapter);

                    }
                });
    }

//sumit keyboard

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public void clear(){
        String clear = "";

        edtTotProcQty.setText(clear);
        edtTotDeliveryQty.setText(clear);
        edtTotSbnd.setText(clear);
        edtRedyStock.setText(clear);
        edtAuctionQty.setText(clear);
        edtFutureAuctionedQty.setText(clear);
        edtFutureDeliveryQty.setText(clear);

    }
}
