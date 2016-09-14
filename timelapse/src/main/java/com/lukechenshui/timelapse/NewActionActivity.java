package com.lukechenshui.timelapse;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_action);
    }

    public void cancel(View view) {
        finish();
    }

    public void createAction(View view) {
        ActionDatabaseHelper dbHelper = new ActionDatabaseHelper(getApplicationContext());

        EditText actionNameEditText = (EditText) findViewById(R.id.newActioneditTextNameEditText);
        String actionName = actionNameEditText.getText().toString();
        if (actionName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter the name of the new action", Toast.LENGTH_LONG).show();
        } else {
            ArrayList<Action> actionList = dbHelper.getAllRecords(getApplicationContext());

            for (Action action : actionList) {
                if (action.getName() != null && action.getName().equals(actionName)) {
                    Toast.makeText(getApplicationContext(), "There is already an action with that name. Please enter a different name.", Toast.LENGTH_SHORT).show();
                }
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Action action = new Action();
            action.calculateDuration();
            action.setName(actionName);

            ContentValues values = new ContentValues();

            values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_NAME, actionName);
            values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_START_DATE, action.getStartTime());
            values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_END_DATE, action.getEndTime());
            values.put(ActionReaderContract.ActionEntry.COLUMN_NAME_DURATION, action.getDuration());

            db.insert(ActionReaderContract.ActionEntry.TABLE_NAME, null, values);
            finish();
        }

    }
}
