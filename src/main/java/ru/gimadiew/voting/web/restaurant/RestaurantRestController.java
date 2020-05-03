package ru.gimadiew.voting.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.RestaurantTo;
import ru.gimadiew.voting.repository.DishRepository;
import ru.gimadiew.voting.repository.RestaurantRepository;
import ru.gimadiew.voting.repository.VoteRepository;
import ru.gimadiew.voting.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.Comparator;
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

    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    @GetMapping("/{id}/menu")
    public List<Dish> getMenu(@PathVariable int id) {
        return dishRepository.getMenu(id, LocalDate.now().atStartOfDay());
    }

    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        return checkNotFoundWithId(restaurantRepository.findWithMenu(id, LocalDate.now().atStartOfDay()), id);
    }

    @GetMapping
    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.findAllWithMenu(LocalDate.now().atStartOfDay());
    }

    @GetMapping("/ratings")
    public List<RestaurantTo> getRatings() {
        List<Vote> todayVotes = voteRepository.getVotes(LocalDate.now().atStartOfDay());
        List<Restaurant> restaurants = getAllWithMenu();
        List<RestaurantTo> result = RestaurantUtil.getRestTos(restaurants, todayVotes);
        result.sort(Comparator.comparing(RestaurantTo::getRating).reversed());
        return result;
    }

}
