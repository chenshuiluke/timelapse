package com.lukechenshui.timelapse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ActionDetailsActivity extends AppCompatActivity {
    String actionName;
    ArrayList<Action> actions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_details);
        Intent intent = getIntent();
        actionName = getIntent().getStringExtra("actionName");
        Button startButton = (Button) findViewById(R.id.startPerformingAction);
        startButton.setText("Start " + actionName);
        PopulateListViewTask task = new PopulateListViewTask();
        task.execute();
    }


    public void populateListView() {
        ActionDatabaseHelper dbHelper = new ActionDatabaseHelper(getApplicationContext());
        final ArrayList<Action> tempActionsList = dbHelper.getAllRecordsWhereNameEquals(getApplicationContext(), actionName);


        System.out.println("Action details: " + tempActionsList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView list = (ListView) findViewById(R.id.actionDetailsListView);


                list.setAdapter(new ActionDetailsAdapter(getApplicationContext(), new ArrayList<Action>()));
                ActionDetailsAdapter adapter = new ActionDetailsAdapter(getApplicationContext(), tempActionsList);
                list.setAdapter(adapter);
            }
        });
        actions = tempActionsList;
    }

    public void startAction(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("actionName", actionName);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PopulateListViewTask task = new PopulateListViewTask();
        task.execute();
    }

    public void showChartActivity(View view) {
        Intent intent = new Intent(this, ActionChartActivity.class);
        intent.putExtra("actions", actions);
        startActivity(intent);
    }

    public class PopulateListViewTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            populateListView();
            return null;
        }
    }
}
