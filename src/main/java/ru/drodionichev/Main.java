package ru.drodionichev;

public class Main {
    public static void main(String[] args) throws Exception {

        WeatherClient weatherClient = new WeatherClient();

        double lon = 30.34;
        double lat = 59.93;
        int limit = 3;

        String json = weatherClient.getWeather(lat, lon, limit);
        WeatherCalculator weatherCalculator = new WeatherCalculator(json);
        System.out.println(json);
        System.out.println("Текущая температура: " + weatherCalculator.getCurrentTemperature());
        System.out.println("Средняя температура: " + weatherCalculator.calculateAverageTemperature(limit));
    }
}