package com.cotton.mahacott;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cotton.mahacott.service.LoginService;
import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.util.AES;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnNext;
    EditText edtTxtUsername;
    String strUsername;
    String strZoneCode;
    String strPassword;
    String strUserId;
    String strEmployeeCode;
    String strCenterCode;
    AlertDialog.Builder builder;
    private boolean isButtonClicked = false;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = (Button) findViewById(R.id.btnNext);
        edtTxtUsername = (EditText) findViewById(R.id.edtUsername);

            btnNext.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
//                if (v.getId() == R.id.btnNext) {
//                    isButtonClicked = !isButtonClicked; // toggle the boolean flag
//                    v.setBackgroundResource(isButtonClicked ? R.drawable.roundcorner_button_fade : R.drawable.roundcorner_button);
//                }

                progress = new ProgressDialog(MainActivity.this);
                progress.setTitle("Please Wait!!");
                progress.setMessage("Wait!!");
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                closeKeyBoard();
                strUsername = edtTxtUsername.getText().toString();
                getUserNameDetails(strUsername);
                }
            });

    }

    public void getUserNameDetails(String userName){

        if(isNetworkAvailable()==true){
            final LoginService loginService = new LoginService(this, userName);
            loginService.getUsernameDetails(new HttpResponseListener<JSONObject>() {

                @Override
                public void getResponse(JSONObject response) {
                    // TODO Auto-generated method stub

                    try{
//                        btnNext.setEnabled(true);
                        progress.hide();

                        JSONArray jsonArray = new JSONArray();
                        jsonArray = response.getJSONArray("Information");
                        JSONObject jsonObj = new JSONObject();
                        jsonObj = (JSONObject) jsonArray.get(0);
                        if(jsonArray.length()<1 ){
                            Toast.makeText(getApplicationContext(),"Enter Correct UserName." , Toast.LENGTH_SHORT).show();
                        }else{
                            strZoneCode = jsonObj.getString("ZONECODE");
                            strPassword = jsonObj.getString("pass");
                            strUserId = jsonObj.getString("USERID");
                            SaveSharedPreference.setUserCode(getApplicationContext(), strUserId);
                            SaveSharedPreference.setUserName(getApplicationContext(), strUsername);
                            SaveSharedPreference.setzonecode(getApplicationContext(), strZoneCode);

                            String originalPasword= AES.decrypt(strPassword);
                            System.out.println("ORIGINAL PASSWORD==="+originalPasword);
                            SaveSharedPreference.setPassword(getApplicationContext(), originalPasword);
                            loginService.checkUserStatus(strUsername,strZoneCode,new HttpResponseListener<JSONObject>() {

                                @Override
                                public void getResponse(JSONObject response) {
                                    try{

                                        JSONArray jsonArray = new JSONArray();
                                        jsonArray = response.getJSONArray("Data");
                                        JSONObject jsonObj = new JSONObject();

                                        if(jsonArray.length()<1){
                                            Toast.makeText(getApplicationContext(),"No centers available.." , Toast.LENGTH_SHORT).show();
                                        }else{

                                            jsonObj = (JSONObject) jsonArray.get(0);
                                            if(jsonObj.getString("STATUS").equalsIgnoreCase("false")){
                                                String error = jsonObj.getString("MSG");
                                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();

                                            }else{
                                                strCenterCode = jsonObj.getString("CENTERCODE");
                                                strEmployeeCode = jsonObj.getString("EMPLOYECODE");
                                                SaveSharedPreference.setcentercodelist(getApplicationContext(), strCenterCode);
                                                Intent intent = new Intent(MainActivity.this,PasswordActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("zoneCode", strZoneCode);
                                                bundle.putString("centerCode", strCenterCode);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                    catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });


						/*	Intent intent = new Intent(MainActivity.this,PasswordActivity.class);
							Bundle bundle = new Bundle();

							bundle.putString("zoneCode", strZoneCode);
							bundle.putString("centerCode", strCenterCode);
							intent.putExtras(bundle);
							startActivity(intent);*/
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"No user details available." , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Please check internet connection." , Toast.LENGTH_SHORT).show();
        }

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




    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        builder = new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //finish();
                        finishAffinity();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("");
        alert.show();
    }

       /* Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/
    }

