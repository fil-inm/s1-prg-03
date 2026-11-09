package people;

import food.Dish;
import food.DishType;
import food.Ingredient;
import weather.Weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Patient extends Person {
    private static final int LOVE_THRESHOLD = 8;
    private static final int RECOVERY_HEALTH = 9;
    private static final int WISHES_PER_DAY = 3;


    private final ArrayList<Dish> wishes = new ArrayList<>();
    private double capriciousness;
    private int health;
    private final Map<Nurse, Integer> attraction = new HashMap<>();
    private Weather weather;

    public Patient(String name, Random random) {
        super(name, random);
        capriciousness = 0.3 + random.nextDouble() * 0.3;
    }

    @Override
    public void onNewDay(Weather w) {
        weather = w;
        if (w.isGloomy()) {
            capriciousness = Math.min(0.9, capriciousness + 0.05);
        }
        wishes.clear();
        for (int i = 0; i < WISHES_PER_DAY; i++) {
            wishes.add(inventWish());
        }
        System.out.println(getName() + " сегодня хочет: " + wishes);
        setMood(Mood.CAPRICIOUS);
    }

    public boolean accept(Dish dish, Nurse from) {
        Dish wish = currentWish();
        double chance;
        int gain;
        if (dish.equals(wish)) {
            chance = 1 - capriciousness;
            gain = 2;
        } else if (dish.sameTypeAs(wish)) {
            chance = (1 - capriciousness) * 0.5;
            gain = 1;
        } else {
            chance = 0.05;
            gain = 1;
        }

        if (random.nextDouble() < chance) {
            System.out.println("  " + getName() + " съел " + dish + ".");
            wishes.removeFirst();
            if (wishes.isEmpty()) {
                wishes.add(inventWish());
            }
            health++;
            // от такого внимания совсем избаловался
            capriciousness = Math.min(0.9, capriciousness + 0.05);
            int value = attraction.merge(from, gain, Integer::sum);
            System.out.println("  " + getName() + " смотрит на нянечку " + from.getName()
                    + " всё ласковее (влечение " + value + ").");
            setMood(value >= LOVE_THRESHOLD ? Mood.IN_LOVE : Mood.SATISFIED);
            return true;
        }

        wishes.set(0, inventWish());
        System.out.println("  " + getName() + ": «" + inventExcuse(dish) + "»");
        setMood(Mood.CAPRICIOUS);
        return false;
    }

    public Dish currentWish() {
        return wishes.getFirst();
    }

    public Nurse favourite() {
        Nurse best = null;
        int max = 0;
        for (Map.Entry<Nurse, Integer> e : attraction.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                best = e.getKey();
            }
        }
        return best;
    }

    public boolean isInLove() {
        for (int value : attraction.values()) {
            if (value >= LOVE_THRESHOLD) return true;
        }
        return false;
    }

    public boolean isRecovered() {
        return health >= RECOVERY_HEALTH;
    }

    private Dish inventWish() {
        DishType[] types = DishType.values();
        double total = 0;
        for (DishType t : types) total += t.weight(weather);
        double r = random.nextDouble() * total;
        DishType type = types[types.length - 1];
        for (DishType t : types) {
            r -= t.weight(weather);
            if (r <= 0) {
                type = t;
                break;
            }
        }

        Ingredient[] ingredients = Ingredient.values();
        Ingredient ingredient;
        do {
            ingredient = ingredients[random.nextInt(ingredients.length)];
        } while (!ingredient.isSweet() && random.nextBoolean());

        return new Dish(type, ingredient);
    }

    private String inventExcuse(Dish dish) {
        return switch (random.nextInt(3)) {
            case 0 -> "Я просил " + currentWish() + "!";
            case 1 -> {
                String title = dish.type().getTitle();
                yield Character.toUpperCase(title.charAt(0)) + title.substring(1) + " воняет луком!";
            }
            default -> dish.ingredient().isSweet() ? "Фу, слишком сладко!" : "А где сладкое?";
        };
    }

    @Override
    public String toString() {
        return "Patient{name='" + getName() + "', mood=" + getMood() + ", health=" + health
                + String.format(", capriciousness=%.2f", capriciousness) + ", attraction=" + attraction.size() + "}";
    }
}
