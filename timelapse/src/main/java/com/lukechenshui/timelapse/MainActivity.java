package com.lukechenshui.timelapse;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ArrayList<Action> actions = new ArrayList<>();
    HashSet<Action> actionNamesSet = new HashSet<>();
    HashMap<String, Float> actionProportions = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetRecordsAsyncTask task = new GetRecordsAsyncTask();
        task.execute();
    }

    public void showNewActionActivity(View view) {
        Intent intent = new Intent(this, NewActionActivity.class);
        startActivityForResult(intent, 1);
        recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GetRecordsAsyncTask task = new GetRecordsAsyncTask();
        task.execute();
    }

    private class GetRecordsAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            final ActionDatabaseHelper dbHelper = new ActionDatabaseHelper(getApplicationContext());
            final ListView actionsList = (ListView) findViewById(R.id.actionNamesListView);
            actions = dbHelper.getAllRecords(getApplicationContext());

            for (Action action : actions) {
                actionNamesSet.add(action);
            }

            for (Action action : actionNamesSet) {
                ArrayList<Action> list = dbHelper.getAllRecordsWhereNameEquals(getApplicationContext(), action.getName());
                Float sum = new Float(0);
                for (Action tempAction : list) {
                    sum += tempAction.getDuration();
                }
                actionProportions.put(action.getName(), sum);
            }

            ArrayList<Action> actionNamesList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    actionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Action item = (Action) actionsList.getAdapter().getItem(i);
                            Intent intent = new Intent(getApplicationContext(), ActionDetailsActivity.class);
                            intent.putExtra("actionName", item.getName());
                            startActivityForResult(intent, 1);
                        }
                    });
                }
            });

            actionNamesList.addAll(actionNamesSet);

            System.out.println("Action Names:" + actionNamesList);
            final ActionAdapter adapter = new ActionAdapter(getApplicationContext(), actionNamesList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    actionsList.setAdapter(new ActionAdapter(getApplicationContext(), new ArrayList<Action>()));
                    actionsList.setAdapter(adapter);
                }
            });

            final PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
            ArrayList<PieEntry> entries = new ArrayList<>();

            int counter = 0;

            for (Action action : actionNamesSet) {
                entries.add(new PieEntry(actionProportions.get(action.getName()), action.getName()));
                System.out.println(actionProportions.get(action.getName()));
                counter++;
            }

            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());


            PieDataSet dataSet = new PieDataSet(entries, "Action Proportions");
            final PieData data = new PieData(dataSet);
            data.setValueFormatter(new DefaultValueFormatter(0));
            data.setValueTextSize(20f);
            dataSet.setColors(colors);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pieChart.setEntryLabelTextSize(20F);
                    pieChart.setEntryLabelColor(Color.BLACK);
                    pieChart.setUsePercentValues(false);
                    pieChart.setData(data);
                }
            });




            return null;
        }
    }
}
