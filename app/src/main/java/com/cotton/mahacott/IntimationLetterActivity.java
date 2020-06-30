package com.cotton.mahacott;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cotton.mahacott.interfaces.HttpResponseListener;
import com.cotton.mahacott.service.SeedStockEntryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntimationLetterActivity extends AppCompatActivity {

    Spinner spinVariety,spinGrade;
    Button btnNext;
    String strVariety="",strGrade="";

    List<String> gradeTypeList = new ArrayList<>();
    Map<String, String> gradeTypeMap = new HashMap<>();
    List<String> varietyTypeList = new ArrayList<>();
    Map<String, String> varietyTypeMap = new HashMap<>();
    private String pattern;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intimation_letter);
        spinVariety=(Spinner) findViewById(R.id.selectVariety);
        spinGrade=(Spinner)findViewById(R.id.selectGrade);
        btnNext=(Button)findViewById(R.id.btnNext);
        pattern = "dd/MM/yyyy";
        final String dateInString =new SimpleDateFormat(pattern).format(new Date());


        SeedStockEntryService seedStockEntryService = new SeedStockEntryService(
                this);

        seedStockEntryService
                .getVerietyHelp(new HttpResponseListener<JSONObject>() {

                    @Override
                    public void getResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");
                            if (jsonArray.length() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No veriety available.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject jsonObj = new JSONObject();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObj = (JSONObject) jsonArray.get(i);
                                    varietyTypeMap.put(
                                            jsonObj.getString("VARIETY"),
                                            jsonObj.getString("VARIETYKEY"));
                                    varietyTypeList.add(jsonObj
                                            .getString("VARIETY"));
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> varietyTypeAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.custom_spinner_item, varietyTypeList);
                        spinVariety.setAdapter(varietyTypeAdapter);

                    }
                });



        seedStockEntryService
                .getGradeHelp(new HttpResponseListener<JSONObject>() {
                    @Override
                    public void getResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray = response.getJSONArray("Information");

                            if (jsonArray.length() < 1) {
                                Toast.makeText(getApplicationContext(),
                                        "No grade available.",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                JSONObject jsonObj = new JSONObject();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObj = (JSONObject) jsonArray.get(i);
                                    // GARDE KEY YET TO BE DISSCUSSED
                                    gradeTypeMap.put(
                                            jsonObj.getString("GRADE"),
                                            jsonObj.getString("GRADEKEY"));
                                    gradeTypeList.add(jsonObj
                                            .getString("GRADE"));
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> gradeTypeAdapter = new ArrayAdapter<>(
                                getApplicationContext(),
                                R.layout.custom_spinner_item, gradeTypeList);
                        spinGrade.setAdapter(gradeTypeAdapter);

                    }
                });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                strVariety = spinVariety.getSelectedItem().toString();
                strGrade = spinGrade.getSelectedItem().toString();
                String commoMstId = "2";
                String varietyMstID = varietyTypeMap
                        .get(strVariety);
                String gradeId = gradeTypeMap.get(strGrade);
                String seasonMstId = "2";

                Intent intent = new Intent(getApplicationContext(), IntimationDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("COMMO", strVariety);
                bundle.putString("GRADE", strGrade);
                bundle.putString("CURRENT_DATE", dateInString);
                bundle.putString("COMMO_MST_ID", commoMstId);
                bundle.putString("VAR_MST_ID", varietyMstID);
                bundle.putString("GRADE_ID", gradeId);
                bundle.putString("SEASON_MST_ID", seasonMstId);

                System.out.println("IN HOME ,DATE====="+dateInString);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
