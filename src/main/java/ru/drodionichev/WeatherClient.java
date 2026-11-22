package ru.drodionichev;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {
    private final String apiKey = System.getenv("YANDEX_WEATHER_KEY");
    private final String baseUrl = "https://api.weather.yandex.ru/v2/forecast";

    public String getWeather(double lat, double lon, int limit) throws Exception {

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Ключ YANDEX_WEATHER_KEY не найден или пустой");
        }

        String weatherUri = baseUrl + "?" + "lon=" + lon + "&" + "lat=" + lat + "&" + "limit=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weatherUri))
                .header("X-Yandex-Weather-Key", apiKey)
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200){
            throw new IllegalStateException("Случилась ошибка, статус код: " + response.statusCode());
        }

        return response.body();
    }
}
