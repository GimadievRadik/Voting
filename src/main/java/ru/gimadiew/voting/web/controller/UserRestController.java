package ru.gimadiew.voting.web.controller;


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
import ru.gimadiew.voting.util.VoteUtil;
import ru.gimadiew.voting.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    static final String REST_URL = "/rest/user";

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishService dishService;
    @Autowired
    VoteService voteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/votes")
    public List<VoteTo> getVotingHistory(@RequestParam @Nullable LocalDate date) {
        int id = SecurityUtil.authUserId();
        log.info("getVotingHistory for user {}, date filter is {}", id, date);
        return VoteUtil.getTos(voteService.getVotingHistory(id, date));
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant id = {}", id);
        return restaurantService.get(id);
    }

    @GetMapping("/restaurants/{id}/dishes")
    public List<Dish> getMenu(@PathVariable int id) {
        log.info("get menu for restaurant with id = {}", id);
        return dishService.getMenu(id, LocalDate.now());
    }

    @GetMapping("/restaurants/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu, id = {}", id);
        return restaurantService.getWithMenu(id);
    }

    @GetMapping("/restaurants/{id}/dishes/history")
    public List<Dish> getDishesAfterDate(@PathVariable int id, @RequestParam @Nullable LocalDate date) {
        log.info("get menus history for restaurant id = {} from date {}", id, date);
        return dishService.getDishesAfterDate(id, date);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllWithMenu() {
        log.info("get all restaurants menu");
        return restaurantService.getAllWithMenu();
    }

    @GetMapping("/restaurants/votes")
    public List<RestaurantTo> getRatings() {
        log.info("get restaurants rating");
        return restaurantService.getRatings();
    }

    @PostMapping("/restaurants/{id}/votes")
    public ResponseEntity<VoteTo> addVote(@PathVariable int id) {
        VoteTo vTo = voteService.addVote(id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/votes/{id}")
                .buildAndExpand(vTo.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vTo);
    }

    @PutMapping(value = "/restaurants/{id}/votes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable int id) {
        voteService.updateVote(id);
    }
}