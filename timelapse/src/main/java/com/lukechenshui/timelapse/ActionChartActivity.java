package com.lukechenshui.timelapse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ActionChartActivity extends AppCompatActivity {
    String actionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_chart);
        ArrayList<Action> actions = getIntent().getParcelableArrayListExtra("actions");
        Collections.reverse(actions);
        actionName = actions.size() > 0 ? actions.get(0).getName() : "";

        List<Entry> entries = new ArrayList<>();

        for (int counter = 0; counter < actions.size(); counter++) {
            Action action = actions.get(counter);
            entries.add(new Entry(counter, action.getDuration()));
        }

        LineDataSet dataSet = new LineDataSet(entries, actionName + " Times");
        LineData data = new LineData(dataSet);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }
}
