package com.cotton.mahacott;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reports extends AppCompatActivity {

    String pattern;
    Button btnShow;
    Spinner spinselectReportName;
    Button btnFromDate,btnToDate;
    DatePickerDialog datePickerDialog;
    private ArrayList<String> ReportList=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        btnShow=(Button)findViewById(R.id.btnReportShow);

        pattern = "dd/MM/yyyy";
        final String dateInString =new SimpleDateFormat(pattern).format(new Date());

        btnFromDate=(Button)findViewById(R.id.btnFromDate);
        btnFromDate.setText(dateInString);
        btnToDate=(Button)findViewById(R.id.btnToDate);
        btnToDate.setText(dateInString);
        spinselectReportName=(Spinner)findViewById(R.id.selectReportName);

        ReportList.add("Pending Do");
        ReportList.add("Auction Report");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ReportList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinselectReportName.setAdapter(adapter);


        btnFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Reports.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                btnFromDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                //flag=true;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });


        btnToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Reports.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                btnToDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                                //flag=true;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();



            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinselectReportName.getSelectedItem().toString().equals("Pending Do")){
                    Intent intent=new Intent(getApplicationContext(),PendingDoReports.class);
                    intent.putExtra("FROMDATE",btnFromDate.getText().toString());
                    Log.d("Fromdate-----",btnFromDate.getText().toString());

                    intent.putExtra("TODATE",btnToDate.getText().toString());
                    Log.d("Fromdate-----",btnToDate.getText().toString());

                    startActivity(intent);

                }
                if(spinselectReportName.getSelectedItem().toString().equals("Auction Report")){
                    Intent intent=new Intent(getApplicationContext(),AuctionReports.class);
                    intent.putExtra("FROMDATE",btnFromDate.getText().toString());
                    Log.d("Fromdate-----",btnFromDate.getText().toString());

                    intent.putExtra("TODATE",btnToDate.getText().toString());
                    Log.d("ToDate-----",btnToDate.getText().toString());

                    startActivity(intent);

                }

            }
        });








    }
}
