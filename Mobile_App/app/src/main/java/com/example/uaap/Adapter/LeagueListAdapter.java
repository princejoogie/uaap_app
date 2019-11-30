package com.example.uaap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.Model.LeagueDetails;
import com.example.uaap.R;

import java.util.ArrayList;

public class LeagueListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LeagueDetails> dataModelArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.league_item, null, true);

            holder.btnDelLeague = convertView.findViewById(R.id.btnDelLeague);
            holder.txtLeagueName = convertView.findViewById(R.id.txtLeagueName);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtLeagueName.setText(dataModelArrayList.get(position).name);

        return convertView;
    }


    private class ViewHolder {

        protected TextView txtLeagueName;
        protected Button btnDelLeague;
    }

}
