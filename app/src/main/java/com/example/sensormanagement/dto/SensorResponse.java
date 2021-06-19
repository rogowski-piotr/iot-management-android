package com.example.sensormanagement.dto;

import org.json.JSONObject;
import java.util.function.Function;

public class SensorResponse {

    private Float temperature;

    private Float humidity;

    private SensorResponse(Float temperature, Float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public static Function<String, SensorResponse> dtoToEntityMapper() {
        return jsonString -> {
            SensorResponse responseObj = null;
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                Float temperature = Float.valueOf(jsonObj.getString("temperature"));
                Float humidity = Float.valueOf(jsonObj.getString("humidity"));
                responseObj = new SensorResponse(temperature, humidity);
            } catch (Exception ignored) { }
            return responseObj;
        };
    }

}
