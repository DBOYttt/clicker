package com.example.clickergame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public void purchaseUpgrade() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues upgradeValues = new ContentValues();
        upgradeValues.put("UPGRADE", 1); // 1 oznacza, że ulepszenie zostało zakupione
        db.update("CLICKS", upgradeValues, "_id = ?", new String[]{"1"});
    }

    public boolean isUpgradePurchased() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("CLICKS", new String[]{"UPGRADE"}, "_id = ?", new String[]{"1"}, null, null, null);
        boolean isPurchased = false;

        if (cursor.moveToFirst()) {
            int upgradeIndex = cursor.getColumnIndex("UPGRADE");
            isPurchased = cursor.getInt(upgradeIndex) == 1;
        }
        cursor.close();
        return isPurchased;
    }

}

