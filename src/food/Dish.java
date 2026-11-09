package food;

import java.util.Objects;
import java.util.Random;

public record Dish(DishType type, Ingredient ingredient) {

    public Dish {
        Objects.requireNonNull(type);
        Objects.requireNonNull(ingredient);
    }

    public static Dish random(Random r) {
        DishType[] types = DishType.values();
        Ingredient[] ingredients = Ingredient.values();
        return new Dish(types[r.nextInt(types.length)], ingredients[r.nextInt(ingredients.length)]);
    }

    public boolean isPossible() {
        return !type.needsSolidBase() || ingredient.isSolid();
    }

    public boolean sameTypeAs(Dish other) {
        return other != null && type == other.type;
    }

    @Override
    public String toString() {
        return type.getTitle() + " из " + ingredient.getTitle();
    }
}
