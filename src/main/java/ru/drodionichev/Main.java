package ru.drodionichev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        String key = System.getenv("YANDEX_WEATHER_KEY");
        String baseUrl = "https://api.weather.yandex.ru/v2/forecast";
        double lon = 30.34;
        double lat = 59.93;
        int limit = 3;

        if (key == null || key.isBlank()) {
            System.out.println("Системная ошибка обратитесь в чат поддержки");
        } else {
            String weatherUri = baseUrl + "?" + "lon=" + lon + "&" + "lat=" + lat + "&" + "limit=" + limit;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUri))
                    .header("X-Yandex-Weather-Key", key)
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            int temp = root.path("fact").path("temp").asInt();
            int humidity = root.path("fact").path("humidity").asInt();
            String nameInfo = root.path("info").path("tzinfo").path("name").asText();


            System.out.println("Текущая температура: " + temp);

            double sum = 0;
            for (JsonNode day: root.path("forecasts")){
                double tempAvg = day.path("parts").path("day").path("temp_avg").asDouble();
                sum += tempAvg;
            }
            System.out.println("Средняя температура: " + sum/limit);

            System.out.println(response.body());
            System.out.println(response.statusCode());

        }
    }
}