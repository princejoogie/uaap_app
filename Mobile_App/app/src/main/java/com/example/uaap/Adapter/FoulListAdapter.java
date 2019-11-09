package com.example.uaap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.Foul;
import com.example.uaap.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FoulListAdapter extends BaseAdapter {
    private Context context;
    private List<String> dataModelArrayList;


    public FoulListAdapter(Context context, List<String> dataModelArrayList) {

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
    public String getItem(int position) {
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
            convertView = inflater.inflate(R.layout.foul_item, null, true);
            holder.txtFoul = convertView.findViewById(R.id.txtFoul);


            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
        }
//        protected TextView period,time,callType,committingTeam,committing,disadvantagedTeam,disadvantaged,referee,area,reviewDecision,comment;
        holder.txtFoul.setText(dataModelArrayList.get(position).toUpperCase());

        return convertView;
    }

    private class ViewHolder {
        protected TextView txtFoul;
    }

}
