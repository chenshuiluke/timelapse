package com.lukechenshui.timelapse;

import android.provider.BaseColumns;

/**
 * Created by luke on 9/12/16.
 */
public class ActionReaderContract {
    public static final String TEXT_TYPE = " TEXT";
    public static final String BLOB_TYPE = " BLOB";
    public static final String INTEGER_TYPE = " INTEGER";

    public static final String COMMA_SEP = ", ";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ActionEntry.TABLE_NAME + "( " +
                    ActionEntry.COLUMN_NAME_START_DATE + INTEGER_TYPE + COMMA_SEP +
                    ActionEntry.COLUMN_NAME_END_DATE + INTEGER_TYPE + COMMA_SEP +
                    ActionEntry.COLUMN_NAME_DURATION + INTEGER_TYPE + COMMA_SEP +
                    ActionEntry.COLUMN_NAME_NAME + TEXT_TYPE + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ActionEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "actions_database";

    public ActionReaderContract() {
    }
    public static class ActionEntry implements BaseColumns{
        public static final String TABLE_NAME = "actions";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_END_DATE = "end_date";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_NAME = "name";
    }
}
