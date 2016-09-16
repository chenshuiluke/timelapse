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
public class ActionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Action> dataSource;

    public ActionAdapter(Context context, ArrayList<Action> dataSource) {
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
        View newView = inflater.inflate(R.layout.action_list_layout, parent, false);
        TextView actionNameTextView = (TextView) newView.findViewById(R.id.actionNameTextView);
        System.out.println("Getting: " + getItem(position).getName());
        actionNameTextView.setText(getItem(position).getName());
        return newView;
    }

    public void clear() {
        dataSource.clear();
    }
}
