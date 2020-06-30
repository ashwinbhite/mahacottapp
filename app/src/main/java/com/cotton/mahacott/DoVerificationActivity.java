package com.cotton.mahacott;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.DoVerificationService;
import com.cotton.mahacott.util.AllUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoVerificationActivity extends AppCompatActivity {

    static final int CURRENT_DATE_DIALOG_ID = 0;
    private Button btnDownload;

    private String currentWorkingDate;
    private Button btnDODate;
    int year,month,date;
    String selectedDoDate;
    private String strDeliveryDate;
    private String strBuyerCode;
    private Date cDate;
    EditText edtDoNumber;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_verification);
        //checkPermission();
        isStoragePermissionGranted();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        edtDoNumber = (EditText)findViewById(R.id.edtDoNumber);
        btnDownload= (Button) findViewById(R.id.btnDownload);
        btnDODate = (Button)findViewById(R.id.btnDODate);
        currentWorkingDate = getIntent().getExtras().getString("CURRENT_DATE");
        currentWorkingDate=AllUtils.getFormattedDateForXML(currentWorkingDate);
        System.out.println("CURRENT DATE==="+currentWorkingDate);

        mActivity = DoVerificationActivity.this;
        btnDODate.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(CURRENT_DATE_DIALOG_ID);
            }
        });
        //get the current date
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

        if(currentWorkingDate!=""){

            btnDODate.setText(currentWorkingDate);
            selectedDoDate = btnDODate.getText().toString();

        }else{
            updateDate();
        }



        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                closeKeyBoard();

                DoVerificationService doVerifyService = new DoVerificationService(getApplicationContext());
                doVerifyService.getBuyerCode(edtDoNumber.getText().toString(), btnDODate.getText().toString(),
                        new HttpResponseListener<JSONObject>() {

                            @Override
                            public void getResponse(JSONObject response) {
                                // TODO Auto-generated method stub
                                System.out.println("ACTIVITY:"+response);
                                try{

                                    JSONObject jsonObj = response;
                                    strBuyerCode = jsonObj.getString("BuyerCode");
                                    System.out.println("BUYER CODE=="+strBuyerCode);

                                    strDeliveryDate = AllUtils.getFormattedDateForSqlDashnew(btnDODate.getText().toString());
                                    System.out.println("DATE========="+strDeliveryDate);

                                    String URL ="https://mahacotton.com/PDFGeneration.ashx?ReportName=DELIVERY_ORDER&Do_No="+
                                            edtDoNumber.getText().toString()+
                                            "&Trader_Code="+strBuyerCode+"&Do_Date="+strDeliveryDate.trim()+"";

                                    //new DownloadTask(DoVerificationActivity.this,URL);
//								DownloadFile downloadFile = new DownloadFile(URL, getApplicationContext());
//								downloadFile.downloadAndOpenPDF();
                                    Intent intent = new Intent(getApplicationContext(), DownloadDocActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("URL", URL);
                                    intent.putExtras(bundle);
                                    startActivity(intent);


                                }catch(JSONException err){
                                    Toast.makeText(getApplicationContext(), "Invalid DO-Number or DO-Date.", Toast.LENGTH_LONG).show();
                                    err.printStackTrace();

                                }
                            }
                        });
            }
        });
    }


    private void updateDate() {
        // TODO Auto-generated method stub

        btnDODate.setText(new StringBuilder()
                .append(date < 10 ? ("0" + date) : (date))
                .append("-")
                .append((month < 9 ? ("0" + (month + 1))
                        : (month + 1))).append("-").append(year)
                .append(" "));

        selectedDoDate = date + "-" + (month + 1) + "-" + year;

        strDeliveryDate = btnDODate.getText().toString();
        strDeliveryDate= AllUtils.getFormattedDateForSqlDashnew(strDeliveryDate);
        System.out.println("DeliveryDate=======after button click --------"+strDeliveryDate);
//		getInvoiceNumberList();
    }

    //the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("DatePicker: inside from date condition"+view.getId());
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
                return new DatePickerDialog(this,dateSetListener, year, month, date);

        }
        return null;
    }

    public void onClick(View v){

        String strchequeDate = btnDODate.getText().toString();
        //	getInvoiceNumberList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try{
            cDate = (Date) sdf.parse(strDeliveryDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
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
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("granted","Permission is granted");
                return true;
            } else {

                Log.v("revoked","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("granted","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("granted","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

}
