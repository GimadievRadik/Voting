package ru.gimadiew.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.to.DishTo;
import ru.gimadiew.voting.model.to.MenuTo;
import ru.gimadiew.voting.repository.DishRepository;
import ru.gimadiew.voting.repository.RestaurantRepository;
import ru.gimadiew.voting.util.DishUtil;
import ru.gimadiew.voting.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.gimadiew.voting.util.ValidationUtil.assureIdConsistent;
import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Dish> getMenu(int restaurantId, LocalDate date) {
        return dishRepository.getMenu(restaurantId, LocalDate.now());
    }

    public List<Dish> getDishesAfterDate(int restaurantId, LocalDate date) {
        LocalDate startDate = date == null ? LocalDate.of(2000, 1, 1) : date;
        return dishRepository.getMenu(restaurantId, startDate);
    }

    public List<Dish> createDishes(MenuTo menuTo, int restaurantId) {
        return menuTo.getMenu().stream()
                .peek(ValidationUtil::checkNew)
                .map(DishUtil::createFromTo)
                .map(d -> saveDish(d, restaurantId))
                .collect(Collectors.toList());
    }

    public void update(DishTo dishTo, int dishId, int restaurantId) {
        assureIdConsistent(dishTo, dishId);
        Dish dish = checkNotFoundWithId(dishRepository.findById(dishId).orElse(null), dishId);
        DishUtil.updateFromTo(dish, dishTo);
        saveDish(dish, restaurantId);
    }

    public void delete(int restaurantId, int dishId) {
        dishRepository.deleteById(dishId, restaurantId);
    }

    private Dish saveDish(Dish dish, int restaurantId) {
        if (!dish.isNew() && getDish(dish.id(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    private Dish getDish(int id, int restaurantId) {
        return dishRepository.findById(id).filter(dish -> dish.getRestaurant().getId() == restaurantId).orElse(null);
    }
}
