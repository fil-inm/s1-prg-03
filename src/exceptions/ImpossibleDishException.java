package exceptions;

import food.Dish;

public class ImpossibleDishException  extends Exception{
    private final Dish dish;

    public ImpossibleDishException(Dish dish) {
        this.dish = dish;
    }

    @Override
    public String getMessage() {
        return "каждому известно, что " + dish + " не бывает";
    }
}
