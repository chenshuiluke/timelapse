package com.lukechenshui.timelapse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by luke on 9/12/16.
 */
public class ActionDatabaseHelper extends SQLiteOpenHelper {

    public ActionDatabaseHelper(Context context){
        super(context, ActionReaderContract.DATABASE_NAME, null, ActionReaderContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ActionReaderContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ActionReaderContract.SQL_DELETE_ENTRIES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Cursor getCursor(Context context) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columnNames = {
                ActionReaderContract.ActionEntry.COLUMN_NAME_NAME,
                ActionReaderContract.ActionEntry.COLUMN_NAME_START_DATE,
                ActionReaderContract.ActionEntry.COLUMN_NAME_END_DATE,
                ActionReaderContract.ActionEntry.COLUMN_NAME_DURATION
        };
        String selection = ActionReaderContract.ActionEntry.COLUMN_NAME_NAME;
        String[] selectionArgs = {"*"};
        Cursor cursor = db.rawQuery("SELECT * FROM " + ActionReaderContract.ActionEntry.TABLE_NAME, null);
        return cursor;
    }

    public ArrayList<Action> getAllRecords(Context context) {
        Cursor cursor = getCursor(context);
        ArrayList<Action> actions = new ArrayList<>();
        while (cursor.moveToNext()) {
            Action tempAction = new Action();
            tempAction.setName(cursor.getString(cursor.getColumnIndexOrThrow(ActionReaderContract.ActionEntry.COLUMN_NAME_NAME)));
            tempAction.setStartTime(cursor.getLong(cursor.getColumnIndexOrThrow(ActionReaderContract.ActionEntry.COLUMN_NAME_START_DATE)));
            tempAction.setEndTime(cursor.getLong(cursor.getColumnIndexOrThrow(ActionReaderContract.ActionEntry.COLUMN_NAME_END_DATE)));
            tempAction.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(ActionReaderContract.ActionEntry.COLUMN_NAME_DURATION)));
            actions.add(tempAction);
        }
        return actions;
    }
}
