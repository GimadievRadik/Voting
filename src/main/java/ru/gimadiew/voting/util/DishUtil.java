package ru.gimadiew.voting.util;

import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.to.DishTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {

    public static Dish createFromTo(DishTo dTo) {
        return new Dish(dTo.getDescription(), formatPrice(dTo.getPrice()));
    }

    public static List<Dish> getListFromTos(List<DishTo> dTos) {
        return dTos.stream().map(DishUtil::createFromTo).collect(Collectors.toList());
    }

    public static Dish updateFromTo(Dish dish, DishTo dTo) {
        dish.setDescription(dTo.getDescription());
        dish.setPrice(formatPrice(dTo.getPrice()));
        return dish;
    }

    public static Integer formatPrice(Float price) {
        return Float.valueOf(price * 100).intValue();
    }
}
