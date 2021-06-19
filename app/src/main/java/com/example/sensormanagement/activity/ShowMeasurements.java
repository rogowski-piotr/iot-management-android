package com.example.sensormanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sensormanagement.MainActivity;
import com.example.sensormanagement.R;
import com.example.sensormanagement.database.MeasurementDbHelper;
import com.example.sensormanagement.dto.SensorResponse;

import java.util.List;

public class ShowMeasurements extends AppCompatActivity {

    private ListView listView;
    private MeasurementDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_measurements);
        listView = findViewById(R.id.ListViewMeasurements);

        dbHelper = new MeasurementDbHelper(this);
        Toast.makeText(this, "Found " + dbHelper.countRecord() + " elements", Toast.LENGTH_LONG).show();

        MeasurementsAdapter adapter = new MeasurementsAdapter(this, dbHelper.readAll());
        listView.setAdapter(adapter);
    }
}
