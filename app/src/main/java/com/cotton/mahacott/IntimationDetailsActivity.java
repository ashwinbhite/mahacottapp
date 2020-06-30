package com.cotton.mahacott;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.SeedStockEntryService;
import com.cotton.mahacott.util.TextUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IntimationDetailsActivity extends AppCompatActivity {

    private TextView txtViewCommodity;
    private TextView txtViewAvailableQty;
    private TextView txtViewPendingQty;


    List<String> contractNolist = new ArrayList<>();
    List<String> totQtyList = new ArrayList<>();
    List<String> alreadyIntimatedList = new ArrayList<>();
    List<String> balanceList = new ArrayList<>();
    List<String> traderNamelist = new ArrayList<>();
    List<String> traderCodelist = new ArrayList<>();
    List<String> slotDetailsIdList = new ArrayList<>();
    List<String> contractDateList = new ArrayList<>();
    String strContractNo;
    String strTotQty;
    String strAlreadyIntimated;
    String strBalance;
    String strTraderName;
    String strTraderCode;

    HorizontalScrollView hsv;
    TableLayout table_layout;
    int fontSize = 18;

    String strCommodity, strGrade, strCurrentDate, strCommoMstId, strVarMstId,
            strgradeId, strSeasonMstId;
    private String strAvailableQty = "";
    private String strPendingQty = "";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimation_details);
        strCommodity = getIntent().getExtras().getString("COMMO");
        strGrade = getIntent().getExtras().getString("GRADE");
        strCurrentDate = getIntent().getExtras().getString("CURRENT_DATE");
        strCommoMstId = getIntent().getExtras().getString("COMMO_MST_ID");
        strVarMstId = getIntent().getExtras().getString("VAR_MST_ID");
        strgradeId = getIntent().getExtras().getString("GRADE_ID");
        strSeasonMstId = getIntent().getExtras().getString("SEASON_MST_ID");

        table_layout = (TableLayout) findViewById(R.id.tableStockReport);
        table_layout.removeAllViews();
        table_layout.setStretchAllColumns(true);
        table_layout.bringToFront();
        table_layout.clearFocus();

        txtViewCommodity = (TextView) findViewById(R.id.txtViewCommodity);
        txtViewAvailableQty = (TextView) findViewById(R.id.txtViewAvailableQty);
        txtViewPendingQty = (TextView) findViewById(R.id.txtViewPendingQty);

        // SET COMMODITY
        txtViewCommodity.setText(strCommodity + "/" + strGrade);

        // SET AVAILABLE QTY
        SeedStockEntryService serviceForAvailQty = new SeedStockEntryService(
                this);
        serviceForAvailQty.getSeedStockQty("L", strCurrentDate, strCommoMstId,
                strVarMstId, strgradeId,
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
                                        + jsonObj.getString("STOCK"));

                                // int stockPending
                                // =Integer.parseInt(jsonObj.getString("STOCK"));
                                txtViewAvailableQty.setText(jsonObj
                                        .getString("STOCK"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

        // SET AVAILABLE QTY
        SeedStockEntryService serviceForPendQty = new SeedStockEntryService(
                this);
        serviceForPendQty.getSeedStockQty("S", strCurrentDate, strCommoMstId,
                strVarMstId, strgradeId,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("RES");
                            JSONObject jsonObj = new JSONObject();
                            jsonObj = (JSONObject) jsonArray.get(0);
                            if (response.getString("msg").equalsIgnoreCase(
                                    "SUCCESS")) {

                                System.out.println("---------STOCK--"
                                        + jsonObj.getString("STOCK"));

                                // int stockPending
                                // =Integer.parseInt(jsonObj.getString("STOCK"));
                                txtViewPendingQty.setText(jsonObj
                                        .getString("STOCK"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
        getIntimationDetails();

    }


    private void getIntimationDetails() {

        SeedStockEntryService seedStockEntryService = new SeedStockEntryService(getApplicationContext());
        seedStockEntryService.getIntimationDetails(strgradeId,strCommoMstId,strVarMstId,new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    contractNolist.clear();
                    totQtyList.clear();
                    alreadyIntimatedList.clear();
                    balanceList.clear();
                    traderNamelist.clear();
                    traderCodelist.clear();
                    slotDetailsIdList.clear();
                    contractDateList.clear();

                    JSONArray jsonArray = new JSONArray();
                    jsonArray = response.getJSONArray("RES");
                    JSONObject jsonObj = new JSONObject();
                    System.out.println("length of array is "+ jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObj = (JSONObject) jsonArray.get(i);

                        strTraderCode = jsonObj.getString("TRADER_CODE");
                        strTraderName = jsonObj.getString("TRADER_NAME");
                        strContractNo = jsonObj.getString("CONTRACT_NO");
                        strAlreadyIntimated = jsonObj.getString("ALREADY_INT_QTY");
                        strBalance = jsonObj.getString("BAL_QTY");
                        strTotQty=jsonObj.getString("TOT_QTY");
                        String 	strSlotDetailsId=jsonObj.getString("SLOT_DETAILS_ID");
                        String contractDate=jsonObj.getString("CONTRACT_DATE");;



                        contractNolist.add(strContractNo);
                        totQtyList.add(strTotQty);
                        alreadyIntimatedList.add(strAlreadyIntimated);
                        balanceList.add(strBalance);
                        traderNamelist.add(strTraderName);
                        traderCodelist.add(strTraderCode);
                        slotDetailsIdList.add(strSlotDetailsId);
                        contractDateList.add(contractDate);

                    }

                    drawTable();
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

        // HEADER 1(Contract No)
        TextView ContractNoColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };

        ContractNoColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ContractNoColumnLabel.setGravity(Gravity.CENTER);
        ContractNoColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        ContractNoColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        ContractNoColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Contract No") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        ContractNoColumnLabel.setPadding(10, 8, 5, 2);
        ContractNoColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(ContractNoColumnLabel);

        // HEADER 5
		/*TextView traderCodeColumnLabe = new TextView(getApplicationContext()) {

		};

		traderCodeColumnLabe.setLayoutParams(new TableRow.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		traderCodeColumnLabe.setGravity(Gravity.CENTER);
		traderCodeColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		traderCodeColumnLabe.setTextColor(getResources().getColor(
				android.R.color.black));
		traderCodeColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
				+ safeTrim("TraderCode") + "$$", "$$", new StyleSpan(
				Typeface.BOLD)));
		traderCodeColumnLabe.setPadding(10, 8, 5, 2);
		traderCodeColumnLabe.setBackground(getResources().getDrawable(
				R.drawable.cell_border));
		tableColumnLayout.addView(traderCodeColumnLabe);*/

        // HEADER 6
        TextView traderNameColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        traderNameColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        traderNameColumnLabe.setGravity(Gravity.CENTER);
        traderNameColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        traderNameColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        traderNameColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Purchaser") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        traderNameColumnLabe.setPadding(10, 8, 5, 2);
        traderNameColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(traderNameColumnLabe);

        // HEADER 2(Total Qty)
        TextView totQtyColumnLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
        };
        totQtyColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        totQtyColumnLabel.setGravity(Gravity.CENTER);
        totQtyColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        totQtyColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        totQtyColumnLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("TotalQty") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        totQtyColumnLabel.setPadding(10, 8, 5, 2);
        totQtyColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(totQtyColumnLabel);

        // HEADER 3(Already Intimated)
        // TextView unitColumnLabel = new TextView(this);
        TextView alreadyIntimatedColumnLabel = new android.support.v7.widget.AppCompatTextView(
                getApplicationContext()) {

        };
        alreadyIntimatedColumnLabel.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        alreadyIntimatedColumnLabel.setGravity(Gravity.RIGHT);
        alreadyIntimatedColumnLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                fontSize);
        alreadyIntimatedColumnLabel.setTextColor(getResources().getColor(
                android.R.color.black));
        alreadyIntimatedColumnLabel.setText(TextUtility.setSpanBetweenTokens(
                "$$" + safeTrim("Intimated") + "$$", "$$", new StyleSpan(
                        Typeface.BOLD)));
        alreadyIntimatedColumnLabel.setPadding(10, 8, 5, 2);
        alreadyIntimatedColumnLabel.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(alreadyIntimatedColumnLabel);

        // HEADER 4 (Balance)
        TextView balanceColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };

        balanceColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        balanceColumnLabe.setGravity(Gravity.CENTER);
        balanceColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        balanceColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        balanceColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                        + safeTrim("Balance") + "$$", "$$",
                new StyleSpan(Typeface.BOLD)));
        balanceColumnLabe.setPadding(10, 8, 5, 2);
        balanceColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(balanceColumnLabe);

        // HEADER 7(Intimatation Qty)
        TextView intimatationQtyColumnLabe = new android.support.v7.widget.AppCompatTextView(
                getApplicationContext()) {

        };
        intimatationQtyColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        intimatationQtyColumnLabe.setGravity(Gravity.CENTER);
        intimatationQtyColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                fontSize);
        intimatationQtyColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        intimatationQtyColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("IntimationQty") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        intimatationQtyColumnLabe.setPadding(10, 8, 5, 2);
        intimatationQtyColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(intimatationQtyColumnLabe);
        // table_layout.addView(tableColumnLayout);

        // HEADER 8(Intimate)
        TextView intimateColumnLabe = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

        };
        intimateColumnLabe.setLayoutParams(new TableRow.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        intimateColumnLabe.setGravity(Gravity.CENTER);
        intimateColumnLabe.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        intimateColumnLabe.setTextColor(getResources().getColor(
                android.R.color.black));
        intimateColumnLabe.setText(TextUtility.setSpanBetweenTokens("$$"
                + safeTrim("Intimate") + "$$", "$$", new StyleSpan(
                Typeface.BOLD)));
        intimateColumnLabe.setPadding(10, 8, 5, 2);
        intimateColumnLabe.setBackground(getResources().getDrawable(
                R.drawable.cell_border));
        tableColumnLayout.addView(intimateColumnLabe);
        // ADD ROW TO TABLE
        table_layout.addView(tableColumnLayout);

        // Dynamic table
        // System.out.println("gridheapNolist---------" +gridheapNolist.size());

        for (int i = 0; i < contractNolist.size(); i++) {
            final TableRow tableRowLayout = new TableRow(this);
            tableRowLayout.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            // COL1
            TextView contractNoRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {
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
            contractNoRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            contractNoRowLabel
                    .setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            contractNoRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            contractNoRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + "" + safeTrim("" + contractNolist.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            contractNoRowLabel.setPadding(10, 10, 5, 10);
            contractNoRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(contractNoRowLabel);

            // COL 5
			/*TextView traderCodeRowLabel = new TextView(getApplicationContext()) {

			};
			// TextView uomRowLabel = new TextView(this);
			traderCodeRowLabel.setLayoutParams(new TableRow.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			traderCodeRowLabel
					.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
			traderCodeRowLabel.setTextColor(getResources().getColor(
					android.R.color.black));
			traderCodeRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
					+ safeTrim("" + traderCodelist.get(i)) + "$$", "$$",
					new StyleSpan(Typeface.NORMAL)));
			traderCodeRowLabel.setPadding(10, 10, 5, 10);
			traderCodeRowLabel.setBackground(getResources().getDrawable(
					R.drawable.cell_border));
			tableRowLayout.addView(traderCodeRowLabel);*/

            // COL 6
            TextView traderNameRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            traderNameRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            traderNameRowLabel
                    .setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            traderNameRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            traderNameRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + traderNamelist.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            traderNameRowLabel.setPadding(10, 10, 5, 10);
            traderNameRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(traderNameRowLabel);

            // COL2
            TextView totQtyRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView commRowLabel = new TextView(this);
            totQtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            totQtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            totQtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            totQtyRowLabel.setText(TextUtility.setSpanBetweenTokens("$$" + ""
                            + safeTrim("" + totQtyList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            totQtyRowLabel.setPadding(10, 10, 5, 10);
            totQtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(totQtyRowLabel);

            // COL3
            TextView alreadyIntimatedRowLabel = new android.support.v7.widget.AppCompatTextView(
                    getApplicationContext()) {

            };
            // TextView qtyRowLabel = new TextView(this);
            alreadyIntimatedRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            alreadyIntimatedRowLabel.setGravity(Gravity.RIGHT);
            alreadyIntimatedRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    fontSize);
            alreadyIntimatedRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            alreadyIntimatedRowLabel.setText(TextUtility.setSpanBetweenTokens(
                    "$$" + safeTrim("" + alreadyIntimatedList.get(i)) + "$$",
                    "$$", new StyleSpan(Typeface.NORMAL)));
            alreadyIntimatedRowLabel.setPadding(10, 10, 5, 10);
            alreadyIntimatedRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(alreadyIntimatedRowLabel);

            // COL 4
            TextView balRowLabel = new android.support.v7.widget.AppCompatTextView(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            balRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            balRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            balRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            balRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
                            + safeTrim("" + balanceList.get(i)) + "$$", "$$",
                    new StyleSpan(Typeface.NORMAL)));
            balRowLabel.setPadding(10, 10, 5, 10);
            balRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));
            tableRowLayout.addView(balRowLabel);

            // COL 7
            EditText intimatedQtyRowLabel = new android.support.v7.widget.AppCompatEditText(
                    getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            intimatedQtyRowLabel.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            intimatedQtyRowLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    fontSize);


            intimatedQtyRowLabel.setTextColor(getResources().getColor(
                    android.R.color.black));
            // saleTypeRowLabel.setText(TextUtility.setSpanBetweenTokens("$$"
            // + safeTrim("" + gridSaleTypeList.get(i)) + "$$", "$$",
            // new StyleSpan(Typeface.NORMAL)));
            intimatedQtyRowLabel.setPadding(10, 10, 5, 10);
            intimatedQtyRowLabel.setBackground(getResources().getDrawable(
                    R.drawable.cell_border));

            intimatedQtyRowLabel.setClickable(true);
            intimatedQtyRowLabel.requestFocus();
            intimatedQtyRowLabel.setFocusableInTouchMode(true);

            intimatedQtyRowLabel.setInputType(InputType.TYPE_CLASS_NUMBER);



            tableRowLayout.addView(intimatedQtyRowLabel);

            // COL 8
            final Button btnIntimate = new android.support.v7.widget.AppCompatButton(getApplicationContext()) {

            };
            // TextView uomRowLabel = new TextView(this);
            btnIntimate.setLayoutParams(new TableRow.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

            btnIntimate.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            // btnIntimate.setTextColor(getResources().getColor(
            // android.R.color.black));
            btnIntimate.setId(i);
            btnIntimate.setText("Intimate");
            btnIntimate.setPadding(10, 2, 5, 2);
            btnIntimate.setBackgroundColor(Color.GRAY);

            tableRowLayout.addView(btnIntimate);

            btnIntimate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    int Id = btnIntimate.getId();
                    EditText allotqty = (EditText) tableRowLayout.getChildAt(6);
                    String allotedqty = allotqty.getText().toString();
                    System.out.println("allotedqty---" + allotedqty);

                    String TraderCode = traderCodelist.get(Id);
                    String slotdetailsid = slotDetailsIdList.get(Id);
                    String contractString = contractNolist.get(Id);
                    String contractDate = contractDateList.get(Id);

                    Intimatetrader(slotdetailsid, allotedqty, TraderCode,
                            contractString, contractDate);

                }
            });

            table_layout.addView(tableRowLayout);
        }
    }


    public	void Intimatetrader(String slotdetailsid, String allotedqty, String TraderCode,String contractString,String contractDate)
    {
        SeedStockEntryService serviceForPendQty = new SeedStockEntryService(
                this);
        serviceForPendQty.sendIntimation(slotdetailsid,allotedqty, TraderCode,contractString,contractDate,
                new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {
                            String msg=response.getString("status");

                            Toast.makeText(getApplicationContext(), "Trader intimation is"+msg, Toast.LENGTH_SHORT).show();

                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });




    }

    public static String safeTrim(String str) {
        return str != null ? str.trim() : str;
    }
}
