package people;

import weather.Weather;

import java.util.Objects;
import java.util.Random;

public abstract class Person {
    private final String name;
    private Mood mood = Mood.CALM;
    protected final Random random;

    protected Person(String name, Random random) {
        this.name = name;
        this.random = random;
    }

    public abstract void onNewDay(Weather w);

    public String getName() {
        return name;
    }

    public Mood getMood() {
        return mood;
    }

    protected void setMood(Mood newMood) {
        if (mood != newMood) {
            mood = newMood;
            System.out.println("  [" + name + "] настроение: " + newMood.getDescription());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equals(((Person) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getSimpleName(), name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', mood=" + mood + "}";
    }
}
