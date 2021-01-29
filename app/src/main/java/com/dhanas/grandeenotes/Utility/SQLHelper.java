package com.dhanas.grandeenotes.Utility;

import android.database.sqlite.SQLiteDatabase;

import static com.dhanas.grandeenotes.Utility.Constants.DATABASE_NAME;

public class SQLHelper {

    SQLiteDatabase mDatabase;

    public void createDownloadsTable() {
//        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    department varchar(200) NOT NULL,\n" +
                        "    joiningdate datetime NOT NULL,\n" +
                        "    salary double NOT NULL\n" +
                        ");"
        );
    }
}
