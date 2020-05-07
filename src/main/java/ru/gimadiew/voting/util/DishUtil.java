package ru.gimadiew.voting.util;

import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.to.DishTo;

import static ru.gimadiew.voting.model.Dish.*;

public class DishUtil {

    public static Dish createFromTo(DishTo dTo) {
        return new Dish(dTo.getDescription(), formatPrice(dTo.getPrice()));
    }

    public static Dish updateFromTo(Dish dish, DishTo dTo) {
        dish.setDescription(dTo.getDescription());
        dish.setPrice(formatPrice(dTo.getPrice()));
        return dish;
    }

    public static Integer formatPrice(Float price) {
        return Float.valueOf(price * CURRENCY_MULTIPLIER).intValue();
    }
}
