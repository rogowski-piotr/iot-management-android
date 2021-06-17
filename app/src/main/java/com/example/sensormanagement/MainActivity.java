package com.example.sensormanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.example.sensormanagement.service.MeasurementExecutorService;

public class MainActivity extends AppCompatActivity {
    private TextView textViewInfo;
    private MeasurementExecutorService measurementExecutorService;
    private boolean isBinded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = findViewById(R.id.textViewInfo);
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
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String value = measurementExecutorService.executeMeasurement();
                textViewInfo.setText(String.format("Received value: %s", value));
            }
        });
    }

}