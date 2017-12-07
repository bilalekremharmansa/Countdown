package com.bilalekremharmansa.countdown.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bilalekrem on 06.12.2017.
 */

public class CountdownDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "COUNTDOWN.db";

    public CountdownDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL(NGDatabaseContract.SQL_CREATE_NUMBER_GAME_ENTRY);
            db.execSQL(NGDatabaseContract.SQL_CREATE_SOLUTION_ENTRY);
        }
    }
}
