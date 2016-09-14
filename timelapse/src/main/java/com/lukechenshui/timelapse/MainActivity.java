package com.lukechenshui.timelapse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ArrayList<Action> actions = new ArrayList<>();
    HashSet<Action> actionNamesSet = new HashSet<>();

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
                            startActivity(intent);
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

            return null;
        }
    }
}
