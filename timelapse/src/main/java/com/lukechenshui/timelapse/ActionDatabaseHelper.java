package com.lukechenshui.timelapse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
