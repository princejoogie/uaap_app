package com.example.uaap.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.uaap.Model.Delete;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.uaap.Model.LeagueDetails;
import com.example.uaap.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.Toast;

public class LeagueListAdapter extends BaseAdapter {
    private Context context;
    private Delete delete;
    private ArrayList<LeagueDetails> dataModelArrayList;
    private String deleteLeague1 = "http://68.183.49.18/uaap/public/deleteLeague1";
    private String deleteLeague2 = "http://68.183.49.18/uaap/public/deleteLeague2";

    public LeagueListAdapter(Context context, ArrayList<LeagueDetails> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }


    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.league_item, null, true);

            holder.btnDelLeague = convertView.findViewById(R.id.btnDelLeague);
            holder.txtLeagueName = convertView.findViewById(R.id.txtLeagueName);

            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtLeagueName.setText(dataModelArrayList.get(position).name);

        final String League = holder.txtLeagueName.getText().toString();

        holder.btnDelLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String id = dataModelArrayList.get(position).id;

                RequestQueue queue = Volley.newRequestQueue(context);

                StringRequest putRequest = new StringRequest(Request.Method.POST, deleteLeague1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();
                                delete = gson.fromJson(response, Delete.class);
                                String status = delete.status;
                                String message = delete.message;
                                if (status.equals("true")) {
                                    Toast.makeText(context, League + " Successfully deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
                                    alertbox.setMessage(message);
                                    alertbox.setTitle("Warning");

                                    alertbox.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    RequestQueue queue = Volley.newRequestQueue(context);

                                                    StringRequest putRequest = new StringRequest(Request.Method.POST, deleteLeague2,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
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
                                                            params.put("id", id);
                                                            return params;
                                                        }

                                                    };

                                                    queue.add(putRequest);
                                                }
                                            });
                                    alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    });
                                    alertbox.show();
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
                        params.put("id", id);
                        return params;
                    }

                };

                queue.add(putRequest);
            }
        });


        return convertView;


    }


    private class ViewHolder {
        public View VieAdd;
        protected TextView txtLeagueName;
        protected Button btnDelLeague;
    }
}
