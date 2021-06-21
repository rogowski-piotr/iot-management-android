package com.example.sensormanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MeasurementDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MeasurementDatabases.db";
    private static final String TABLE_NAME = "measurements";
    private static final int DB_VERSION = 1;

    public MeasurementDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "DATE DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + "TEMPERATURE REAL, "
            + "HUMIDITY REAL);");
        Log.v("DATABASE","CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

    public void reset() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        database.execSQL("CREATE TABLE " + TABLE_NAME +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "DATE DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + "TEMPERATURE REAL, "
            + "HUMIDITY REAL);");
    }

    public boolean addRecord(float temperature, float humidity) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEMPERATURE", temperature);
        values.put("HUMIDITY", humidity);
        database.insert(TABLE_NAME,null, values);
        return true;
    }

    public int countRecords() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select COUNT(*) from " + TABLE_NAME, null);
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    public Cursor readAll(float minValue, float maxValue, String type) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] {"_id", "DATE", "TEMPERATURE", "HUMIDITY"};
        String selection = type.equals("Temperature")
                ? "TEMPERATURE >= ? AND TEMPERATURE <= ?"
                : "HUMIDITY >= ? AND HUMIDITY <= ?";
        String[] selectionArgs = new String[] {String.valueOf(minValue), String.valueOf(maxValue)};
        return database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    public boolean deleteRecord(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(TABLE_NAME, "_id=?", new String[] {String.valueOf(id)});
        return true;
    }

    public Cursor getOneRecord(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] {"_id", "DATE", "TEMPERATURE", "HUMIDITY"};
        String selection = "_id = ?";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        return database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

}