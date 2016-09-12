package com.lukechenshui.timelapse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.realm.Realm;


public class TimerActivity extends AppCompatActivity {
    CounterTask task = new CounterTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
    }

    public void toggleTimer(View view){
        Button button = (Button)findViewById(R.id.timerButton);
        if(task.getStatus() == AsyncTask.Status.RUNNING){
            task.setFinished();
            task = new CounterTask();
            button.setText("Start");
            System.out.println("Stopping counter!");
        }
        else{
            button.setText("Stop");
            System.out.println("Starting counter!");
            task.execute();
        }
        System.out.println(task.getStatus());
    }

    void saveAction(Action action){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(action);
        realm.commitTransaction();
        System.out.println(action.duration.getSeconds());
    }

    private class CounterTask extends AsyncTask {
        private Action action;
        private boolean finished = false;
        public void setFinished(){
            finished = true;
        }
        @Override
        protected Object doInBackground(Object[] params) {
            action = new Action("TEMP");
            for(int counter = 0; !finished; counter++){
                try{
                    publishProgress(counter);
                    Thread.sleep(1000);
                }
                catch(InterruptedException exc){
                    exc.printStackTrace();
                }
            }
            System.out.println("Done!");
            action.calculateDuration();
            return action;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            TextView counterView = (TextView)findViewById(R.id.counterTextView);
            action.incrementByOneSecond();
            counterView.setText(action.getDifferenceInSeconds());
            System.out.println(values[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            saveAction((Action)o);
        }
    }

}
