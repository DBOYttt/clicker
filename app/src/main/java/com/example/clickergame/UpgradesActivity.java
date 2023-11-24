package com.example.clickergame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpgradesActivity extends AppCompatActivity {

    private ClickerDatabaseHelper dbHelper;
    private int currentClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        dbHelper = new ClickerDatabaseHelper(this);
        currentClicks = getCurrentClickCount();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button purchaseButton = findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentClicks >= 100) {
                    dbHelper.purchaseUpgrade();
                    dbHelper.updateClickCount(currentClicks - 100); // Aktualizacja liczby kliknięć
                    Toast.makeText(UpgradesActivity.this, "Ulepszenie zakupione!", Toast.LENGTH_SHORT).show();
                    finish(); // Zamknij aktywność po zakupie
                } else {
                    Toast.makeText(UpgradesActivity.this, "Za mało kliknięć!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getCurrentClickCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CLICKS", new String[]{"COUNT"}, "_id = ?", new String[]{"1"}, null, null, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            int countIndex = cursor.getColumnIndex("COUNT");
            count = cursor.getInt(countIndex);
        }
        cursor.close();
        return count;
    }
}
