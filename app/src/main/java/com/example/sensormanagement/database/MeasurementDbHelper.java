package com.example.sensormanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    public int countRecord() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select COUNT(*) from " + TABLE_NAME, null);
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    public Cursor readAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String[] columns = new String[] {"_id", "DATE", "TEMPERATURE", "HUMIDITY"};
        cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

}