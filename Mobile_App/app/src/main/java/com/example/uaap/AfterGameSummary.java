package com.example.uaap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Adapter.EvaluationListAdapter;
import com.example.uaap.Model.AfterGameFilter;
import com.example.uaap.Model.CurrentGame;
import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.EvaluationModel;
import com.example.uaap.Model.GetAll;
import com.google.gson.Gson;

import org.apache.commons.lang3.ArrayUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class AfterGameSummary extends AppCompatActivity {
    private CurrentGame currentGame;
    private String playing;
    private String getAll = "http://68.183.49.18/uaap/public/getAll";
    private GetAll getResult;
    private TextView txtLeagueName;
    private TextView txtAfterTeamA;
    private TextView txtAfterTeamB;
    private TextView txtDateToday;

    private Button btnFoulSelect;
    private Button btnVioSelect;
    private Button btnRefereeSelect;
    private Button btnReviewDecisionSelect;
    private Button btnCommittingSelect;
    private Button btnDisadvantagedSelect;
    private Button btnAreaSelect;
    private Button btnAopSelect;
    private Button btnAfterFilter;
    private Button btnAfterRefSum;

    private AfterGameFilter filter;
    private String[] foul;
    private String[] vio;
    private boolean[] checkedFoul;
    private boolean[] checkedVio;
    private String[] refName;
    private String[] refId;
    private boolean[] checkedRef;
    private String[] rdItems;
    private boolean[] checkedRd;
    private String[] areaIteams;
    private boolean[] checkedArea;
    private boolean[] checkedAop;

    private String[] teamId;
    private String[] teamName;
    private boolean[] checkedComm;
    private boolean[] checkedDis;
    private EvaluationListAdapter listAdapter;
    private EvaluationModel calls;
    private String GetCallURL = "http://68.183.49.18/uaap/public/getAll";
    private String FilterEvalURL = "http://68.183.49.18/uaap/public/filterEval";

    private ListView evaluationList;

    private Button btnExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game_summary);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            playing = extras.getString("playing");

        }
        Gson gson = new Gson();
        currentGame = gson.fromJson(playing, CurrentGame.class);

        txtLeagueName = findViewById(R.id.txtLeagueName);
        txtAfterTeamA = findViewById(R.id.txtAfterTeamA);
        txtAfterTeamB = findViewById(R.id.txtAfterTeamB);
        txtDateToday = findViewById(R.id.txtDateToday);
        btnRefereeSelect = findViewById(R.id.btnRefereeSelect);
        btnReviewDecisionSelect = findViewById(R.id.btnReviewDecisionSelect);
        btnCommittingSelect = findViewById(R.id.btnCommittingSelect);
        btnDisadvantagedSelect = findViewById(R.id.btnDisadvantagedSelect);
        evaluationList = findViewById(R.id.evaluationList);
        btnAfterFilter = findViewById(R.id.btnAfterFilter);
        btnAfterRefSum = findViewById(R.id.btnAfterRefSum);
        btnFoulSelect = findViewById(R.id.btnFoulSelect);
        btnVioSelect = findViewById(R.id.btnVioSelect);
        btnAreaSelect = findViewById(R.id.btnAreaSelect);
        btnAopSelect = findViewById(R.id.btnAoPSelect);
        btnExcel = findViewById(R.id.btnExcel);
        txtLeagueName.setText(currentGame.getLeagueName());
        txtAfterTeamA.setText(currentGame.getTeamA());
        txtAfterTeamB.setText(currentGame.getTeamB());
        txtDateToday.setText(currentGame.getDate());
        filter = new AfterGameFilter();

        //
        getCalls();
        btnVioSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("vio");
            }
        });
        btnFoulSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("foul");
            }
        });
        btnRefereeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("ref");
            }
        });
        btnReviewDecisionSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("rd");
            }
        });
        btnCommittingSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("comm");
            }
        });
        btnDisadvantagedSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("dis");
            }
        });
        btnAreaSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("area");
            }
        });
        btnAopSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter("aop");
            }
        });
        btnAfterFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterEval();
            }
        });
        btnAfterRefSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterGameSummary.this, AfterRefereeSummary.class);
                intent.putExtra("playing", playing);
                startActivity(intent);
            }
        });
        btnExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExcelSheet(getApplicationContext());
            }
        });
    }

    private void getCalls() {
        filter.setReferees("");
        filter.setCalls("");
        filter.setVio("");
        filter.setFoul("");
        filter.setComm("");
        filter.setDis("");
        filter.setRds("");
        filter.setArea("");
        filter.setAop("");
        foul = getResources().getStringArray(R.array.foul);
        vio = getResources().getStringArray(R.array.violation);
        checkedFoul = new boolean[foul.length];
        checkedVio = new boolean[vio.length];

        for (int i = 0; i < checkedFoul.length; i++) {
            checkedFoul[i] = true;
        }
        for (int i = 0; i < checkedVio.length; i++) {
            checkedVio[i] = true;
        }
        refId = new String[currentGame.referee.size()];
        refName = new String[currentGame.referee.size()];
        for (int i = 0; i < currentGame.referee.size(); i++) {
            refId[i] = currentGame.referee.get(i).id;
            refName[i] = currentGame.referee.get(i).name;
        }
        checkedRef = new boolean[3];
        for (int i = 0; i < 3; i++) {
            checkedRef[i] = true;
        }

        rdItems = new String[6];
        rdItems[0] = "CC";
        rdItems[1] = "IC";
        rdItems[2] = "CFR";
        rdItems[3] = "NCFR";
        rdItems[4] = "CNC";
        rdItems[5] = "INC";
        checkedRd = new boolean[6];
        for (int i = 0; i < 6; i++) {
            checkedRd[i] = true;
        }
        teamId = new String[2];
        teamId[0] = currentGame.getTeamAId();
        teamId[1] = currentGame.getTeamBId();
        teamName = new String[2];
        teamName[0] = currentGame.getTeamA();
        teamName[1] = currentGame.getTeamB();
        checkedComm = new boolean[2];
        checkedDis = new boolean[2];
        for (int i = 0; i < 2; i++) {
            checkedComm[i] = true;
            checkedDis[i] = true;
        }
        areaIteams = new String[3];
        areaIteams[0] = "Lead";
        areaIteams[1] = "Center";
        areaIteams[2] = "Trail";
        checkedArea = new boolean[3];
        checkedAop = new boolean[3];
        for (int i = 0; i < 3; i++) {
            checkedAop[i] = true;
            checkedArea[i] = true;
        }
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest putRequest = new StringRequest(Request.Method.POST, GetCallURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        Gson gson = new Gson();
                        calls = gson.fromJson(response, EvaluationModel.class);
                        ArrayList<EvaluationDetails> dataModelArrayList = calls.result;
                        if (!dataModelArrayList.isEmpty()) {
                            listAdapter = new EvaluationListAdapter(getApplicationContext(), dataModelArrayList);
                            evaluationList.setAdapter(listAdapter);
                            evaluationList.setSelection(evaluationList.getAdapter().getCount() - 1);

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
                params.put("gameId", currentGame.getGameId());
                return params;
            }

        };

        queue.add(putRequest);
    }


    private void setFilter(String type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AfterGameSummary.this);
        builder.setTitle("Choose");

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (type.equals("foul")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedFoul.length; i++) {
                        checkedFoul[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedFoul.length; i++) {
                        checkedFoul[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    filter.setFoul("");
                    for (int i = 0; i < foul.length; i++) {
                        if (checkedFoul[i]) {

                            if (filter.getFoul().length() > 0) {
                                filter.setFoul(filter.getFoul() + ',');
                            }
                            filter.setFoul(filter.getFoul() + foul[i]);
                            count++;

                        }

                    }
                    if (count == foul.length) {
                        btnFoulSelect.setText("ALL");
                    } else if (count == 0) {
                        btnFoulSelect.setText("Select");
                    } else {
                        if (filter.getFoul().length() > 6) {
                            btnFoulSelect.setText(filter.getFoul().substring(0, 6) + "..");
                        } else {
                            btnFoulSelect.setText(filter.getFoul());
                        }
                    }
                    Log.e("item", filter.getCalls());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(foul, checkedFoul, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("vio")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedVio.length; i++) {
                        checkedVio[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedVio.length; i++) {
                        checkedVio[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    filter.setVio("");
                    for (int i = 0; i < vio.length; i++) {
                        if (checkedVio[i]) {

                            if (filter.getVio().length() > 0) {
                                filter.setVio(filter.getVio() + ',');
                            }
                            filter.setVio(filter.getVio() + vio[i]);
                            count++;

                        }

                    }
                    if (count == vio.length) {
                        btnVioSelect.setText("ALL");
                    } else if (count == 0) {
                        btnVioSelect.setText("Select");
                    } else {
                        if (filter.getVio().length() > 6) {
                            btnVioSelect.setText(filter.getVio().substring(0, 6) + "..");
                        } else {
                            btnVioSelect.setText(filter.getVio());
                        }
                    }
                    Log.e("item", filter.getCalls());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(vio, checkedVio, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("ref")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedRef.length; i++) {
                        checkedRef[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedRef.length; i++) {
                        checkedRef[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    String refNames = "";
                    filter.setReferees("");
                    for (int i = 0; i < refId.length; i++) {
                        if (checkedRef[i]) {
                            if (filter.getReferees().length() > 0) {
                                filter.setReferees(filter.getReferees() + ',');
                                refNames = refNames + ',';
                            }
                            filter.setReferees(filter.getReferees() + refId[i]);
                            refNames = refNames + refName[i];
                            count++;

                        }

                    }
                    if (count == refId.length) {
                        btnRefereeSelect.setText("ALL");
                    } else if (count == 0) {
                        btnRefereeSelect.setText("Select");
                    } else {
                        if (refNames.length() > 6) {
                            btnRefereeSelect.setText(refNames.substring(0, 6) + "..");
                        } else {
                            btnRefereeSelect.setText(refNames);
                        }
                    }
                    Log.e("item", filter.getReferees());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(refName, checkedRef, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("rd")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedRd.length; i++) {
                        checkedRd[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedRd.length; i++) {
                        checkedRd[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    filter.setRds("");
                    for (int i = 0; i < rdItems.length; i++) {
                        if (checkedRd[i]) {
                            if (filter.getRds().length() > 0) {
                                filter.setRds(filter.getRds() + ',');
                            }
                            filter.setRds(filter.getRds() + rdItems[i]);
                            count++;

                        }

                    }
                    if (count == rdItems.length) {
                        btnReviewDecisionSelect.setText("ALL");
                    } else if (count == 0) {
                        btnReviewDecisionSelect.setText("Select");
                    } else {
                        if (filter.getRds().length() > 6) {
                            btnRefereeSelect.setText(filter.getRds().substring(0, 6) + "..");
                        } else {
                            btnRefereeSelect.setText(filter.getRds());
                        }
                    }
                    Log.e("item", filter.getRds());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(rdItems, checkedRd, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("area")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedArea.length; i++) {
                        checkedArea[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedArea.length; i++) {
                        checkedArea[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    filter.setArea("");
                    for (int i = 0; i < areaIteams.length; i++) {
                        if (checkedArea[i]) {
                            if (filter.getArea().length() > 0) {
                                filter.setArea(filter.getArea() + ',');
                            }
                            filter.setArea(filter.getArea() + areaIteams[i]);
                            count++;

                        }

                    }
                    if (count == areaIteams.length) {
                        btnAreaSelect.setText("ALL");
                    } else if (count == 0) {
                        btnAreaSelect.setText("Select");
                    } else {
                        if (filter.getArea().length() > 6) {
                            btnAreaSelect.setText(filter.getArea().substring(0, 6) + "..");
                        } else {
                            btnAreaSelect.setText(filter.getArea());
                        }
                    }
                    Log.e("item", filter.getArea());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(areaIteams, checkedArea, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("aop")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedAop.length; i++) {
                        checkedAop[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedAop.length; i++) {
                        checkedAop[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    filter.setAop("");
                    for (int i = 0; i < areaIteams.length; i++) {
                        if (checkedAop[i]) {
                            if (filter.getAop().length() > 0) {
                                filter.setAop(filter.getAop() + ',');
                            }
                            filter.setAop(filter.getAop() + areaIteams[i]);
                            count++;

                        }

                    }
                    if (count == areaIteams.length) {
                        btnAopSelect.setText("ALL");
                    } else if (count == 0) {
                        btnAopSelect.setText("Select");
                    } else {
                        if (filter.getAop().length() > 6) {
                            btnAopSelect.setText(filter.getAop().substring(0, 6) + "..");
                        } else {
                            btnAopSelect.setText(filter.getAop());
                        }
                    }
                    Log.e("item", filter.getAop());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(areaIteams, checkedAop, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("comm")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedComm.length; i++) {
                        checkedComm[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedComm.length; i++) {
                        checkedComm[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    filter.setComm("");
                    int count = 0;
                    String commNames = "";
                    for (int i = 0; i < teamId.length; i++) {
                        if (checkedComm[i]) {
                            if (filter.getComm().length() > 0) {
                                filter.setComm(filter.getComm() + ',');
                                commNames = commNames + ',';
                            }
                            filter.setComm(filter.getComm() + teamId[i]);
                            commNames = commNames + teamName[i];
                            count++;

                        }

                    }
                    if (count == teamId.length) {
                        btnCommittingSelect.setText("ALL");
                    } else if (count == 0) {
                        btnCommittingSelect.setText("Select");
                    } else {
                        if (commNames.length() > 6) {
                            btnCommittingSelect.setText(commNames.substring(0, 6) + "..");
                        } else {
                            btnCommittingSelect.setText(commNames);
                        }
                    }
                    Log.e("item", filter.getComm());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(teamName, checkedComm, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (type.equals("dis")) {

            builder.setNegativeButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedDis.length; i++) {
                        checkedDis[i] = true;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedDis.length; i++) {
                        checkedDis[i] = false;
                    }
                    AlertDialog dia = builder.create();
                    dia.show();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int count = 0;
                    String disNames = "";
                    filter.setDis("");
                    for (int i = 0; i < teamId.length; i++) {
                        if (checkedDis[i]) {
                            if (filter.getDis().length() > 0) {
                                filter.setDis(filter.getDis() + ',');
                                disNames = disNames + ',';
                            }
                            filter.setDis(filter.getDis() + teamId[i]);
                            disNames = disNames + teamName[i];
                            count++;
                        }

                    }
                    if (count == teamId.length) {
                        btnDisadvantagedSelect.setText("ALL");
                    } else if (count == 0) {
                        btnDisadvantagedSelect.setText("Select");
                    } else {
                        if (disNames.length() > 6) {
                            btnRefereeSelect.setText(disNames.substring(0, 6) + "..");
                        } else {
                            btnRefereeSelect.setText(disNames);
                        }
                    }
                    Log.e("item", filter.getDis());
                }
            });
            Log.e("type", "call");
            builder.setMultiChoiceItems(teamName, checkedDis, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void filterEval() {
        if (filter.getVio().equals("") || filter.getVio().isEmpty()) {
            filter.setCalls(filter.getFoul());
        } else if (filter.getFoul().equals("") || filter.getFoul().isEmpty()) {
            filter.setCalls(filter.getVio());
        } else if (filter.getFoul().length() > 0 && filter.getVio().length() > 0) {
            filter.setCalls(filter.getFoul() + "," + filter.getVio());
        } else {
            filter.setCalls("");
        }
        if (filter.getComm().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select committing team/s.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getRds().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select review decision/s.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getCalls().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select call/s.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getReferees().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select referee/s.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getArea().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select area/s.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getAop().length() == 0) {
            Toast.makeText(AfterGameSummary.this, "Select area/s of play.", Toast.LENGTH_SHORT).show();
        }
        if (filter.getComm().length() != 0 &&
                filter.getRds().length() != 0 && filter.getCalls().length() != 0 &&
                filter.getReferees().length() != 0 &&
                filter.getAop().length() != 0 &&
                filter.getArea().length() != 0) {
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest putRequest = new StringRequest(Request.Method.POST, FilterEvalURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response", response);
                            Gson gson = new Gson();
                            calls = gson.fromJson(response, EvaluationModel.class);
                            ArrayList<EvaluationDetails> dataModelArrayList = calls.result;
                            if (!dataModelArrayList.isEmpty()) {
                                evaluationList.setVisibility(View.VISIBLE);
                                listAdapter = new EvaluationListAdapter(getApplicationContext(), dataModelArrayList);
                                evaluationList.setAdapter(listAdapter);
                                evaluationList.setSelection(evaluationList.getAdapter().getCount() - 1);

                            } else {
                                Toast.makeText(getApplicationContext(), "No Result!", Toast.LENGTH_LONG).show();
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
                    params.put("gameId", currentGame.getGameId());
                    params.put("committingTeam", filter.getComm());

                    params.put("calls", filter.getCalls());
                    params.put("referees", filter.getReferees());
                    params.put("rd", filter.getRds());
                    params.put("area", filter.getArea());
                    params.put("aop", filter.getAop());
                    if (filter.getDis().length() != 0) {
                        params.put("disadvantaged", filter.getDis());
                    }
                    return params;
                }

            };

            queue.add(putRequest);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AfterGameSummary.this, Evaluation.class);
        Gson gson = new Gson();
        String json = gson.toJson(currentGame);
        intent.putExtra("playing", json);
        startActivity(intent);

    }

    private void createExcelSheet(final Context context) {
        final String gameid = currentGame.getGameId();
        final String TeamA = currentGame.getTeamA();
        final String TeamB = currentGame.getTeamB();
        final String refA = currentGame.referee.get(0).name;
        final String refB = currentGame.referee.get(1).name;
        final String refC = currentGame.referee.get(2).name;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest putRequest = new StringRequest(Request.Method.POST, getAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        getResult = gson.fromJson(response, GetAll.class);
                        String status = getResult.status;
                        String Fnamexls = "Uaap.xls";
                        File sdCard = Environment.getDataDirectory();

                        //   File directory = new File ( context.getFilesDir().getAbsolutePath()+ "/UAAP");
                        File directory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)));
                        //directory.mkdirs();
                        File file = new File(directory, Fnamexls);
                        int Size = getResult.result.size();
                        int i = 0;
                        WorkbookSettings wbSettings = new WorkbookSettings();

                        wbSettings.setLocale(new Locale("en", "EN"));

                        WritableWorkbook workbook;

                        WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
                        try {
                            cellFont.setBoldStyle(WritableFont.BOLD);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }

                        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
                        try {
                            cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.DOUBLE);
                            cellFormat.setAlignment(Alignment.CENTRE);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
// Period
                        WritableFont periodCell = new WritableFont(WritableFont.ARIAL, 10);
                        try {
                            periodCell.setBoldStyle(WritableFont.BOLD);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }

                        WritableCellFormat cellPeriod = new WritableCellFormat(periodCell);
                        try {
                            cellPeriod.setAlignment(Alignment.CENTRE);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }

//Center

                        WritableFont center = new WritableFont(WritableFont.ARIAL, 10);
                        WritableCellFormat centerCell = new WritableCellFormat(center);
                        try {
                            centerCell.setAlignment(Alignment.CENTRE);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
// Header
                        WritableFont header = new WritableFont(WritableFont.ARIAL, 14);
                        try {
                            header.setBoldStyle(WritableFont.BOLD);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
                        WritableCellFormat headerCell = new WritableCellFormat(header);
                        try {
                            cellPeriod.setAlignment(Alignment.CENTRE);
                        } catch (WriteException e) {
                            e.printStackTrace();
                        }
                        try {

                            workbook = Workbook.createWorkbook(file, wbSettings);
                            WritableSheet sheet = workbook.createSheet("Reports", 0);

                            for (int x = 0; x < getResult.result.size(); x++) {
                                String Id = Integer.toString(getResult.result.get(x).id);
                                String time = getResult.result.get(x).time;
                                int period = getResult.result.get(x).period;
                                String finalPeriod;
                                if (period >= 4) {
                                    finalPeriod = "OT";
                                } else {
                                    finalPeriod = "Q" + Integer.toString(period + 1);
                                }
                                String disadvantage = getResult.result.get(x).disadvantaged;
                                String callType = getResult.result.get(x).callType;
                                String committing = getResult.result.get(x).committing;
                                String referee = getResult.result.get(x).referee;
                                String area = getResult.result.get(x).area;
                                String areaOfPlay = getResult.result.get(x).areaOfPlay;
                                String reviewDecision = getResult.result.get(x).reviewDecision;
                                String comment = getResult.result.get(x).comment;
                                String scores = getResult.result.get(x).scores;
                                Label col6 = new Label(0, 6, "Period", cellFormat);
                                Label col7 = new Label(1, 6, "Time", cellFormat);
                                Label col8 = new Label(2, 6, "Call Type", cellFormat);
                                Label col9 = new Label(3, 6, "Committing Player", cellFormat);
                                Label col10 = new Label(4, 6, "Disadvantage Player", cellFormat);
                                Label game = new Label(2, 2, TeamA + " VS " + TeamB, headerCell);
                                Label refA1 = new Label(8, 2, refA);
                                Label refB1 = new Label(8, 3, refB);
                                Label refC1 = new Label(8, 4, refC);
                                Label col11 = new Label(5, 6, "Area Of Play", cellFormat);
                                Label col12 = new Label(6, 6, "Referee", cellFormat);
                                Label col13 = new Label(7, 6, "Area", cellFormat);
                                Label col14 = new Label(8, 6, "Review Decision", cellFormat);
                                Label col15 = new Label(9, 6, "Comments", cellFormat);
                                Label col16 = new Label(10, 6, "Score", cellFormat);
                                // Rows
                                Label row6 = new Label(0, x + 7, finalPeriod, cellPeriod);
                                Label row7 = new Label(1, x + 7, time, centerCell);
                                Label row8 = new Label(2, x + 7, callType);
                                Label row9 = new Label(3, x + 7, committing);
                                Label row10 = new Label(4, x + 7, disadvantage);
                                Label row11 = new Label(5, x + 7, areaOfPlay, centerCell);
                                Label row12 = new Label(6, x + 7, referee);
                                Label row13 = new Label(7, x + 7, area, centerCell);
                                Label row14 = new Label(8, x + 7, reviewDecision, centerCell);
                                Label row15 = new Label(9, x + 7, comment);
                                Label row16 = new Label(10, x + 7, scores, centerCell);

                                Log.e("HGELLO", Id);
                                try {
                                    sheet.addCell(col6);
                                    sheet.addCell(game);
                                    sheet.addCell(col7);
                                    sheet.addCell(col8);
                                    sheet.addCell(col9);
                                    sheet.addCell(col10);
                                    sheet.addCell(col11);
                                    sheet.addCell(col12);
                                    sheet.addCell(col13);
                                    sheet.addCell(col14);
                                    sheet.addCell(col15);
                                    sheet.addCell(col16);
                                    //add row
                                    sheet.addCell(row6);
                                    sheet.addCell(row7);
                                    sheet.addCell(row8);
                                    sheet.addCell(row9);
                                    sheet.addCell(row10);
                                    sheet.addCell(row11);
                                    sheet.addCell(row12);
                                    sheet.addCell(row13);
                                    sheet.addCell(row14);
                                    sheet.addCell(row15);
                                    sheet.addCell(row16);
                                    sheet.addCell(refA1);
                                    sheet.addCell(refB1);
                                    sheet.addCell(refC1);


                                } catch (RowsExceededException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (WriteException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }

                            workbook.write();

                            Toast.makeText(getApplicationContext(), "Excel Sucessfully Created.", Toast.LENGTH_SHORT).show();

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
                params.put("gameId", gameid);
                return params;
            }

        };

        queue.add(putRequest);

    }

}
