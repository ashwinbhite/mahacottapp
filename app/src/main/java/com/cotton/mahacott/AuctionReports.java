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
import com.cotton.mahacott.service.SeedStockEntryService;
import com.cotton.mahacott.util.AllUtils;
import com.cotton.mahacott.util.TextUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuctionReports extends AppCompatActivity {
    TableLayout table_layout;

    String FromDate="";
    String ToDate="";
    String PurchaserName="";
    String PurchaseRate="";
    String AuctionQty="";
    String AuctionDate="";
    String Commodity="";
    String Variety="";
    String Grade="";

    int fontSize=18;

    protected static final ViewGroup.LayoutParams lp_fill_fill = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

     ArrayList<String> PurchaserNameList=new ArrayList<String>();
    ArrayList<String> PurchaseRateList=new ArrayList<String>();
    ArrayList<String> AuctionQtyList=new ArrayList<String>();
    ArrayList<String> AuctionDateList=new ArrayList<String>();
    ArrayList<String> CommodityList=new ArrayList<String>();
    ArrayList<String> VarietyList=new ArrayList<String>();
    ArrayList<String> GradeList=new ArrayList<String>();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_reports);
        table_layout=(TableLayout)findViewById(R.id.tableauctionreport);
        FromDate = getIntent().getExtras().getString("FROMDATE");
        Log.d("FROMDATEFORMATE",FromDate);

        ToDate = getIntent().getExtras().getString("TODATE");
        Log.d("TODATEFORMATE",ToDate);
        getAuctiondetailsReports(FromDate,ToDate);
    }



    public void getAuctiondetailsReports(String fromDate, String toDate){
        Log.d("sumit==",fromDate);

        ReportsService reportsService = new ReportsService(getApplicationContext());
        reportsService.getAutionDetails(fromDate, toDate, new HttpResponseListener<JSONObject>() {


            @Override
            public void getResponse(JSONObject response) {

                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    System.out.println("length of array is " + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObj = (JSONObject) jsonArray.get(i);
                        PurchaserName = jsonObj.getString("PURCHASERNAME");
                        PurchaseRate = jsonObj.getString("PURCRATE");
                        AuctionQty = jsonObj.getString("AUCTIONQTY");
                        AuctionDate = jsonObj.getString("AUCTIONDATE");
                        Commodity = jsonObj.getString("COMMODITY");
                        Variety = jsonObj.getString("VARIETY");
                        Grade = jsonObj.getString("GRADE");


                        PurchaserNameList.add(PurchaserName);
                        PurchaseRateList.add(PurchaseRate);
                        AuctionQtyList.add(AuctionQty);
                        AuctionDateList.add(AllUtils.getFormattedDateForCurrentDate(AuctionDate));
                        CommodityList.add(Commodity);
                        VarietyList.add(Variety);
                        GradeList.add(Grade);
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

        TextView PurchaserNameColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        PurchaserNameColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        PurchaserNameColumnLabel.setGravity(Gravity.CENTER);
        PurchaserNameColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        PurchaserNameColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        PurchaserNameColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Purchaser Name") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        PurchaserNameColumnLabel.setPadding(10, 8, 5, 2);
        PurchaserNameColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(PurchaserNameColumnLabel);

        TextView purchrateColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };
        purchrateColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        purchrateColumnLabel.setGravity(Gravity.CENTER);
        purchrateColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        purchrateColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        purchrateColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Purchase Rate") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        purchrateColumnLabel.setPadding(10, 8, 5, 2);
        purchrateColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(purchrateColumnLabel);

        // TextView unitColumnLabel = new TextView(this);
        TextView quantityColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        quantityColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        quantityColumnLabel.setGravity(Gravity.RIGHT);
        quantityColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        quantityColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        quantityColumnLabel
                .setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Quantity") + "$$", "$$", new StyleSpan(
                        Typeface.BOLD)));
        quantityColumnLabel.setPadding(10, 8, 5, 2);
        quantityColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(quantityColumnLabel);

        TextView auctiondateColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        auctiondateColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        auctiondateColumnLabe.setGravity(Gravity.CENTER);
        auctiondateColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        auctiondateColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        auctiondateColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Auction Date") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        auctiondateColumnLabe.setPadding(10, 8, 5, 2);
        auctiondateColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(auctiondateColumnLabe);

        TextView commoColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        commoColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        commoColumnLabe.setGravity(Gravity.CENTER);
        commoColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        commoColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        commoColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Commodity") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        commoColumnLabe.setPadding(10, 8, 5, 2);
        commoColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(commoColumnLabe);

        TextView varietyColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        varietyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        varietyColumnLabe.setGravity(Gravity.CENTER);
        varietyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        varietyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        varietyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Variety") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        varietyColumnLabe.setPadding(10, 8, 5, 2);
        varietyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(varietyColumnLabe);

        TextView gradeColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        gradeColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gradeColumnLabe.setGravity(Gravity.CENTER);
        gradeColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        gradeColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        gradeColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Grade") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        gradeColumnLabe.setPadding(10, 8, 5, 2);
        gradeColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(gradeColumnLabe);

        table_layout.addView(tableColumnLayout);

        System.out.println("gridheapNolist---------" + PurchaserNameList.size());
        for (int i = 0; i < PurchaserNameList.size(); i++) {
            TableRow tableRowLayout = new TableRow(this);
            tableRowLayout.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView purchasernameRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            purchasernameRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            purchasernameRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            purchasernameRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            purchasernameRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + PurchaserNameList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            purchasernameRowLabel.setPadding(10, 8, 5, 2);
            purchasernameRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(purchasernameRowLabel);

            TextView rateRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            rateRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rateRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            rateRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            rateRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + PurchaseRateList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            rateRowLabel.setPadding(10, 8, 5, 2);
            rateRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(rateRowLabel);

            TextView qtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView qtyRowLabel = new TextView(this);
            qtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            qtyRowLabel.setGravity(Gravity.RIGHT);
            qtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            qtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            qtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + AuctionQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            qtyRowLabel.setPadding(10, 8, 5, 2);
            qtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(qtyRowLabel);

            TextView dateRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            dateRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dateRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            dateRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            dateRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + AuctionDateList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            dateRowLabel.setPadding(10, 8, 5, 2);
            dateRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(dateRowLabel);

            TextView commoRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            commoRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            commoRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            commoRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            commoRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + CommodityList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            commoRowLabel.setPadding(10, 8, 5, 2);
            commoRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(commoRowLabel);

            TextView varietyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            varietyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            varietyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            varietyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            varietyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + VarietyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            varietyRowLabel.setPadding(10, 8, 5, 2);
            varietyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(varietyRowLabel);

            TextView gradeRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            gradeRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            gradeRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            gradeRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            gradeRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + GradeList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            gradeRowLabel.setPadding(10, 8, 5, 2);
            gradeRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(gradeRowLabel);



            table_layout.addView(tableRowLayout);

        }

    }


    public static String safeTrim(String str) {
        return str != null ? str.trim() : str;
    }
}
