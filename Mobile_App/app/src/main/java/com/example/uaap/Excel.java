package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Model.GetAll;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Excel extends AppCompatActivity {
    Button createExcel;
    private String getAll = "http://68.183.49.18/uaap/public/getAll";
    private GetAll getResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
        createExcel = (Button)findViewById(R.id.btnExcel);

        createExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAll(getApplicationContext());
            }
        });

    }

    public void getAll(final Context context){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest putRequest = new StringRequest(Request.Method.POST, getAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        getResult = gson.fromJson(response, GetAll.class);
                        String status = getResult.status;
                        String Fnamexls="Uaap.xls";
                        File sdCard = Environment.getDataDirectory();
                        File directory = new File ( context.getFilesDir().getAbsolutePath()+ "/UAAP");
                        directory.mkdirs();
                        File file = new File(directory, Fnamexls);
                        int Size = getResult.result.size();
                        int i = 0;
                        WorkbookSettings wbSettings = new WorkbookSettings();

                        wbSettings.setLocale(new Locale("en", "EN"));

                        WritableWorkbook workbook;

                        try {

                            workbook = Workbook.createWorkbook(file, wbSettings);
                            WritableSheet sheet = workbook.createSheet("Reports", 0);

                            for(int x=0; x<getResult.result.size();x++){
                                String Id = Integer.toString(getResult.result.get(x).id);
                                String time = getResult.result.get(x).time;
                                int period = getResult.result.get(x).period;
                                String finalPeriod;
                                if(period >=4){
                                    finalPeriod = "OT";
                                }else{
                                    finalPeriod = "Q" + Integer.toString(period + 1);
                                }
                                String callType = getResult.result.get(x).callType;
                                String committing = getResult.result.get(x).committing;
                                String referee = getResult.result.get(x).referee;
                                String area = getResult.result.get(x).area;
                                String areaOfPlay = getResult.result.get(x).areaOfPlay;
                                String reviewDecision = getResult.result.get(x).reviewDecision;
                                String comment = getResult.result.get(x).comment;
                                String scores = getResult.result.get(x).scores;
                                Label col1 = new Label(0, 0, "ID");
                                Label col2 = new Label(1, 0, "TIME");
                                Label col3 = new Label(2, 0, "PERIOD");
                                Label col4 = new Label(3, 0, "CALL TYPE");
                                Label col5 = new Label(4, 0, "COMMITTING");
                                Label col6 = new Label(5, 0, "REFEREE");
                                Label col7 = new Label(6, 0, "AREA");
                                Label col8 = new Label(7, 0, "AREA OF PLAY");
                                Label col9 = new Label(8, 0, "REVIEW DECISION");
                                Label col10 = new Label(9, 0, "COMMENT");
                                Label col11 = new Label(10, 0, "SCORES");
                                Label row1 = new Label(0, x+1, Id);
                                Label row2 = new Label(1, x+1, time);
                                Label row3 = new Label(2, x+1, finalPeriod);
                                Label row4 = new Label(3, x+1, callType);
                                Label row5 = new Label(4, x+1, committing);
                                Label row6 = new Label(5, x+1, referee);
                                Label row7 = new Label(6, x+1, area);
                                Label row8 = new Label(7, x+1, areaOfPlay);
                                Label row9 = new Label(8, x+1, reviewDecision);
                                Label row10 = new Label(9, x+1, comment);
                                Label row11 = new Label(10, x+1, scores);
                                Log.e("HGELLO", Id);
                                try {
                                    sheet.addCell(col1);
                                    sheet.addCell(col2);
                                    sheet.addCell(col3);
                                    sheet.addCell(col4);
                                    sheet.addCell(col5);
                                    sheet.addCell(col6);
                                    sheet.addCell(col7);
                                    sheet.addCell(col8);
                                    sheet.addCell(col9);
                                    sheet.addCell(col10);
                                    sheet.addCell(col11);
                                    sheet.addCell(row1);
                                    sheet.addCell(row2);
                                    sheet.addCell(row3);
                                    sheet.addCell(row4);
                                    sheet.addCell(row5);
                                    sheet.addCell(row6);
                                    sheet.addCell(row7);
                                    sheet.addCell(row8);
                                    sheet.addCell(row9);
                                    sheet.addCell(row10);
                                    sheet.addCell(row11);
                                } catch (RowsExceededException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (WriteException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            workbook.write();
                            try {
                                workbook.close();
                            } catch (WriteException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            //createExcel(excelSheet);


                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("gameId", "34");
                return params;
            }

        };

        queue.add(putRequest);
    }
}
