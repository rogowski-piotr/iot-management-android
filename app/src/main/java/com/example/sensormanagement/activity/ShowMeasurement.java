package com.example.sensormanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensormanagement.MainActivity;
import com.example.sensormanagement.R;
import com.example.sensormanagement.database.MeasurementDbHelper;

public class ShowMeasurement extends AppCompatActivity {
    private int elementId;
    private MeasurementDbHelper dbHelper;
    private TextView textViewTemperature;
    private TextView textViewHumidity;
    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_measurement);
        textViewTemperature = findViewById(R.id.textViewTemperatureValue);
        textViewHumidity = findViewById(R.id.textViewHumidityValue);
        textViewDate = findViewById(R.id.textViewDateValue);

        Bundle bundle = getIntent().getExtras();
        elementId = bundle.getInt("id");

        dbHelper = new MeasurementDbHelper(this);
        Cursor cursor = dbHelper.getOneRecord(elementId);
        cursor.moveToFirst();
        String humidity = String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("HUMIDITY")));
        String temperature = String.valueOf(cursor.getFloat(cursor.getColumnIndexOrThrow("TEMPERATURE")));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));

        textViewTemperature.setText(temperature + " °C");
        textViewHumidity.setText(humidity + " %");
        textViewDate.setText(date);
    }

    public void deleteElement(View view) {
        dbHelper.deleteRecord(elementId);
        Toast.makeText(this, "Element has been deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
