package com.example.sensormanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SensorClient implements Runnable {
    private static final String ADDRESS_IP = "192.168.0.19";
    private static final int PORT = 50007;
    private boolean waiting = true;
    private String response = null;

    public void run() {
        Log.v("sensor client", "connecting");
        try (Socket socket = new Socket("192.168.0.19", 50007)) {
            Log.v("sensor client", "connected");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            Log.v("sensor client", response);
            reader.close();
        } catch (IOException | NullPointerException e) {
            Log.v("sensor client", e.getMessage());
        }
        waiting = false;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public String getResponse() {
        return response;
    }

}