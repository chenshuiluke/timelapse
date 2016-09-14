package com.lukechenshui.timelapse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by luke on 9/14/16.
 */
public class ActionDetailsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Action> dataSource;

    public ActionDetailsAdapter(Context context, ArrayList<Action> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Action getItem(int position) {

        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = inflater.inflate(R.layout.action_details_list_layout, parent, false);
        TextView actionStartDate = (TextView) newView.findViewById(R.id.startTimeTextView);
        TextView durationTextView = (TextView) newView.findViewById(R.id.durationTextView);
        Action action = getItem(position);
        System.out.println("Start date:" + action.getFormattedStartDate());
        actionStartDate.setText(action.getFormattedStartDate());
        durationTextView.setText(action.getFormattedDifference());
        return newView;
    }


}
