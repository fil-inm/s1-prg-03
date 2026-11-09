package food;

import weather.Weather;

public enum DishType  {
    SOUP("суп", true, false),
    PORRIDGE("каша", true, false),
    CUTLET("котлеты", true, true),
    PASTA("макароны", true, false),
    PUREE("пюре", false, false),
    KVASS("квас", false, false),
    JELLY("кисель", false, false);

    private final String title;
    private final boolean hot;
    private final boolean needsSolidBase;

    DishType(String title, boolean hot, boolean needsSolidBase) {
        this.title = title;
        this.hot = hot;
        this.needsSolidBase = needsSolidBase;
    }

    public double weight(Weather w) {
        double result = 1.0;
        if (w.isCold()) {
            result *= hot ? 2.5 : 0.5;
        }
        if (w.isHot()) {
            result *= hot ? 0.4 : 2.5;
        }
        if (w.isGloomy() && this == SOUP) {
            result *= 1.5;
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public boolean needsSolidBase() {
        return needsSolidBase;
    }
}
