package com.lukechenshui.timelapse;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ActionChartActivity extends AppCompatActivity {
    String actionName = "";
    LineChart chart;
    int previousVal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_chart);
        ArrayList<Action> actions = getIntent().getParcelableArrayListExtra("actions");
        final HashMap<Integer, Action> actionHashMap = new HashMap<>();
        chart = (LineChart) findViewById(R.id.chart);
        Collections.reverse(actions);
        actionName = actions.size() > 0 ? actions.get(0).getName() : "";

        List<Entry> entries = new ArrayList<>();
        Log.i("Charting", "Number of actions: " + String.valueOf(actions.size()));
        for (int counter = 0; counter < actions.size(); counter++) {
            Action action = actions.get(counter);
            entries.add(new Entry(counter, action.getDuration()));
            actionHashMap.put(counter, action);
        }

        LineDataSet dataSet = new LineDataSet(entries, actionName + " Times");
        LineData data = new LineData(dataSet);


        dataSet.setLineWidth(1.75f);
        dataSet.setCircleRadius(5f);
        dataSet.setCircleHoleRadius(2.5f);
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setHighLightColor(Color.BLUE);
        dataSet.setDrawValues(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1 / actions.size());
        xAxis.setLabelCount(3, true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(20);

        chart.getAxisLeft().setTextColor(Color.BLACK);
        chart.getAxisRight().setTextColor(Color.BLACK);

        chart.getAxisLeft().setTextSize(20);
        chart.getAxisRight().setTextSize(20);

        xAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.i("Charting", "Value: " + String.valueOf(value));
                Action action = actionHashMap.get((int) value);
                return "";

            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        chart.setExtraOffsets(30, 10, 30, 0);
        chart.setData(data);
        chart.setDescription("Duration of action in seconds");
        chart.setDescriptionTextSize(15);
        chart.setDescriptionColor(Color.BLACK);
        chart.setBackgroundColor(Color.WHITE);

        chart.invalidate();
    }
}
