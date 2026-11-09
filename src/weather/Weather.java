package weather;

import java.util.Random;

public record Weather(double temperature, double humidity, double wind) {

    public Weather {
        if (temperature < -30 || temperature > 35) {
            throw new IllegalArgumentException("температура должна быть от -30 до 35, а не " + temperature);
        }
        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException("влажность должна быть от 0 до 100, а не " + humidity);
        }
        if (wind < 0 || wind > 25) {
            throw new IllegalArgumentException("ветер должен быть от 0 до 25, а не " + wind);
        }
    }

    public static Weather random(Random r) {
        return new Weather(-10 + r.nextDouble() * 40, 20 + r.nextDouble() * 70, r.nextDouble() * 12);
    }

    public Weather next(Random r) {
        double t = temperature + 0.03 * (50 - humidity) - 0.2 * wind + r.nextGaussian() * 2;
        double h = humidity + 0.5 * wind - 0.4 * (temperature - 15) + r.nextGaussian() * 5;
        double w = Math.abs(wind + 0.05 * (humidity - 60) - 0.05 * (temperature - 10) + r.nextGaussian() * 1.5);
        return new Weather(Math.clamp(t, -30, 35), Math.clamp(h, 0, 100), Math.clamp(w, 0, 25));
    }

    public boolean isCold() {
        return temperature < 5;
    }

    public boolean isHot() {
        return temperature > 22;
    }

    public boolean isGloomy() {
        return humidity > 75 || wind > 10;
    }

    @Override
    public String toString() {
        return String.format("%.1f°C, влажность %.0f%%, ветер %.1f м/с, %s",
                temperature, humidity, wind, isGloomy() ? "пасмурно" : "ясно");
    }
}
