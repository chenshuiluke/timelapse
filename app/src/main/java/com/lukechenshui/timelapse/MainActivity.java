package com.lukechenshui.timelapse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    CounterTask task = new CounterTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toggleTimer(View view){
        Button button = (Button)findViewById(R.id.timerButton);
        if(task.getStatus() == AsyncTask.Status.RUNNING){
            task.cancel(false);
            task = new CounterTask();
            button.setText("Start");
            System.out.println("Stopping counter!");
        }
        else{
            button.setText("Stop");
            System.out.println("Starting counter!");
        }
        System.out.println(task.getStatus());
    }

    private class CounterTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            for(int counter = 0; !isCancelled(); counter++){
                try{
                    publishProgress(counter);
                    Thread.sleep(1000);
                }
                catch(InterruptedException exc){
                    exc.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            TextView counterView = (TextView)findViewById(R.id.counterTextView);
            counterView.setText(String.valueOf(values[0]));
            System.out.println(values[0]);
        }
    }

}
