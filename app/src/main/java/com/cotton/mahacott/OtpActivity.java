package com.cotton.mahacott;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.LoginService;
import com.cotton.mahacott.util.AES;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {

    String userCurrentPass = "";
    String prevPass1 = "";
    String prevPass2 = "";
    String receivedOtp = "";
    String strLoginName = "";

    Button btnVerify;
    Button btnNewPass;
    EditText edtTxtOtp;
    EditText edtTxtNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnNewPass = (Button) findViewById(R.id.btnNewPass);
        edtTxtOtp = (EditText) findViewById(R.id.edtTxtOtp);
        edtTxtNewPass = (EditText) findViewById(R.id.edtTxtNewPass);

        btnNewPass.setVisibility(View.GONE);
        edtTxtNewPass.setVisibility(View.GONE);

        strLoginName = SaveSharedPreference
                .getUserName(getApplicationContext());

        LoginService loginService = new LoginService(this, strLoginName);
        loginService.forgotPassword(new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                // TODO Auto-generated method stub
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonArray = response.getJSONArray("Information");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObj = jsonArray.getJSONObject(i);

                        userCurrentPass = jsonObj.getString("CURRENT_PASS");
                        try {

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (!jsonObj.getString("PREVIOUS_PASS1").equals(" ")) {
                            prevPass1 = jsonObj.getString("PREVIOUS_PASS1");
                            // prevPass2 = jsonObj.getString("PREVIOUS_PASS2");
                        } else {
                            prevPass1 = " ";
                            // prevPass2=" ";
                        }

                        receivedOtp = jsonObj.getString("OTP");

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (edtTxtOtp.getText().toString()
                        .equalsIgnoreCase(receivedOtp)) {

                    btnNewPass.setVisibility(View.VISIBLE);
                    edtTxtNewPass.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "OTP didn't match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNewPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                closeKeyBoard();

                String enteredPassword = edtTxtNewPass.getText().toString();
                try {
                    System.out.println("plain text--" + enteredPassword.trim());
                    enteredPassword = AES.encrypt(enteredPassword.trim());
                    System.out.println("after encryption--------"
                            + enteredPassword);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (enteredPassword.equals(userCurrentPass)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Password should be different than previous password!",
                            Toast.LENGTH_SHORT).show();
                }
                if (enteredPassword.equals(prevPass1)) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Password should be different than previous 2 password!",
                            Toast.LENGTH_SHORT).show();
                }
                if ((!enteredPassword.equals(userCurrentPass))
                        && (!enteredPassword.equals(prevPass1))) {

                    Toast.makeText(getApplicationContext(),
                            "Password change successfully!", Toast.LENGTH_SHORT)
                            .show();

                    // run update password ser
                    LoginService loginService = new LoginService(
                            getApplicationContext(), strLoginName);
                    loginService.updatePassword(enteredPassword,
                            userCurrentPass, strLoginName,
                            new HttpResponseListener<JSONObject>() {

                                @Override
                                public void getResponse(JSONObject response) {
                                    // TODO Auto-generated method stub

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            OtpActivity.this);
                                    alertDialogBuilder
                                            .setMessage("Password changed successfully");
                                    alertDialogBuilder
                                            .setPositiveButton(
                                                    "ok",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(
                                                                DialogInterface arg0,
                                                                int arg1) {

                                                            Intent intent = new Intent(
                                                                    OtpActivity.this,
                                                                    MainActivity.class);
                                                            startActivity(intent);

                                                        }

                                                    });

                                    AlertDialog alertDialog = alertDialogBuilder
                                            .create();
                                    alertDialog.show();

                                }
                            });

                }
            }
        });

    }

    // sumit keyboard
    private void closeKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
