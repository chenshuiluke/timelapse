package com.lukechenshui.timelapse;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;


public class TimerActivity extends AppCompatActivity {
    CounterTask task = new CounterTask();
    private Switch saveSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        saveSwitch = (Switch) findViewById(R.id.saveActionCheckBox);

    }

    public void toggleTimer(View view){
        Button button = (Button)findViewById(R.id.timerButton);
        if(task.getStatus() == AsyncTask.Status.RUNNING){
            task.setFinished();
            task = new CounterTask();
            button.setText("Start");
            System.out.println("Stopping counter!");
            saveSwitch.setEnabled(true);
        }
        else{
            button.setText("Stop");
            System.out.println("Starting counter!");
            task.execute();
            saveSwitch.setEnabled(false);
        }
        System.out.println(task.getStatus());
    }

    public void back(View view) {
        if (!task.isCancelled()) {
            task.cancel(true);
        }
        finish();
    }

    private class CounterTask extends AsyncTask {
        private Action action;
        private boolean finished = false;
        public void setFinished(){
            finished = true;
        }
        @Override
        protected Object doInBackground(Object[] params) {
            action = new Action(getIntent().getStringExtra("actionName"));

            try {
                for (int counter = 0; !finished; counter++) {

                    publishProgress(counter);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
            System.out.println("Done!");
            action.calculateDuration();
            return action;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            TextView counterView = (TextView)findViewById(R.id.counterTextView);
            action.incrementByOneSecond();
            counterView.setText(action.getFormattedDifference());
            System.out.println(values[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            System.out.println(action.duration.getSeconds());
            if (saveSwitch.isChecked()) {
                ActionDatabaseHelper dbHelper = new ActionDatabaseHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_START_DATE, action.getStartTime());
                values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_END_DATE, action.getEndTime());
                values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_DURATION, action.getDuration());
                values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_NAME, action.getName());

                db.insert(ActionReaderContract.ActionEntry.TABLE_NAME, null, values);
            }
        }


    }
}
