package com.mycompany.app;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;

public class Task3 {
    public static void getWeather() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");

            WebElement elem = webDriver.findElement(By.tagName("pre"));

            String json_str = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);

            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            JSONArray temperature = (JSONArray) hourly.get("temperature_2m");
            JSONArray rain = (JSONArray) hourly.get("rain");

            FileWriter writer = new FileWriter("..\\result\\forecast.txt", false);
            writer.write(String.format("%-3s %-18s %-13s %-13s%n", "№", "Дата/время", "Температура", "Осадки (мм)"));

            for (int i = 0; i < time.size(); ++i) {
                writer.append(String.format("%-4d %-18s %-13.1f %-11.2f%n", i+1, (String) time.get(i), (Double) temperature.get(i), (Double) rain.get(i)));
            }

            writer.flush();
            
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
