package com.example.clickergame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView clickCounter;
    private int count = 0;
    private int imageIndex = 0;
    private int[] imageArray = {R.drawable.img1, R.drawable.img2};
    private ClickerDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        clickCounter = findViewById(R.id.clickCounter);
        dbHelper = new ClickerDatabaseHelper(this);

        loadClickCount(); // Wczytuje liczbę kliknięć

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                clickCounter.setText("Klikniecia: " + count);
                changeImage();
                dbHelper.updateClickCount(count); // Aktualizuje licznik w bazie danych
            }
        });
    }

    private void changeImage() {
        imageView.setImageResource(imageArray[imageIndex]);
        imageIndex++;
        if (imageIndex >= imageArray.length) {
            imageIndex = 0;
        }
    }

    private void loadClickCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("CLICKS", new String[] {"COUNT"}, "_id = ?", new String[] {"1"}, null, null, null);

        if (cursor.moveToFirst()) {
            int countIndex = cursor.getColumnIndex("COUNT");
            count = cursor.getInt(countIndex);
            clickCounter.setText("Kliknięcia: " + count);
        }
        cursor.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.updateClickCount(count); // Zapisuje liczbę kliknięć przy pauzowaniu aktywności
    }
}
