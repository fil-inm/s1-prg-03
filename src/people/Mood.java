package people;

public enum Mood {
    CALM("спокойное"),
    CAPRICIOUS("капризное"),
    SATISFIED("довольное"),
    TIRED("усталое"),
    EXHAUSTED("сил больше нет"),
    IN_LOVE("влюблённое");

    private final String description;

    Mood(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}