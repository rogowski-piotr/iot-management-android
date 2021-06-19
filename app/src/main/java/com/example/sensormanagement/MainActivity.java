package com.example.sensormanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensormanagement.activity.ShowMeasurements;
import com.example.sensormanagement.database.MeasurementDbHelper;
import com.example.sensormanagement.dto.SensorResponse;
import com.example.sensormanagement.service.MeasurementExecutorService;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    private TextView textViewInfo;
    private TextView textViewError;
    private MeasurementExecutorService measurementExecutorService;
    private boolean isBinded = false;
    private static MeasurementDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = findViewById(R.id.textViewInfo);
        textViewError = findViewById(R.id.textViewError);
        dbHelper = new MeasurementDbHelper(this);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName comName, IBinder binder) {
            MeasurementExecutorService.MeasurementExecutorServiceBinder serviceBinder = (MeasurementExecutorService.MeasurementExecutorServiceBinder) binder;
            measurementExecutorService = serviceBinder.getMeasurementExecutorService();
            isBinded = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName comName) {
            isBinded = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MeasurementExecutorService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBinded) {
            unbindService(serviceConnection);
            isBinded = false;
        }
    }

    public void executeMeasurement(View view) {
        new Handler().post(() -> {
            SensorResponse response = measurementExecutorService.executeMeasurement();
            try {
                dbHelper.addRecord(response.getTemperature(), response.getHumidity());
                textViewError.setText("");
                textViewInfo.setText(String.format("Temperature: %s °C \nHumidity: %s %%", response.getTemperature(), response.getHumidity()));
                Toast.makeText(this, "Data added to database", Toast.LENGTH_LONG).show();

            } catch (NullPointerException e) {
                textViewInfo.setText("");
                textViewError.setText("Can not connect to sensor");
                Toast.makeText(this, "can not add data to database", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, ShowMeasurements.class);
        startActivity(intent);
    }

}