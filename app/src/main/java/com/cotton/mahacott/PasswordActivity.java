package com.cotton.mahacott;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.LoginService;
import com.cotton.mahacott.util.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordActivity extends AppCompatActivity implements HttpResponseListener<JSONObject>, AdapterView.OnItemSelectedListener {

    EditText edtPassword;
    Button btnLogin;
    Button btnOk;
    String strUsername="",userPasword="",strZoneCode="",strCenterCode="";
    Bundle bundle;
    Spinner selectCenterName;
    TextView centerCode;
    Map<String,String> centerNameMap = new HashMap<>();
    List<String> centerNameList = new ArrayList<String>();
    String selectedCenterCode="",selectedCenterName="";
    TextView txtForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("Password");

        strUsername=SaveSharedPreference.getUserName(getApplicationContext());
        userPasword=SaveSharedPreference.getPassword(getApplicationContext());
        strZoneCode=SaveSharedPreference.getzonecode(getApplicationContext());
        strCenterCode=SaveSharedPreference.getcentercodelist(getApplicationContext());

        edtPassword = (EditText)findViewById(R.id.edtPassword);
        txtForgotPass = (TextView)findViewById(R.id.txtForgotPass);
        btnLogin =(Button)findViewById(R.id.btnLogin);
        btnOk =(Button)findViewById(R.id.btnOk);
        centerCode=(TextView)findViewById(R.id.txtCenterCode);
        selectCenterName = (Spinner)findViewById(R.id.selectCenterName);
        selectCenterName.setOnItemSelectedListener(this);
        btnOk.setVisibility(View.GONE);
        selectCenterName.setVisibility(View.GONE);
        centerCode.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                closeKeyBoard();

                if(edtPassword.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "Please Enter Password..", Toast.LENGTH_SHORT).show();
                }else{
                    if(edtPassword.getText().toString().equalsIgnoreCase(userPasword))
                    {
                        //password successfulll and call url gfor branch lisyt
                        btnLogin.setEnabled(false);
                        getVerify();


                    }
                    else

                        Toast.makeText(getApplicationContext(), "Please Enter correct Password..", Toast.LENGTH_SHORT).show();
                }
//				getVerify()
            }

            private void getVerify() {
                String centercodeserver=SaveSharedPreference.getcentercodelist(getApplicationContext());
                // TODO Auto-generated method stub
                LoginService loginService = new LoginService(getApplicationContext(), strUsername);
                loginService.verifyPassword(centercodeserver,new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        System.out.println(response);

                        try {
                            btnLogin.setEnabled(true);

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");
                            if(jsonArray.length()<1){
                                Toast.makeText(getApplicationContext(),"Password missmatch.", Toast.LENGTH_SHORT).show();
                            }else{
                                JSONObject jsonObj = new JSONObject();
                                JSONObject jsonObj1 = new JSONObject();
                                for(int i=0;i<jsonArray.length();i++){

                                    jsonObj = (JSONObject) jsonArray.get(i);
                                    jsonObj1 = (JSONObject) jsonArray.get(0);
                                    centerNameList.add(jsonObj.getString("CenterName"));
                                    centerNameMap.put(jsonObj.getString("CenterName"), jsonObj.getString("CenterCode"));
                                //    SaveSharedPreference.setPincode(getApplicationContext(),jsonObj1.getString("PinCode"));

                                }


                                ArrayAdapter<String> centerNameAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.custom_spinner_item, centerNameList);
                                selectCenterName.setAdapter(centerNameAdapter);
                                btnOk.setVisibility(View.VISIBLE);
                                selectCenterName.setVisibility(View.VISIBLE);
                                centerCode.setVisibility(View.VISIBLE);
                                edtPassword.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.GONE);
                                txtForgotPass.setVisibility(View.GONE);
                                txtForgotPass.setVisibility(View.INVISIBLE);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            btnLogin.setEnabled(true);
                        }
                    }
                });
            }

        });

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(selectedCenterCode.length()<1){
                    Toast.makeText(getApplicationContext(),"center name unavailable..", Toast.LENGTH_SHORT).show();
                }else{

                    insertUserLock();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }


        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Bundle bundle = new Bundle();
                Intent intent = new Intent(PasswordActivity.this,OtpActivity.class);
                startActivity(intent);
            }
        });




    }//end Create method

    private void insertUserLock() {
        // TODO Auto-generated method stub
        LoginService loginService = new LoginService(getApplicationContext(),strUsername);
        loginService.insertUserLockDet(SaveSharedPreference.getUserCode(getApplicationContext()), new HttpResponseListener<JSONObject>() {

            @Override
            public void getResponse(JSONObject response) {
                //successfully locked user login
            }
        });

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

    @Override
    public void getResponse(JSONObject response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub
        selectedCenterName = parent.getItemAtPosition(position).toString();
        selectedCenterCode = centerNameMap.get(selectedCenterName);
        System.out.println("CENTER NAME SELECTED==========="+selectedCenterName);
        System.out.println("CENTER CODE SELECTED==========="+selectedCenterCode);
        SaveSharedPreference.setBranchName(getApplicationContext(), selectedCenterName);
        SaveSharedPreference.setBranchCode(getApplicationContext(), selectedCenterCode);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }





    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
