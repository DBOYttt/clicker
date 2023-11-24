package com.example.clickergame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClickerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "clicker";
    private static final int DB_VERSION = 1;

    ClickerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CLICKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "COUNT INTEGER);");

        insertClickCount(db, 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertClickCount(SQLiteDatabase db, int count) {
        ContentValues clickValues = new ContentValues();
        clickValues.put("COUNT", count);
        db.insert("CLICKS", null, clickValues);
    }

    public void updateClickCount(int count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues clickValues = new ContentValues();
        clickValues.put("COUNT", count);
        db.update("CLICKS", clickValues, "_id = ?", new String[] {"1"});
    }
}

