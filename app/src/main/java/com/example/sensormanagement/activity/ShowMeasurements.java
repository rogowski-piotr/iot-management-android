package com.example.sensormanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.example.sensormanagement.R;
import com.example.sensormanagement.database.MeasurementDbHelper;

public class ShowMeasurements extends AppCompatActivity {

    private ListView listView;
    private MeasurementDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_measurements);
        listView = findViewById(R.id.ListViewMeasurements);

        Bundle bundle = getIntent().getExtras();
        float max = bundle.getFloat("max");
        float min = bundle.getFloat("min");
        String type = bundle.getString("type");

        dbHelper = new MeasurementDbHelper(this);
        Cursor results = dbHelper.readAll(min, max, type);
        Toast.makeText(this, "Found " + results.getCount() + " elements", Toast.LENGTH_LONG).show();

        MeasurementsAdapter adapter = new MeasurementsAdapter(this, results);
        listView.setAdapter(adapter);
    }
}
