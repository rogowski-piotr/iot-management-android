package com.example.sensormanagement.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.sensormanagement.SensorClient;

public class MeasurementExecutorService extends Service {
    private final MeasurementExecutorServiceBinder binder = new MeasurementExecutorServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MeasurementExecutorServiceBinder extends Binder {
        public MeasurementExecutorService getMeasurementExecutorService() {
            return MeasurementExecutorService.this;
        }
    }

    public String executeMeasurement() {
        Log.v("Service", "executed");
        SensorClient sensorClient = new SensorClient();
        AsyncTask.execute(sensorClient);
        while (sensorClient.isWaiting()) {}
        return sensorClient.getResponse();
    }

}
