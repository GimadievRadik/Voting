package ru.gimadiew.voting.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.model.to.DishTo;
import ru.gimadiew.voting.model.to.MenuTo;
import ru.gimadiew.voting.model.to.RestaurantTo;
import ru.gimadiew.voting.repository.DishRepository;
import ru.gimadiew.voting.repository.RestaurantRepository;
import ru.gimadiew.voting.util.DishUtil;
import ru.gimadiew.voting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static ru.gimadiew.voting.util.ValidationUtil.assureIdConsistent;
import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController extends AbstractUserController {

    static final String REST_URL = "/rest/admin";

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;

    @GetMapping("/users")
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/users/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody User user) {
        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        super.update(user, id);
    }

    @GetMapping("/users/by")
    public User getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }



    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> create(@RequestParam String name) {
        log.info("create restaurant with name {}", name);
        Restaurant created = restaurantRepository.save(new Restaurant(name));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/restaurants/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Dish>> createMenu(@PathVariable int id, @Valid @RequestBody MenuTo menuTo) {
        log.info("create menu for restaurant id = {}", id);
        List<Dish> created = menuTo.getMenu().stream()
                .peek(ValidationUtil::checkNew)
                .map(DishUtil::createFromTo)
                .map(d -> saveDish(d, id))
                .collect(Collectors.toList());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}/menu")
                .buildAndExpand(id).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurants/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@PathVariable int restaurantId, @PathVariable int dishId, @Valid @RequestBody DishTo dishTo) {
        log.info("update dish, id = {}, restaurantId = {}", dishId, restaurantId);
        assureIdConsistent(dishTo, dishId);
        Dish dish = checkNotFoundWithId(dishRepository.findById(dishId).orElse(null), dishId);
        DishUtil.updateFromTo(dish, dishTo);
        saveDish(dish, restaurantId);
    }

    @PutMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@PathVariable int id, @Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("update restaurant id = {}", id);
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id), id);
        restaurant.setName(restaurantTo.getName());
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable int id) {
        log.info("delete restaurant id = {}", id);
        restaurantRepository.deleteById(id);
    }

    @DeleteMapping("/restaurants/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("delete dish with id = {} for restaurant id = {}", dishId, restaurantId);
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