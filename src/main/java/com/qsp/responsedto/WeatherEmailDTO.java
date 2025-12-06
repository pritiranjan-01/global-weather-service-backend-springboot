package com.qsp.responsedto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WeatherEmailDTO {
    private String cityName;
    private String weatherType;
    private double temperature;
    private String advice;
    private String adviceColor;

    public WeatherEmailDTO(String cityName, String weatherType, double temperature) {
        this.cityName = cityName;
        this.weatherType = weatherType;
        this.temperature = temperature;
        this.generateAdvice();
    }

    private void generateAdvice() {
        // Handle null safety and ensure we match the ENUM string format
        String type = (weatherType != null) ? weatherType.toUpperCase() : "MEDIUM";
        
        switch (type) {
            case "COLD":
                this.advice = "❄️ It's freezing! Wear a heavy coat.";
                this.adviceColor = "#3182ce"; // Blue
                break;

            case "HOT":
                this.advice = "🥵 High temperatures. Stay hydrated and seek shade.";
                this.adviceColor = "#e53e3e"; // Red
                break;

            case "SUNNY":
                this.advice = "☀️ High UV levels. Don't forget sunscreen.";
                this.adviceColor = "#d69e2e"; // Gold/Orange
                break;

            case "RAINY":
                this.advice = "☔ Expect showers. Carry an umbrella.";
                this.adviceColor = "#4299e1"; // Light Blue
                break;

            case "STORM":
                this.advice = "⛈️ Severe weather alert! Stay indoors.";
                this.adviceColor = "#742a2a"; // Dark Red (Warning)
                break;

            case "SNOWY":
                this.advice = "🧤 Roads are icy. Drive with extra caution.";
                this.adviceColor = "#00b5d8"; // Cyan
                break;

            case "WINDY":
                this.advice = "🍃 Gusty winds today. Secure loose objects.";
                this.adviceColor = "#805ad5"; // Purple
                break;

            case "SMOG":
                this.advice = "😷 Air quality is poor. Recommend wearing a mask.";
                this.adviceColor = "#718096"; // Gray
                break;

            case "MEDIUM":
            default:
                this.advice = "✅ Pleasant conditions. Great day for outdoors.";
                this.adviceColor = "#38a169"; // Green
                break;
        }
    }
}