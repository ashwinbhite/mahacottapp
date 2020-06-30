package com.cotton.mahacott;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.ReportsService;
import com.cotton.mahacott.util.AllUtils;
import com.cotton.mahacott.util.TextUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingDoReports extends AppCompatActivity {
    TableLayout table_layout;
    String FromDate="";
    String ToDate="";
    String doNumber="";
    String doDate="";
    String purchaserName="";
    String doQty="";
    String deliveredQty="";
    String pendingQty="";

    int fontSize=18;

    protected static final ViewGroup.LayoutParams lp_fill_fill = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);


    ArrayList<String> DoNumberList=new ArrayList<String>();
    ArrayList<String> DoDateList=new ArrayList<String>();
    ArrayList<String> PurchaserNameList=new ArrayList<String>();
    ArrayList<String> DoQtyList=new ArrayList<String>();
    ArrayList<String> DeliveredQtyList=new ArrayList<String>();
    ArrayList<String> PendingQtyList=new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendingdo_reports);
        table_layout=(TableLayout)findViewById(R.id.tablependingdo);

        FromDate = getIntent().getExtras().getString("FROMDATE");
        Log.d("FROMDATEFORMATE",FromDate);

        ToDate = getIntent().getExtras().getString("TODATE");
        Log.d("TODATEFORMATE",ToDate);

        getPendingDoReports(FromDate,ToDate);
    }

    public void getPendingDoReports(String fromDate, String toDate ){

        ReportsService reportsService = new ReportsService(getApplicationContext());
        reportsService.getPendingDoDetails(fromDate, toDate, new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {


                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    System.out.println("length of array is " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObj = (JSONObject) jsonArray.get(i);
                        doNumber = jsonObj.getString("DO_NO");
                        doDate = jsonObj.getString("DO_DATE");
                        purchaserName = jsonObj.getString("PURCHASER_NAME");
                        doQty = jsonObj.getString("DO_QTY");
                        deliveredQty = jsonObj.getString("DELIVERED_QTY");
                        pendingQty = jsonObj.getString("PENDING_QTY");


                        DoNumberList.add(doNumber);
                        DoDateList.add(AllUtils.getFormattedDateForCurrentDate(doDate));
                        PurchaserNameList.add(purchaserName);
                        DoQtyList.add(doQty);
                        DeliveredQtyList.add(deliveredQty);
                        PendingQtyList.add(pendingQty);

                    }

                    drawTable();
                } catch (JSONException e) {
                    e.printStackTrace();
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
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView DoNumberColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        DoNumberColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        DoNumberColumnLabel.setGravity(Gravity.CENTER);
        DoNumberColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        DoNumberColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        DoNumberColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("DO Number") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        DoNumberColumnLabel.setPadding(10, 8, 5, 2);
        DoNumberColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(DoNumberColumnLabel);

        TextView doDateColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };
        doDateColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        doDateColumnLabel.setGravity(Gravity.CENTER);
        doDateColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        doDateColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        doDateColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("DO Date") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        doDateColumnLabel.setPadding(10, 8, 5, 2);
        doDateColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(doDateColumnLabel);

        // TextView unitColumnLabel = new TextView(this);
        TextView purchaserNameColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        purchaserNameColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        purchaserNameColumnLabel.setGravity(Gravity.RIGHT);
        purchaserNameColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        purchaserNameColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        purchaserNameColumnLabel
                .setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Purchaser Name") + "$$", "$$", new StyleSpan(
                        Typeface.BOLD)));
        purchaserNameColumnLabel.setPadding(10, 8, 5, 2);
        purchaserNameColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(purchaserNameColumnLabel);

        TextView doQtyColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        doQtyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        doQtyColumnLabe.setGravity(Gravity.CENTER);
        doQtyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        doQtyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        doQtyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("DO Quantity") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        doQtyColumnLabe.setPadding(10, 8, 5, 2);
        doQtyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(doQtyColumnLabe);

        TextView deliveredQtyColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        deliveredQtyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        deliveredQtyColumnLabe.setGravity(Gravity.CENTER);
        deliveredQtyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        deliveredQtyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        deliveredQtyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Delivered Quantity") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        deliveredQtyColumnLabe.setPadding(10, 8, 5, 2);
        deliveredQtyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(deliveredQtyColumnLabe);

        TextView pendingQtyColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        pendingQtyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        pendingQtyColumnLabe.setGravity(Gravity.CENTER);
        pendingQtyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        pendingQtyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        pendingQtyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Pending Quantity") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        pendingQtyColumnLabe.setPadding(10, 8, 5, 2);
        pendingQtyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(pendingQtyColumnLabe);


        table_layout.addView(tableColumnLayout);

        System.out.println("DoNumberList---------" + PurchaserNameList.size());
        for (int i = 0; i < DoNumberList.size(); i++) {
            TableRow tableRowLayout = new TableRow(this);
            tableRowLayout.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView doNumberRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            doNumberRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            doNumberRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            doNumberRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            doNumberRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + DoNumberList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            doNumberRowLabel.setPadding(10, 8, 5, 2);
            doNumberRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(doNumberRowLabel);

            TextView doDateRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            doDateRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            doDateRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            doDateRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            doDateRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + DoDateList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            doDateRowLabel.setPadding(10, 8, 5, 2);
            doDateRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(doDateRowLabel);

            TextView purchaserRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView qtyRowLabel = new TextView(this);
            purchaserRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            purchaserRowLabel.setGravity(Gravity.RIGHT);
            purchaserRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            purchaserRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            purchaserRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + PurchaserNameList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            purchaserRowLabel.setPadding(10, 8, 5, 2);
            purchaserRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(purchaserRowLabel);

            TextView doQtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            doQtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            doQtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            doQtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            doQtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + DoQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            doQtyRowLabel.setPadding(10, 8, 5, 2);
            doQtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(doQtyRowLabel);

            TextView deliveredQtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            deliveredQtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            deliveredQtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            deliveredQtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            deliveredQtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + DeliveredQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            deliveredQtyRowLabel.setPadding(10, 8, 5, 2);
            deliveredQtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(deliveredQtyRowLabel);

            TextView pendingQtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            pendingQtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            pendingQtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            pendingQtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            pendingQtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + PendingQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            pendingQtyRowLabel.setPadding(10, 8, 5, 2);
            pendingQtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(pendingQtyRowLabel);





            table_layout.addView(tableRowLayout);

        }

    }

    public static String safeTrim(String str) {
        return str != null ? str.trim() : str;
    }
}
