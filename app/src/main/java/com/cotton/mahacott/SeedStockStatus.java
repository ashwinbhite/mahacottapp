package com.cotton.mahacott;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.SeedStockStatusService;
import com.cotton.mahacott.util.TextUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeedStockStatus extends AppCompatActivity {

    TableLayout table_layout;

    HorizontalScrollView hsv;
    int fontSize = 18;
    String CurrentDate = "";
    String CenterCode = "";
    String SeasonStartDate = "";
    String SeasonEndDate = "";
    String CommoditMstId = "";

    String totProc = "";
    String totAuc = "";
    String totDelivery = "";
    String totsbnd = "";
    String totFutureAuc = "";
    String totReady = "";
    String totFutureDelivery = "";
    String variety = "";


    List<String> nameList = new ArrayList<>();
    /*
     * List<String> totProcList = new ArrayList<>(); List<String> totAucList =
     * new ArrayList<>(); List<String> totDeliveryList = new ArrayList<>();
     * List<String> totFutureAucList = new ArrayList<>(); List<String>
     * totsbndList = new ArrayList<>(); List<String> totReadyList = new
     * ArrayList<>(); List<String> totFutureDeliveryList = new ArrayList<>();
     *
     * List<String> varietyList = new ArrayList<>();
     */

    List<String> BBFaqList = new ArrayList<>();
    List<String> BBSplList = new ArrayList<>();
    List<String> LRAFaqList = new ArrayList<>();
    List<String> H4H6FaqList = new ArrayList<>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_stock_status);
        CurrentDate = getIntent().getExtras().getString("CURRENT_DATE");

        nameList.add("Total Procurement");
        nameList.add("Total Auction");
        nameList.add("Total Delivery");
        nameList.add("Total SBND");
        nameList.add("Total Future Auction");
        nameList.add("Total Ready");
        nameList.add("Total Future Delivery");

        BBFaqList.clear();
        BBSplList.clear();
        LRAFaqList.clear();
        H4H6FaqList.clear();

        SeedStockStatusService seedSeasonDateService = new SeedStockStatusService(
                this);
        seedSeasonDateService.getSeasonDate(CurrentDate,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {

                        try {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("RES");
                            JSONObject jsonObj = new JSONObject();
                            jsonObj = (JSONObject) jsonArray.get(0);
                            if (response.getString("msg").equalsIgnoreCase(
                                    "SUCCESS")) {

                                System.out.println("---------STOCK--"
                                        + jsonObj.getString("SeasonStartDate"));
                                System.out.println("---------STOCK--"
                                        + jsonObj.getString("SeasonStartDate"));

                                SeasonStartDate = jsonObj
                                        .getString("SeasonStartDate");
                                System.out.println("---------SeasonStartDate--"
                                        + SeasonStartDate);
                                SeasonEndDate = jsonObj
                                        .getString("SeasonEndtDate");
                                System.out.println("---------SeasonStartDate--"
                                        + SeasonEndDate);

                                getStaus();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

        table_layout = (TableLayout) findViewById(R.id.tableStockStatus);
        table_layout.removeAllViews();
        table_layout.setStretchAllColumns(true);
        table_layout.bringToFront();
        table_layout.clearFocus();

    }

    public void getStaus() {



        SeedStockStatusService seedStatusService = new SeedStockStatusService(
                this);
        seedStatusService.getStockStatus(CenterCode, CommoditMstId,
                CurrentDate, SeasonStartDate, SeasonEndDate,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {

                        try {

                            BBFaqList.clear();
                            BBSplList.clear();
                            LRAFaqList.clear();
                            H4H6FaqList.clear();
                            System.out.println("*******"+response.getJSONArray("RES").length());
                            if(response.getJSONArray("RES")==null || response.getJSONArray("RES").length()<=0)
                            {
                                Toast.makeText(getApplicationContext(),
                                        "Data Not Available",
                                        Toast.LENGTH_SHORT).show();

                            }else{

                                JSONArray jsonArray = new JSONArray();
                                jsonArray = response.getJSONArray("RES");
                                JSONObject jsonObj = new JSONObject();
                                System.out.println("length of array is "
                                        + jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonObj = (JSONObject) jsonArray.get(i);

                                    variety = jsonObj.getString("VARIETY");

                                    if (variety.equalsIgnoreCase("BB FAQ")) {

                                        BBFaqList.add(jsonObj.getString("PROCUREMENT_QTY"));
                                        BBFaqList.add(jsonObj.getString("AUCTION_QTY"));
                                        BBFaqList.add(jsonObj.getString("DELIVERY_QTY"));
                                        BBFaqList.add(jsonObj.getString("SBND"));
                                        BBFaqList.add(jsonObj.getString("FUTURE_AUCTION_QTY"));
                                        BBFaqList.add(jsonObj.getString("READY_STOCK"));
                                        BBFaqList.add(jsonObj.getString("FUTURE_DELV_QTY"));

                                    }
                                    if (variety.equalsIgnoreCase("BB SPL")) {

                                        BBSplList.add(jsonObj
                                                .getString("PROCUREMENT_QTY"));
                                        BBSplList.add(jsonObj
                                                .getString("AUCTION_QTY"));
                                        BBSplList.add(jsonObj
                                                .getString("DELIVERY_QTY"));
                                        BBSplList.add(jsonObj
                                                .getString("SBND"));
                                        BBSplList.add(jsonObj
                                                .getString("FUTURE_AUCTION_QTY"));
                                        BBSplList.add(jsonObj
                                                .getString("READY_STOCK"));
                                        BBSplList.add(jsonObj
                                                .getString("FUTURE_DELV_QTY"));

                                    }
                                    if (variety.equalsIgnoreCase("LRA FAQ")) {

                                        LRAFaqList.add(jsonObj
                                                .getString("PROCUREMENT_QTY"));
                                        LRAFaqList.add(jsonObj
                                                .getString("AUCTION_QTY"));
                                        LRAFaqList.add(jsonObj
                                                .getString("DELIVERY_QTY"));
                                        LRAFaqList.add(jsonObj
                                                .getString("SBND"));
                                        LRAFaqList.add(jsonObj
                                                .getString("FUTURE_AUCTION_QTY"));
                                        LRAFaqList.add(jsonObj
                                                .getString("READY_STOCK"));
                                        LRAFaqList.add(jsonObj
                                                .getString("FUTURE_DELV_QTY"));

                                    }
                                    if (variety.equalsIgnoreCase("H4H6 FAQ")) {

                                        H4H6FaqList.add(jsonObj
                                                .getString("PROCUREMENT_QTY"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("AUCTION_QTY"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("DELIVERY_QTY"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("SBND"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("FUTURE_AUCTION_QTY"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("READY_STOCK"));
                                        H4H6FaqList.add(jsonObj
                                                .getString("FUTURE_DELV_QTY"));

                                    }

                                }
                                System.out.println("LIST=="+BBFaqList);

                                drawTable();
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

        // COL 1
        TextView blankColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        blankColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        blankColumnLabel.setGravity(Gravity.CENTER);
        blankColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        blankColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        blankColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("") + "$$", "$$", new StyleSpan(Typeface.BOLD)));
        blankColumnLabel.setPadding(10, 8, 5, 2);
        blankColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(blankColumnLabel);

        // COL2
        TextView TotProcColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        TotProcColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TotProcColumnLabel.setGravity(Gravity.CENTER);
        TotProcColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        TotProcColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        TotProcColumnLabel.setText(TextUtility
                .setSpanBetweenTokens("$$" + safeTrim("BB SPL") + "$$", "$$",
                        new StyleSpan(Typeface.BOLD)));
        TotProcColumnLabel.setPadding(10, 8, 5, 2);
        TotProcColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(TotProcColumnLabel);

        // COL3
        TextView TotAucColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };
        TotAucColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TotAucColumnLabel.setGravity(Gravity.CENTER);
        TotAucColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        TotAucColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        TotAucColumnLabel.setText(TextUtility
                .setSpanBetweenTokens("$$" + safeTrim("BB FAQ") + "$$", "$$",
                        new StyleSpan(Typeface.BOLD)));
        TotAucColumnLabel.setPadding(10, 8, 5, 2);
        TotAucColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(TotAucColumnLabel);

        // COL4
        // TextView unitColumnLabel = new TextView(this);
        TextView TotDeliveryColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        TotDeliveryColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TotDeliveryColumnLabel.setGravity(Gravity.RIGHT);
        TotDeliveryColumnLabel
                .setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        TotDeliveryColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        TotDeliveryColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("LRA FAQ") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        TotDeliveryColumnLabel.setPadding(10, 8, 5, 2);
        TotDeliveryColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(TotDeliveryColumnLabel);

        // COL5
        TextView TotSbndColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        TotSbndColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TotSbndColumnLabe.setGravity(Gravity.CENTER);
        TotSbndColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        TotSbndColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        TotSbndColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("H4-H6-FAQ") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        TotSbndColumnLabe.setPadding(10, 8, 5, 2);
        TotSbndColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(TotSbndColumnLabe);

        table_layout.addView(tableColumnLayout);

        for (int i = 0; i < 7; i++) {
            TableRow tableRowLayout = new TableRow(this);
            tableRowLayout.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            //Col NameList
            TextView NameLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            NameLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            NameLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            NameLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            NameLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + nameList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            NameLabel.setPadding(10, 8, 5, 2);
            NameLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(NameLabel);

            if(BBSplList.size()>0){
                //Col BBSpl
                TextView BBSplRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                BBSplRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                BBSplRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                BBSplRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                BBSplRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                                + safeTrim("" + BBSplList.get(i)) + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                BBSplRowLabel.setPadding(10, 8, 5, 2);
                BBSplRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(BBSplRowLabel);
            }else
            {
                TextView BBSplRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                BBSplRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                BBSplRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                BBSplRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                BBSplRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                                + safeTrim(" ") + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                BBSplRowLabel.setPadding(10, 8, 5, 2);
                BBSplRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(BBSplRowLabel);
            }

            if(BBFaqList.size()>0){

                //Col BBFaq
                TextView BBFaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                BBFaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                BBFaqRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                BBFaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                BBFaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                                + safeTrim("" + BBFaqList.get(i)) + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                BBFaqRowLabel.setPadding(10, 8, 5, 2);
                BBFaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(BBFaqRowLabel);

            }else
            {
                TextView BBFaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                BBFaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                BBFaqRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                BBFaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                BBFaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                                + safeTrim(" ") + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                BBFaqRowLabel.setPadding(10, 8, 5, 2);
                BBFaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(BBFaqRowLabel);
            }

            if(LRAFaqList.size()>0){
                //col LraFaq
                TextView LRAFaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                LRAFaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                LRAFaqRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        fontSize);
                LRAFaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                LRAFaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                                + "" + safeTrim("" + LRAFaqList.get(i)) + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                LRAFaqRowLabel.setPadding(10, 8, 5, 2);
                LRAFaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(LRAFaqRowLabel);
            }else
            {
                TextView LRAFaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                LRAFaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                LRAFaqRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                        fontSize);
                LRAFaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                LRAFaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                                + "" + safeTrim(" ") + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                LRAFaqRowLabel.setPadding(10, 8, 5, 2);
                LRAFaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(LRAFaqRowLabel);
            }


            if(H4H6FaqList.size()>0){

                TextView H4H6FaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                H4H6FaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                H4H6FaqRowLabel
                        .setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                H4H6FaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                H4H6FaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                                + "" + safeTrim("" + H4H6FaqList.get(i)) + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                H4H6FaqRowLabel.setPadding(10, 8, 5, 2);
                H4H6FaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(H4H6FaqRowLabel);

            }else
            {
                TextView H4H6FaqRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

                };
                // TextView commRowLabel = new TextView(this);
                H4H6FaqRowLabel.setLayoutParams(new TableRow.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                H4H6FaqRowLabel
                        .setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                H4H6FaqRowLabel.setTextColor(getResources().getColor(
                        android.R.color.black));
                H4H6FaqRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                                + "" + safeTrim(" ") + "$$", "$$",
                        new StyleSpan(Typeface.NORMAL)));
                H4H6FaqRowLabel.setPadding(10, 8, 5, 2);
                H4H6FaqRowLabel.setBackground(getResources().getDrawable(
                        R.drawable.cell_border));
                tableRowLayout.addView(H4H6FaqRowLabel);
            }


            table_layout.addView(tableRowLayout);

        }
    }

    public static String safeTrim(String str) {
        return str != null ? str.trim() : str;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
		/*BBFaqList.clear();
		BBSplList.clear();
		LRAFaqList.clear();
		H4H6FaqList.clear();*/
    }
}
