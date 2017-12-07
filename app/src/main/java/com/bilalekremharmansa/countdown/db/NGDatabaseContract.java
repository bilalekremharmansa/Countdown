package com.bilalekremharmansa.countdown.db;

import android.provider.BaseColumns;

/**
 * Created by bilalekrem on 06.12.2017.
 */

public class NGDatabaseContract {
    public static final String SQL_CREATE_NUMBER_GAME_ENTRY =
            "CREATE TABLE " + GameEntry.TABLE_NAME + "(" +
                    GameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    GameEntry.COLUMN_NUMBER_OF_LARGE_NUMS + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_ONE + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_TWO + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_THREE + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_FOUR + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_FIVE + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_NUMBER_SIX + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_TARGET + " INTEGER NOT NULL," +
                    GameEntry.COLUMN_PLAYED_DATETIME + " INTEGER," +
                    GameEntry.COLUMN_IS_ACTIVE + " INTEGER NOT NULL)";

    public static final String SQL_CREATE_SOLUTION_ENTRY =
            "CREATE TABLE " + SolutionEntry.TABLE_NAME + "(" +
                    SolutionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SolutionEntry.COLUMN_GAME_ID + " INTEGER NOT NULL," +
                    SolutionEntry.COLUMN_STEP_ONE + " TEXT NOT NULL," +
                    SolutionEntry.COLUMN_STEP_TWO + " TEXT NOT NULL," +
                    SolutionEntry.COLUMN_STEP_THREE + " TEXT NOT NULL," +
                    SolutionEntry.COLUMN_STEP_FOUR + " TEXT," +
                    SolutionEntry.COLUMN_STEP_FIVE + " TEXT)";

    public static class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "NUMBER_GAME";
        public static final String COLUMN_NUMBER_OF_LARGE_NUMS = "NUMBER_OF_LARGE_NUMS";
        public static final String COLUMN_NUMBER_ONE = "NUM_ONE";
        public static final String COLUMN_NUMBER_TWO = "NUM_TWO";
        public static final String COLUMN_NUMBER_THREE = "NUM_THREE";
        public static final String COLUMN_NUMBER_FOUR = "NUM_FOUR";
        public static final String COLUMN_NUMBER_FIVE = "NUM_FIVE";
        public static final String COLUMN_NUMBER_SIX = "NUM_SIX";
        public static final String COLUMN_TARGET = "TARGET";
        public static final String COLUMN_PLAYED_DATETIME = "PLAYED_DATETIME";
        public static final String COLUMN_IS_ACTIVE = "IS_ACTIVE";
    }


    public static class SolutionEntry implements BaseColumns {
        public static final String TABLE_NAME = "SOLUTION_LIST";
        public static final String COLUMN_GAME_ID = "GAME_ID";
        public static final String COLUMN_STEP_ONE = "STEP_ONE";
        public static final String COLUMN_STEP_TWO = "STEP_TWO";
        public static final String COLUMN_STEP_THREE = "STEP_THREE";
        public static final String COLUMN_STEP_FOUR = "STEP_FOUR";
        public static final String COLUMN_STEP_FIVE = "STEP_FIVE";

    }


}
