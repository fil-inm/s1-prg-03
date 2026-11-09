package hospital;

import exceptions.ImpossibleDishException;
import food.Dish;

public class Kitchen implements Hospital.NewDayListener {
    private int cookedToday;

    public Dish cook(Dish order) throws ImpossibleDishException {
        if (!order.isPossible()) {
            throw new ImpossibleDishException(order);
        }
        cookedToday++;
        return order;
    }

    @Override
    public void onNewDay() {
        cookedToday = 0;
    }

    public int getCookedToday() {
        return cookedToday;
    }

    @Override
    public String toString() {
        return "Kitchen{cookedToday=" + cookedToday + "}";
    }
}
