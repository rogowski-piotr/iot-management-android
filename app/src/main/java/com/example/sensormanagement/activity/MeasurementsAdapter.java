package com.example.sensormanagement.activity;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sensormanagement.R;

public class MeasurementsAdapter extends CursorAdapter {

    public MeasurementsAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_element, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView textViewHumidity = (TextView) view.findViewById(R.id.textViewHumidity);
        TextView textViewTemperature = (TextView) view.findViewById(R.id.textViewTemperature);

        String humidity = String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("HUMIDITY")));
        String temperature = String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("TEMPERATURE")));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));

        textViewDate.setText(date);
        textViewHumidity.setText(humidity);
        textViewTemperature.setText(temperature);
    }
}
