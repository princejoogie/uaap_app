package com.example.uaap.Model;

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
import com.example.uaap.R;
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
    Button Create;
    private String getAll = "http://68.183.49.18/uaap/public/getAll";
    private GetAll getResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);



        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createExcelSheet(getApplicationContext());

            }
        });


    }
    private void createExcelSheet(Context context)
    {
        String Fnamexls="Uaap.xls";
        File sdCard = Environment.getRootDirectory();
        File directory = new File ( sdCard.getAbsolutePath()+ "/UAAP");
        directory.mkdirs();
        File file = new File(directory, Fnamexls);

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;
        try {
            int a = 1;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //workbook.createSheet("Report", 0);
            WritableSheet sheet = workbook.createSheet("Reports", 0);
            Label col1 = new Label(0,0,"ID");
            Label col2 = new Label(1,0,"TIME");
            Label col3 = new Label(1,0,"PERIOD");
            try {
                sheet.addCell(col1);
                sheet.addCell(col2);
                sheet.addCell(col3);
            } catch (RowsExceededException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

    public void getAll(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest putRequest = new StringRequest(Request.Method.POST, getAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        getResult = gson.fromJson(response, GetAll.class);
                        String status = getResult.status;
                      // Get Result Here 
                        if (status.equals("true")) {
                            Toast.makeText(context, " Successfully deleted.", Toast.LENGTH_SHORT).show();
                        } else {

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
                params.put("id", "34");
                return params;
            }

        };

        queue.add(putRequest);
    }
}
