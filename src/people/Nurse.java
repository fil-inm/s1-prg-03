package people;

import exceptions.ImpossibleDishException;
import exceptions.NurseExhaustedException;
import food.Dish;
import hospital.Kitchen;
import weather.Weather;

import java.util.ArrayList;
import java.util.Random;

public class Nurse extends Person{
    private static final int MAX_APPROACHES_PER_DAY = 2;

    private final ArrayList<Dish> menu = new ArrayList<>();
    private int fatigue;
    private final int maxFatigue;
    private int approachesToday;

    public Nurse(String name, Random random) {
        super(name, random);
        while (menu.size() < 2) {
            Dish d = Dish.random(random);
            if (d.isPossible()) {
                menu.add(d);
            }
        }
        maxFatigue = 8 + random.nextInt(6);
    }

    @Override
    public void onNewDay(Weather w) {
        approachesToday = 0;
        if (!isExhausted()) {
            fatigue = Math.max(0, fatigue - (w.isGloomy() ? 2 : 3));
            setMood(Mood.CALM);
        }
    }

    public boolean approach(Patient patient, Kitchen kitchen) throws NurseExhaustedException {
        approachesToday++;
        Dish wish = patient.currentWish();
        System.out.println("Нянечка " + getName() + " подходит к Пульке (" + approachesToday + "/"
                + MAX_APPROACHES_PER_DAY + "). " + patient.getName() + " требует " + wish + ".");

        Dish dish;
        try {
            dish = kitchen.cook(wish);
            System.out.println("  Кухня приготовила " + dish + ".");
        } catch (ImpossibleDishException e) {
            System.out.println("  Кухня: " + e.getMessage() + ".");
            dish = pickFromMenu(wish);
            System.out.println("  " + getName() + " приносит из дома " + dish + ".");
        }

        boolean accepted = patient.accept(dish, this);
        tire(accepted ? 1 : 2);
        if (!accepted) {
            setMood(Mood.TIRED);
        }
        return accepted;
    }

    public boolean canApproach() {
        return !isExhausted() && approachesToday < MAX_APPROACHES_PER_DAY;
    }

    public boolean isExhausted() {
        return fatigue >= maxFatigue;
    }

    private void complain() {
        setMood(Mood.EXHAUSTED);
        System.out.println("  " + getName() + ": «Спокон веку такого больного не было! Сущее наказание, а не больной!»");
    }

    private Dish pickFromMenu(Dish wish) {
        Dish best = menu.getFirst();
        for (Dish d : menu) {
            if (score(d, wish) > score(best, wish)) {
                best = d;
            }
        }
        return best;
    }

    private int score(Dish d, Dish wish) {
        int s = 0;
        if (d.type() == wish.type()) s += 2;
        if (d.ingredient() == wish.ingredient()) s += 1;
        return s;
    }

    private void tire(int amount) throws NurseExhaustedException {
        fatigue += amount;
        if (isExhausted()) {
            complain();
            throw new NurseExhaustedException(getName());
        }
    }

    @Override
    public String toString() {
        return "Nurse{name='" + getName() + "', mood=" + getMood() + ", fatigue=" + fatigue + "/"
                + maxFatigue + ", menu=" + menu + "}";
    }
}
