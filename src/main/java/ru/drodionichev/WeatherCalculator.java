package ru.drodionichev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherCalculator {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonNode root;

    public WeatherCalculator(String json) throws Exception{
        this.root = mapper.readTree(json);
    }

    public int getCurrentTemperature() {
        return root.path("fact")
                .path("temp")
                .asInt();
    }

    public double calculateAverageTemperature(int limit) {

        double sum = 0;
        int n = 0;

        for (n = 0; n < limit && n < root.path("forecasts").size();  n++){
            double tempAvg = root.path("forecasts")
                    .get(n)
                    .path("parts")
                    .path("day")
                    .path("temp_avg")
                    .asDouble();

            sum += tempAvg;
        }

        if (n == 0){
            throw new IllegalArgumentException("Количество обработанных дней не может равняться нулю");
        }
        return sum / n;
    }
}
