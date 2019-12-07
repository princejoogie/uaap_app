package com.example.uaap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.RefereeSum;
import com.example.uaap.R;

import java.util.ArrayList;

public class RefSumListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RefereeSum> dataModelArrayList;

    public RefSumListAdapter(Context context, ArrayList<RefereeSum> dataModelArrayList) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.referee_sum_item, null, true);

            holder.txtOfficial = convertView.findViewById(R.id.txtOfficial);
            holder.txtCC = convertView.findViewById(R.id.txtCC);
            holder.txtIC = convertView.findViewById(R.id.txtIC);
            holder.txtCNC = convertView.findViewById(R.id.txtCNC);
            holder.txtINC = convertView.findViewById(R.id.txtINC);
            holder.txtCFR = convertView.findViewById(R.id.txtCFR);
            holder.txtNCFR = convertView.findViewById(R.id.txtNCFR);
            holder.txtTotal = convertView.findViewById(R.id.txtTotal);
            holder.txtCorrect = convertView.findViewById(R.id.txtCorrect);
            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
//        protected TextView period,time,callType,committingTeam,committing,disadvantagedTeam,disadvantaged,referee,area,reviewDecision,comment;

        holder.txtOfficial.setText(dataModelArrayList.get(position).getRefName());
        holder.txtCC.setText(dataModelArrayList.get(position).getCc());
        holder.txtIC.setText(dataModelArrayList.get(position).getIc());
        holder.txtCNC.setText(dataModelArrayList.get(position).getCnc());
        holder.txtINC.setText(dataModelArrayList.get(position).getInc());
        holder.txtCFR.setText(dataModelArrayList.get(position).getCfr());
        holder.txtNCFR.setText(dataModelArrayList.get(position).getNcfr());
        holder.txtTotal.setText(dataModelArrayList.get(position).getTotal());
        holder.txtCorrect.setText(dataModelArrayList.get(position).getCorrect());
        return convertView;
    }


    private class ViewHolder {

        protected TextView txtOfficial, txtCC, txtIC, txtCNC, txtINC, txtNCFR, txtCFR, txtTotal, txtCorrect;
    }

}
