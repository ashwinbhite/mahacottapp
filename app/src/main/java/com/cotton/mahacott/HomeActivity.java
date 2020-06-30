package com.cotton.mahacott;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.DateUtilityService;
import com.cotton.mahacott.service.LoginService;
import com.cotton.mahacott.service.MobileVersionService;
import com.cotton.mahacott.service.SeedStockStatusService;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements HttpResponseListener<JSONObject> {

    Button btnSeedStockEntry;
    Button btnDeliveryDetailsEntry;
    Button btnDeliveryOrderVerify;
    Button btnLateLiftingReciept;
    Button btnlogout;
    Button btnProcurement;
    Button btnIntimationLetter;
    Button btnFutureAuction;
    Button btnStockStatus;
    Button btnReport;
    String strUsername;
    String strCurrentDate;
    String seasonMstId;
    TextView txtUsername, txtCentername;
    String mFileUrl;
    String versionName="";
    Float mVersion, appVersion;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //update
        try {
            versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            Log.d("VERSION=", versionName);
            appVersion = Float.parseFloat(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//SaveSharedPreference.getPincode(mContext)
        MobileVersionService mobileVersionService = new MobileVersionService();
        mobileVersionService.getAppVersion(getApplicationContext(), new HttpResponseListener<JSONObject>() {
            @Override
            public void getResponse(JSONObject response) {

                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray = response.getJSONArray("Information");
                    JSONObject jsonObj = new JSONObject();
                    jsonObj = (JSONObject) jsonArray.get(0);
                    mVersion = Float.parseFloat(jsonObj.getString("VERSION"));
                    mFileUrl = jsonObj.getString("URL");

                    if (mVersion > appVersion) {
                        updateApp(mFileUrl);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });



        if (SaveSharedPreference.getBranchCode(HomeActivity.this).length() == 0) {
            Intent loginInent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(loginInent);
        }


        if (SaveSharedPreference.getUserName(HomeActivity.this).length() == 0) {
            SaveSharedPreference.clearUserName(getApplicationContext());
            Intent loginInent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(loginInent);
        } else {

            strUsername = SaveSharedPreference.getUserName(getApplicationContext());

        }
        String centerName = SaveSharedPreference.getBranchName(getApplicationContext());
        System.out.println("CENTER NAME=====" + centerName);

        btnSeedStockEntry = (Button) findViewById(R.id.btnSeedStockEntry);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnDeliveryDetailsEntry = (Button) findViewById(R.id.btnDeliveryDetailsEntry);
        btnDeliveryOrderVerify = (Button) findViewById(R.id.btnDeliveryOrderVerify);
        //btnLateLiftingReciept=(Button)findViewById(R.id.btnLateLiftingReciept);
        btnIntimationLetter = (Button) findViewById(R.id.btnIntimationLetter);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText(strUsername);
        txtCentername = (TextView) findViewById(R.id.centername);
        txtCentername.setText(centerName);
        btnProcurement = (Button) findViewById(R.id.btnProcurement);
        btnFutureAuction = (Button) findViewById(R.id.btnFutureAuctionSubmision);
        btnStockStatus = (Button) findViewById(R.id.btnStockStatus);
        btnReport = findViewById(R.id.btnReports);


        DateUtilityService dateUtilityService = new DateUtilityService(this);
        dateUtilityService.getDate(versionName,new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    //JSONObject jsonObj= new JSONObject(response);
                    strCurrentDate = response.getString("Date");
                    getSeasonMstId(strCurrentDate);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        btnSeedStockEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), SeedStockEntryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);

                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        btnFutureAuction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), FutureAuctionSubmision.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnDeliveryDetailsEntry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DeliveryOrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnDeliveryOrderVerify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), DoVerificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        btnProcurement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(getApplicationContext(), ProcurementNew.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnIntimationLetter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IntimationLetterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

		/*btnLateLiftingReciept.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),OpeningBalance.class);
				Bundle bundle = new Bundle();
				bundle.putString("CURRENT_DATE", strCurrentDate);
				System.out.println("IN HOME ,DATE====="+strCurrentDate);
				intent.putExtras(bundle);
				startActivity(intent);
//				Toast.makeText(getApplicationContext(), "This option is currently disable", Toast.LENGTH_SHORT).show();
			}
		});
*/
        btnStockStatus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeedStockStatus.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reports.class);
                Bundle bundle = new Bundle();
                bundle.putString("CURRENT_DATE", strCurrentDate);
                System.out.println("IN HOME ,DATE=====" + strCurrentDate);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        btnlogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openAlert(getWindow().getDecorView());

            }
        });

    }

    private void getSeasonMstId(String strCurrentDate) {

        SeedStockStatusService seedSeasonDateService = new SeedStockStatusService(
                this);
        seedSeasonDateService.getSeasonDate(strCurrentDate,
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

                                seasonMstId = jsonObj.getString("SeasonMstId");

                                Log.d("seasonMstId sumit",seasonMstId);
                                SaveSharedPreference.setSeasonMstId(HomeActivity.this,seasonMstId);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    private void updateApp(final String mFileUrl) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mFileUrl)));
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);
        dialog = builder.show();
    }


    public void openAlert(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Are you sure,you want to logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        deleteFromUserLockTable();

                    }


                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*finish();*/
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteFromUserLockTable() {

        System.out.println("****" + SaveSharedPreference.getUserCode(HomeActivity.this));
        // TODO Auto-generated method stub
        LoginService loginService = new LoginService(getApplicationContext(), SaveSharedPreference.getUserName(HomeActivity.this));

        loginService.logout(SaveSharedPreference.getUserCode(HomeActivity.this), new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                // TODO Auto-generated method stub
				/*SaveSharedPreference.clearUserName(getApplicationContext());
				Intent intent = new Intent(HomeActivity.this,MainActivity.class);
				startActivity(intent);*/
            }
        });
        SaveSharedPreference.clearUserName(getApplicationContext());
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //this.moveTaskToBack(true);

    }

    @Override
    public void getResponse(JSONObject response) {
        // TODO Auto-generated method stub

    }
}
