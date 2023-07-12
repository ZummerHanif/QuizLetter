package com.example.quizletter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "quiz_results";
    private static final String COLUMN_LETTER = "letter";
    private static final String COLUMN_ANSWER = "answer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_LETTER + " TEXT," +
                COLUMN_ANSWER + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void saveAnswer(String letter, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_LETTER + ", " + COLUMN_ANSWER + ") VALUES ('" +
                letter + "', '" + answer + "')";
        db.execSQL(insertQuery);
        db.close();
    }

    public String retrieveResults() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        StringBuilder resultBuilder = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String letter = cursor.getString(cursor.getColumnIndex(COLUMN_LETTER));
                String answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER));
                resultBuilder.append("Letter: ").append(letter)
                        .append(", Answer: ").append(answer).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return resultBuilder.toString();
    }
}

