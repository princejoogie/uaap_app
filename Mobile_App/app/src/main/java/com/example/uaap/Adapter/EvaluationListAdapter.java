package com.example.uaap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.uaap.Model.EvaluationDetails;
import com.example.uaap.R;

import java.util.ArrayList;

public class EvaluationListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EvaluationDetails> dataModelArrayList;

    public EvaluationListAdapter(Context context, ArrayList<EvaluationDetails> dataModelArrayList) {

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
            convertView = inflater.inflate(R.layout.evaluation_item, null, true);

            holder.period = convertView.findViewById(R.id.period);
            holder.time = convertView.findViewById(R.id.time);
            holder.callType = convertView.findViewById(R.id.callType);
            holder.committing = convertView.findViewById(R.id.committing);
            holder.disadvantaged = convertView.findViewById(R.id.disadvantaged);
            holder.referee = convertView.findViewById(R.id.referee);
            holder.area = convertView.findViewById(R.id.area);
            holder.reviewDecision = convertView.findViewById(R.id.reviewDecision);
            holder.comment = convertView.findViewById(R.id.comment);
            holder.areaOfPlay = convertView.findViewById(R.id.areaOfPlay);
            holder.scores = convertView.findViewById(R.id.scores);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
//        protected TextView period,time,callType,committingTeam,committing,disadvantagedTeam,disadvantaged,referee,area,reviewDecision,comment;

        holder.time.setText(dataModelArrayList.get(position).getTime());
        holder.callType.setText(dataModelArrayList.get(position).getCallType());
        holder.committing.setText(dataModelArrayList.get(position).getCommitting());
        holder.disadvantaged.setText(dataModelArrayList.get(position).getDisadvantaged());
        holder.referee.setText(dataModelArrayList.get(position).getReferee());
        holder.area.setText(dataModelArrayList.get(position).getArea());
        holder.reviewDecision.setText(dataModelArrayList.get(position).getReviewDecision());
        if(isEmpty(dataModelArrayList.get(position).getComment())){
            holder.comment.setVisibility(View.GONE);

        }else{
            holder.comment.setText("Comment: "+dataModelArrayList.get(position).getComment());
        }
        holder.comment.setText(dataModelArrayList.get(position).getComment());
        holder.areaOfPlay.setText(dataModelArrayList.get(position).getAreaOfPlay());
        if(dataModelArrayList.get(position).getPeriod()<4){
            holder.period.setText((dataModelArrayList.get(position).getPeriod()+1)+"Q");
        }
        else{
            holder.period.setText("OT");
        }
        holder.scores.setText(dataModelArrayList.get(position).getScores());
        return convertView;
    }

    private boolean isEmpty(String str) {
        if(str != null && !str.trim().isEmpty())
            return false;
        return true;
    }

    private class ViewHolder {

        protected TextView period,time,callType,committing,disadvantaged,referee,area,reviewDecision,comment,areaOfPlay, scores;
    }

}
