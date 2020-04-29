package ru.gimadiew.voting.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.repository.DishRepository;
import ru.gimadiew.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DishRepository dishRepository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        return checkNotFoundWithId(restaurantRepository.getWithMenu(id, LocalDate.now().atStartOfDay()), id);
    }

    @GetMapping("/{id}/menu")
    public List<Dish> getMenu(@PathVariable int id) {
        return dishRepository.getMenu(id, LocalDate.now().atStartOfDay());
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(LocalDate.now().atStartOfDay());
    }

    @GetMapping("/votes")
    public List<Restaurant> getAllWithVotes() {
        return restaurantRepository.findAllWithVotes(LocalDate.now().atStartOfDay());
    }
}
