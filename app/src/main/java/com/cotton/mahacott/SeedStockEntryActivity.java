package com.cotton.mahacott;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.view.ViewGroup.LayoutParams;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.SeedStockEntryService;
import com.cotton.mahacott.util.SaveSharedPreference;
import com.cotton.mahacott.util.TextUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SeedStockEntryActivity extends AppCompatActivity implements
        HttpResponseListener<JSONObject>, AdapterView.OnItemSelectedListener,TextWatcher{

    private TextView txtViewCommodity;
    private EditText edtQty, edtOilpercnt, edtmstrprcnt, edtheapno;
    private AutoCompleteTextView actZone, actVariety, actGinning, actseason,
            actGrade;
    String strZoneCode,strSaleTpe="";
    String PressingDataId="";
    String strCurrentDate;
    String ZoneCode="";
    String CenterCode="";
    String CommodityMstId="";
    String CurrentDate="";
    String SaleType="";
    String SeedStockQuantity="";
    String HeapNumber="";
    String gradeId="";
    String  mVarietyMstID="";
    String  heapNo="";
    int mCount=0;
    int sCount=0;
    String  mGradeId="";
    int fontSize=18;
    String strVarietyType = "", strVarietyMstId = "", strSaleType = "",
            strHeapNo = "", strVariety = "", strGrade = "", strQuantity = "",
            strOilPercent = "", strMoisture = "", strCommodity = "";
    String strColGrade="",strColVariety="",strColHeapNo="",strColQuantity="";
    Button btnCurrentDate;
    Button btnSubmit;
    Button btnClear;
    Spinner selectSaleType;
    TableLayout table_layout;
    TextView txtheapno,txtoilprcnt,txtmostrpercent;
    HorizontalScrollView hsv;


    protected static final LayoutParams lp_fill_fill = new LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    protected static final LayoutParams lp_fill_wrap = new LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    protected static final LayoutParams lp_wrap_fill = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
    protected static final LayoutParams lp_wrap_wrap = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    protected static final LayoutParams lp_button_wrap_wrap = new LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private RelativeLayout Stockreport;

    private ArrayList<String> gridheapNolist=new ArrayList<String>();
    private ArrayList<String> gridvarietyDesclist=new ArrayList<String>();
    private ArrayList<String> gridGradeList=new ArrayList<String>();
    private ArrayList<String> gridQtyList=new ArrayList<String>();
    private ArrayList<String> gridSaleTypeList=new ArrayList<String>();
    List<String> saleTypeList = new ArrayList<>();
    Map<String, String> saleTypeMap = new HashMap<>();
    List<String> varietyTypeList = new ArrayList<>();
    Map<String, String> varietyTypeMap = new HashMap<>();
    List<String> gradeTypeList = new ArrayList<>();
    Map<String, String> gradeTypeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_stock_entry);

        strCurrentDate = getIntent().getExtras().getString("CURRENT_DATE");
        txtheapno= (TextView) findViewById(R.id.txtheapno);
        txtoilprcnt= (TextView) findViewById(R.id.txtoilprcnt);
        txtmostrpercent= (TextView) findViewById(R.id.txtmostrpercent);

        edtQty = (EditText) findViewById(R.id.edtQuantity);
        txtViewCommodity = (TextView) findViewById(R.id.txtViewCommodity);
        edtOilpercnt = (EditText) findViewById(R.id.edtoilPercnt);
        edtmstrprcnt = (EditText) findViewById(R.id.edtmostrpercnt);
        edtheapno = (EditText) findViewById(R.id.edtheapno);
        actVariety = (AutoCompleteTextView) findViewById(R.id.autoTxtVariety);
        actGrade = (AutoCompleteTextView) findViewById(R.id.auutoGrade);
        selectSaleType = (Spinner) findViewById(R.id.selectSaleType);
        btnCurrentDate = (Button) findViewById(R.id.inputDate);
        //btnClear = (Button) findViewById(R.id.btnClear);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCurrentDate.setText(strCurrentDate);
        System.out.println("DATE============"+strCurrentDate);
        txtoilprcnt.setVisibility(View.GONE);
        txtmostrpercent.setVisibility(View.GONE);
        edtOilpercnt.setVisibility(View.GONE);
        edtmstrprcnt.setVisibility(View.GONE);

        table_layout = (TableLayout) findViewById(R.id.tableStockReport);
        table_layout.removeAllViews();
        table_layout.setStretchAllColumns(true);
        table_layout.bringToFront();
        table_layout.clearFocus();
        // DATA FROM HOME ACTIVITY
        selectSaleType.setOnItemSelectedListener(this);


        strZoneCode = SaveSharedPreference.getzonecode(getApplicationContext());

        saleTypeList.add("Ready Stock");


        saleTypeMap.put("Ready Stock", "S");






        ArrayAdapter<String> saleTypeAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.custom_spinner_item,
                saleTypeList);
        selectSaleType.setAdapter(saleTypeAdapter);

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
                        actVariety.setAdapter(varietyTypeAdapter);
                        actVariety.setThreshold(0);
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
                        actGrade.setAdapter(gradeTypeAdapter);
                        actGrade.setThreshold(0);
                    }
                });

        // to select existing grade
        actGrade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                if(!hasFocus){
                    if(gradeTypeList.contains(actGrade.getText().toString())){
                        Log.d("Grade Selected",actGrade.getText().toString());
                        getseedstockquantity(actVariety.getText().toString(),actGrade.getText().toString());
                    }

                    else
                    {
                        if(actGrade.getText().toString().length()>0)
                        {
                            actGrade.setText("");
                            Toast.makeText(getApplicationContext(), "Please select valid grade", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        });

        // to select existing variety

        actVariety.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                if(!hasFocus){
                    if(varietyTypeList.contains(actVariety.getText().toString()))
                        System.out.println("variety selected");
                    else
                    {
                        if(actVariety.getText().toString().length()>0)
                        {
                            actVariety.setText("");
                            Toast.makeText(getApplicationContext(), "Please select valid Variety", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        });

    /*    edtheapno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                if(!hasFocus){


                    String mGradeId = gradeTypeMap.get(actGrade.getText().toString());
                    String mVarietyMstID = varietyTypeMap.get(actVariety.getText().toString());

                    *//*int Slotcount = getSlotCountIfCreated(mVarietyMstID,mGradeId);
                    if (Slotcount == 0) {

                    } else {
                        Toast.makeText(getApplicationContext(), "You Can't update slot already created", Toast.LENGTH_SHORT).show();
                    }*//*

                }
            }
        });*/



       /* actGrade.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){

                    if(gradeTypeList.contains(actGrade.getText().toString())){
                        System.out.println("variety selected");
                        getseedstockquantity(actVariety.getText().toString(),actGrade.getText().toString());
                    }


                    else
                    {
                        if(actGrade.getText().toString().length()>0)
                        {
                            actGrade.setText("");
                            Toast.makeText(getApplicationContext(), "Please select valid grade", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }






                }
            }
        });
*/

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                closeKeyBoard();

                // TODO Auto-generated method stub
                if (txtViewCommodity.getText().toString().length() < 1
                        || btnCurrentDate.getText().toString().length() < 1
                        || edtQty.getText().toString().length() < 1
                        || actVariety.getText().toString().length() < 1

                        || actGrade.getText().toString().length() < 1

                        ||edtheapno	.getText().toString().length()<1
                ) {
                    Toast.makeText(getApplicationContext(),
                            "Fill all the details..", Toast.LENGTH_SHORT)
                            .show();
                    System.out.println("COMO"+txtViewCommodity.getText().toString());
                    System.out.println("DAte"+btnCurrentDate.getText().toString());
                    System.out.println("qty"+edtQty.getText().toString());

                    System.out.println("haep"+edtheapno.getText().toString());
                    System.out.println("vari"+actVariety.getText().toString());
                    System.out.println("grad"+actGrade.getText().toString());


                } else {
//
//					btnCurrentDate.getText().toString(),
//					commoMstId, varietyMstID, gradeId,
//					strQuantity, seasonMstId, strOilPercent,
//					strMoisture, strHeapNo, saleTypeMap.get(strSaleType),
//					new HttpResponseListener<JSONObject>() {
                    strVariety = actVariety.getText().toString();
                    strGrade = actGrade.getText().toString();
                    String commoMstId = "2";
                    String varietyMstID = varietyTypeMap
                            .get(strVariety);
                    String gradeId = gradeTypeMap.get(strGrade);



                    SeedStockEntryService seedStockEntryService = new SeedStockEntryService(getApplicationContext());
                    seedStockEntryService.getSeedStockQty("F",btnCurrentDate.getText().toString(),
                            commoMstId, varietyMstID, gradeId, new HttpResponseListener<JSONObject>() {

                                @Override
                                public void getResponse(JSONObject response) {
                                    // TODO Auto-generated method stub
                                    Log.e("in getsed","success-----try------");
                                    try {
                                        Log.e("in getsed","success-----------");

                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray = response.getJSONArray("RES");
                                        JSONObject jsonObj = new JSONObject();
                                        jsonObj =(JSONObject) jsonArray.get(0);
                                        Float stockPending;

                                        if(response.getString("msg").equalsIgnoreCase("SUCCESS")){

                                            System.out.println("---------STOCK--"+jsonObj.getString("STOCK"));
                                            Log.e("-----STOCK",jsonObj.getString("STOCK"));
                                            if(mCount>0)
                                                stockPending =(Float.parseFloat(jsonObj.getString("STOCK"))+Float.parseFloat(SeedStockQuantity));
                                            else
                                                stockPending =Float.parseFloat(jsonObj.getString("STOCK"));
                                            Float enteredQty = Float.parseFloat(edtQty.getText().toString());

                                            //to check slot has prepared or not
                                            if(sCount>0)
                                            {
                                                Toast.makeText(getApplicationContext(), "Slot has been created.cannot update now", Toast.LENGTH_SHORT).show();
                                            }
                                            //if slot not preparted then all check
                                            else
                                            {
                                                if(stockPending<enteredQty){
                                                    Toast.makeText(getApplicationContext(), "Auction qty must be less than " + stockPending, Toast.LENGTH_SHORT).show();
                                                    edtQty.setText("");

                                                }
                                                else
                                                {
                                                    if( edtheapno.isEnabled() && mCount>0){
                                                        Toast.makeText(getApplicationContext(),
                                                                "HeapNo. for this variety already present",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        openAlert(getWindow().getDecorView());
                                                    }

                                                }

                                            }






                                    /*        if(stockPending<enteredQty){
                                                Log.e("in stock case",String.valueOf(sCount));
                                                if(sCount>0) {
                                                    Toast.makeText(getApplicationContext(), "Slot has been created.cannot update now", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Auction qty must be less than " + stockPending, Toast.LENGTH_SHORT).show();
                                                    edtQty.setText("");
                                                    Log.e("in getsed", "success---1111--------");
                                                }
                                            }else{

                                                String mGradeId = gradeTypeMap.get(strGrade);
                                                String mVarietyMstID = varietyTypeMap.get(strVariety);
                                                //mVarietyMstID, mGradeId, heapNo
                                                Log.e("in getsed","success----222222222-------");
                                                //int isHeapNoAlradyPresent = getHeapCountFromDb(mVarietyMstID, mGradeId,edtheapno.getText().toString());
                                              //  int isSlotAlreadyCreated= getSlotCountIfCreated(mVarietyMstID,mGradeId);

                                               // getHeapCountFromDb(mVarietyMstID, mGradeId,edtheapno.getText().toString());

                                             //   getSlotCountIfCreated(mVarietyMstID,mGradeId);

                                                Log.e("sCount------",String.valueOf(sCount));
                                                Log.e("mCount------",String.valueOf(mCount));
                                                if(sCount>0){
                                                    Toast.makeText(getApplicationContext(),
                                                            "Slot has been created.cannot update now",
                                                            Toast.LENGTH_SHORT).show();
                                                }else{
                                                    openAlert(getWindow().getDecorView());
                                                }
                                               if( edtheapno.isEnabled() && mCount>0){
                                                    Toast.makeText(getApplicationContext(),
                                                            "HeapNo. for this variety already present",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                                //if no slot created and no heap no present
                                                if(mCount==0 &&  sCount==0){
                                                    openAlert(getWindow().getDecorView());
                                                }
                                            }*/



                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "There's Some Error in Entry..", Toast.LENGTH_SHORT).show();

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

//					openAlert(getWindow().getDecorView());


                }
            }
        });



        getSeedStockEntry();


        edtQty.addTextChangedListener(new TextWatcher(){
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (edtQty.getText().toString().matches("^0") )
                {
                    // Not allowed
                    Toast.makeText(getApplicationContext(), "not allowed", Toast.LENGTH_LONG).show();
                    edtQty.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

    }

    //sumit key board

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //sumit 04-01-2020
    public void getseedstockquantity(String strVariety,String strGrade ){
        /*strVariety = actVariety.getText().toString();
        strGrade = actGrade.getText().toString();*/
        CurrentDate=strCurrentDate;


        String varietyMstID = varietyTypeMap
                .get(strVariety);
        String gradeId = gradeTypeMap.get(strGrade);
        Log.e("varietyMstID-----",varietyMstID+""+gradeId+""+CurrentDate);

        SeedStockEntryService getseedStockQtyService = new SeedStockEntryService(
                this);
        // CommodityMstId, varietyMstID, gradeId, selectSaleType



        getseedStockQtyService.getReadyStockQuantity(CommodityMstId,varietyMstID,gradeId,SaleType,CurrentDate,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {

                        try {
                            SeedStockQuantity="";
                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("RES");
                            JSONObject jsonObj = new JSONObject();
                            System.out.println("length of array is "
                                    + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObj = (JSONObject) jsonArray.get(i);

                                if(jsonObj.getString("ISDATA").equals("Y")){
                                    edtheapno.setEnabled(false);
                                    SeedStockQuantity = jsonObj.getString("Quantity");
                                    PressingDataId=jsonObj.getString("PressingDataId");
                                    HeapNumber=jsonObj.getString("HeapNo");
                                }else{
                                    edtheapno.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"No previous data.", Toast.LENGTH_SHORT).show();
                                    SeedStockQuantity = jsonObj.getString("");

                                }

                                edtheapno.setText(HeapNumber);
                                edtQty.setText(SeedStockQuantity);

                                String mGradeId = gradeTypeMap.get(actGrade.getText().toString());
                                String mVarietyMstID = varietyTypeMap.get(actVariety.getText().toString());
                                //mVarietyMstID, mGradeId, heapNo
                                Log.e("in getsed","success----222222222-------");
                                //int isHeapNoAlradyPresent = getHeapCountFromDb(mVarietyMstID, mGradeId,edtheapno.getText().toString());
                                //  int isSlotAlreadyCreated= getSlotCountIfCreated(mVarietyMstID,mGradeId);

                                getHeapCountFromDb(mVarietyMstID, mGradeId,edtheapno.getText().toString());


                            }


                        } catch (JSONException err) {
                            err.printStackTrace();
                        }

                    }
                });
    }



    public void drawTable() {

        table_layout.removeAllViews();
        TableLayout ll1 = new TableLayout(this);
        // TableLayout ll2 = new TableLayout(this);

        TableRow ll14 = new TableRow(this);
        ll14.setPadding(0, 10, 0, 0);

        TableRow ll13 = new TableRow(this);
        ll13.setPadding(0, 10, 0, 0);

        ll1.addView(ll13, lp_fill_fill);
        // ll2.addView(ll14, lp_fill_fill);

        TableLayout tl3 = new TableLayout(this);
        ll13.addView(tl3, lp_fill_fill);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(
                this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        horizontalScrollView.setLayoutParams(layoutParams);

        TableRow tableColumnLayout = new TableRow(this);
        tableColumnLayout.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        TextView heapNoColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        heapNoColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        heapNoColumnLabel.setGravity(Gravity.CENTER);
        heapNoColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        heapNoColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        heapNoColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Heap No") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        heapNoColumnLabel.setPadding(10, 8, 5, 2);
        heapNoColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(heapNoColumnLabel);

        TextView comColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };
        comColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        comColumnLabel.setGravity(Gravity.CENTER);
        comColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        comColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        comColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Variety") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        comColumnLabel.setPadding(10, 8, 5, 2);
        comColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(comColumnLabel);

        // TextView unitColumnLabel = new TextView(this);
        TextView gradeColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        gradeColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        gradeColumnLabel.setGravity(Gravity.RIGHT);
        gradeColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        gradeColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        gradeColumnLabel
                .setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Grade") + "$$", "$$", new StyleSpan(
                        Typeface.BOLD)));
        gradeColumnLabel.setPadding(10, 8, 5, 2);
        gradeColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(gradeColumnLabel);

        TextView qtyColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        qtyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        qtyColumnLabe.setGravity(Gravity.CENTER);
        qtyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        qtyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        qtyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Quantity") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        qtyColumnLabe.setPadding(10, 8, 5, 2);
        qtyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(qtyColumnLabe);

        TextView SaleTypeColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        SaleTypeColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        SaleTypeColumnLabe.setGravity(Gravity.CENTER);
        SaleTypeColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        SaleTypeColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        SaleTypeColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("SaleType") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        SaleTypeColumnLabe.setPadding(10, 8, 5, 2);
        SaleTypeColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(SaleTypeColumnLabe);

        table_layout.addView(tableColumnLayout);

        System.out.println("gridheapNolist---------" + gridheapNolist.size());
        for (int i = 0; i < gridheapNolist.size(); i++) {
            TableRow tableRowLayout = new TableRow(this);
            tableRowLayout.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            TextView heapRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
                /*
                 * @Override protected void onDraw(Canvas canvas) {
                 * super.onDraw(canvas); Rect rect = new Rect(); Paint paint =
                 * new Paint(); paint.setStyle(Paint.Style.STROKE);
                 * paint.setColor
                 * (getResources().getColor(android.R.color.black));
                 * paint.setStrokeWidth(2); getLocalVisibleRect(rect);
                 * canvas.drawRect(rect, paint); }
                 */
            };
            // TextView commRowLabel = new TextView(this);
            heapRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            heapRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            heapRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            heapRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + gridheapNolist.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            heapRowLabel.setPadding(10, 8, 5, 2);
            heapRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(heapRowLabel);

            TextView commRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            commRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            commRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            commRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            commRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + gridvarietyDesclist.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            commRowLabel.setPadding(10, 8, 5, 2);
            commRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(commRowLabel);

            TextView gradeRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView qtyRowLabel = new TextView(this);
            gradeRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            gradeRowLabel.setGravity(Gravity.RIGHT);
            gradeRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            gradeRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            gradeRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + gridGradeList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            gradeRowLabel.setPadding(10, 8, 5, 2);
            gradeRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(gradeRowLabel);

            TextView qtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            qtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            qtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            qtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            qtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + gridQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            qtyRowLabel.setPadding(10, 8, 5, 2);
            qtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(qtyRowLabel);

            TextView saleTypeRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            saleTypeRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            saleTypeRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            saleTypeRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            saleTypeRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + gridSaleTypeList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            saleTypeRowLabel.setPadding(10, 8, 5, 2);
            saleTypeRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(saleTypeRowLabel);

            table_layout.addView(tableRowLayout);

        }

    }

    public void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Are you sure,You want to save data");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface arg0, int arg1) {

                        arg0.dismiss();

                        Log.e("-------------------","SAVE");
                        // DATA------------------------------

                        strCommodity = txtViewCommodity.getText()
                                .toString();
                        // btnCurrentDate.getText();
                        strQuantity = edtQty.getText().toString();


                        strOilPercent = "0.0";
                        strMoisture = "0.0";

                        if(strSaleTpe.equalsIgnoreCase("Future Sale")){
                            strHeapNo="";
                        }else{
                            strHeapNo = edtheapno.getText().toString();
                        }

                        strVariety = actVariety.getText().toString();
                        strGrade = actGrade.getText().toString();
                        String commoMstId = "2";
                        String varietyMstID = varietyTypeMap
                                .get(strVariety);
                        String gradeId = gradeTypeMap.get(strGrade);
                        String seasonMstId = SaveSharedPreference.getSeasonMstId(getApplicationContext());


                        if(SeedStockQuantity.length()>0)
                        {
                            PressingDataId=PressingDataId;
                        }
                        else
                        {
                            PressingDataId="0";
                        }

                        SeedStockEntryService seedStockEntryService = new SeedStockEntryService(
                                getApplicationContext());
                        seedStockEntryService.submitSeedStockEntry(PressingDataId,
                                btnCurrentDate.getText().toString(),
                                commoMstId, varietyMstID, gradeId,
                                strQuantity, seasonMstId, strOilPercent,
                                strMoisture, strHeapNo, saleTypeMap.get(strSaleType),
                                new HttpResponseListener<JSONObject>() {

                                    @Override
                                    public void getResponse(
                                            JSONObject response) {
                                        // TODO Auto-generated method stub

                                        System.out.println("SUMBIT ENTRY=="+ response);
                                        JSONObject jsonObj = new JSONObject();
                                        try {

                                            String Status=response.getString("status");
                                            Toast.makeText(getApplicationContext(),"Stock Entry "+Status, Toast.LENGTH_SHORT).show();
                                            getSeedStockEntry();
                                            arg0.dismiss();
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        clear();
                        //getSeedStockEntry();
                    }


                    // -------------------------------------------------------------------------------

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


    //Get SeedStockEntry
    public void getSeedStockEntry(){

        SeedStockEntryService seedStockEntryService = new SeedStockEntryService(getApplicationContext());
        seedStockEntryService.getSeedStockEntry(btnCurrentDate.getText().toString(),"S", new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {

                System.out.println("IN-ACT GET SEED STOCK RESPONSE: " + response.toString());
                try{
                    gridGradeList.clear();
                    gridheapNolist.clear();
                    gridvarietyDesclist.clear();
                    gridQtyList.clear();
                    gridSaleTypeList.clear();

                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("Information");
                    JSONObject jsonObj = new JSONObject();
                    System.out.println("length of array is "+jsonArray.length());
                    for(int i=0; i<jsonArray.length();i++)
                    {
                        jsonObj = (JSONObject) jsonArray.get(i);
                        strColVariety = jsonObj.getString("varietyDesc");
                        strColGrade= jsonObj.getString("grade");
                        if(jsonObj.has("heapNo"))
                            strColHeapNo = jsonObj.getString("heapNo");
                        else
                            strColHeapNo="";
                        strColQuantity =jsonObj.getString("qty");
                        strSaleTpe=jsonObj.getString("saleType");
                        gridSaleTypeList.add(strSaleTpe);
                        gridheapNolist.add(strColHeapNo);
                        gridvarietyDesclist.add(strColVariety);
                        gridGradeList.add(strColGrade);
                        gridQtyList.add(strColQuantity);
                    }

                    drawTable();
                }
                catch(JSONException err){
                    err.printStackTrace();
                }
            }
        });

    }

    public int getHeapCountFromDb(String mVarietyMstID, String mGradeId,String heapNo){

        SeedStockEntryService service = new SeedStockEntryService(getApplicationContext());
        service.getHeapCount(mVarietyMstID, mGradeId, heapNo, "S", new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    jsonObj = (JSONObject) jsonArray.get(0);

                    mCount = Integer.parseInt(jsonObj.getString("COUNT"));
                    Log.e("mCount---******",String.valueOf(mCount));


                    String mGradeId = gradeTypeMap.get(actGrade.getText().toString());
                    String mVarietyMstID = varietyTypeMap.get(actVariety.getText().toString());


                    getSlotCountIfCreated(mVarietyMstID,mGradeId);

                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        });
        return mCount;
    }

    public int getSlotCountIfCreated(String mVarietyMstID, String mGradeId ){

        heapNo=edtheapno.getText().toString();
        CurrentDate=btnCurrentDate.getText().toString();

        SeedStockEntryService service = new SeedStockEntryService(getApplicationContext());
        service.getSeedEntryCount(mVarietyMstID, mGradeId,CurrentDate, heapNo, new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    jsonObj = (JSONObject) jsonArray.get(0);

                    sCount = Integer.parseInt(jsonObj.getString("COUNT"));
                    Log.e("sCount---******",String.valueOf(sCount));

                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        });
        return sCount;
    }



    @Override
    public void getResponse(JSONObject response) {}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        strSaleType = selectSaleType.getSelectedItem().toString();
        System.out.println("strSaleType-------------"+strSaleType);
        if(strSaleType=="Future Sale"){
            edtheapno.setVisibility(View.GONE);
            edtmstrprcnt.setVisibility(View.GONE);
            edtOilpercnt.setVisibility(View.GONE);
            txtheapno.setVisibility(View.GONE);
            txtoilprcnt.setVisibility(View.GONE);
            txtmostrpercent.setVisibility(View.GONE);
        }else{
            edtheapno.setVisibility(View.VISIBLE);

            txtheapno.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {

        String x = s.toString();
        if(x.startsWith("0"))
        {
            edtQty.setText("");
            Toast.makeText(getApplicationContext(), "Quantity can't be '0'.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clear(){
        String clear = "";

        edtQty.setText(clear);
        edtOilpercnt.setText(clear);
        edtmstrprcnt.setText(clear);
        edtheapno.setText(clear);
        actVariety.setText(clear);
        actGrade.setText(clear);

    }

    public static String safeTrim(String str) {
        return str != null ? str.trim() : str;
    }
}
