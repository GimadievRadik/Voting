package ru.gimadiew.voting.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.to.RestaurantTo;
import ru.gimadiew.voting.model.to.VoteTo;
import ru.gimadiew.voting.service.DishService;
import ru.gimadiew.voting.service.RestaurantService;
import ru.gimadiew.voting.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishService dishService;
    @Autowired
    VoteService voteService;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant id = {}", id);
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/dishes")
    public List<Dish> getMenu(@PathVariable int id) {
        log.info("get menu for restaurant with id = {}", id);
        return dishService.getMenu(id, LocalDate.now());
    }

    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu, id = {}", id);
        return restaurantService.getWithMenu(id);
    }

    @GetMapping("/{id}/dishes/history")
    public List<Dish> getDishesAfterDate(@PathVariable int id, @RequestParam @Nullable LocalDate date) {
        log.info("get menus history for restaurant id = {} from date {}", id, date);
        return dishService.getDishesAfterDate(id, date);
    }

    @GetMapping
    public List<Restaurant> getAllWithMenu() {
        log.info("get all restaurants menu");
        return restaurantService.getAllWithMenu();
    }

    @GetMapping("/votes")
    public List<RestaurantTo> getRatings() {
        log.info("get restaurants rating");
        return restaurantService.getRatings();
    }

    @PostMapping("/{id}/votes")
    public ResponseEntity<VoteTo> addVote(@PathVariable int id) {
        VoteTo vTo = voteService.addVote(id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/votes/{id}")
                .buildAndExpand(vTo.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vTo);
    }

    @PutMapping(value = "/{id}/votes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable int id) {
        voteService.updateVote(id);
    }
}
