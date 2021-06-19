package com.example.sensormanagement;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SensorClient implements Runnable {
    private static final String ADDRESS_IP = "192.168.0.19";
    private static final int PORT = 50007;
    private boolean waiting = true;
    private String response = null;

    public void run() {
        Log.v("sensor client", "connecting");
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ADDRESS_IP, PORT), 1000);
            Log.v("sensor client", "connected");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = reader.readLine();
            Log.v("sensor client", response);
            reader.close();
        } catch (Exception ignored) {
            Log.v("sensor client", ignored.getMessage());
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