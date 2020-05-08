package ru.gimadiew.voting.web.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.RestaurantTo;
import ru.gimadiew.voting.model.to.VoteTo;
import ru.gimadiew.voting.repository.DishRepository;
import ru.gimadiew.voting.repository.RestaurantRepository;
import ru.gimadiew.voting.repository.UserRepository;
import ru.gimadiew.voting.repository.VoteRepository;
import ru.gimadiew.voting.util.RestaurantUtil;
import ru.gimadiew.voting.util.VoteUtil;
import ru.gimadiew.voting.util.exception.VotingException;
import ru.gimadiew.voting.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @GetMapping("/{id}")
    public Restaurant getDish(@PathVariable int id) {
        log.info("get restaurant id = {}", id);
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    @GetMapping("/{id}/dishes")
    public List<Dish> getMenu(@PathVariable int id) {
        log.info("get menu for restaurant with id = {}", id);
        return dishRepository.getMenu(id, LocalDate.now());
    }

    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        log.info("get restaurant with menu, id = {}", id);
        return checkNotFoundWithId(restaurantRepository.findWithMenu(id, LocalDate.now()), id);
    }

    @GetMapping("/{id}/dishes/history")
    public List<Dish> getDishesAfterDate(@PathVariable int id, @RequestParam @Nullable LocalDate date) {
        LocalDate startDate = date == null ? LocalDate.of(2000, 1, 1) : date;
        log.info("get menus history for restaurant id = {} from date {}", id, startDate);
        return dishRepository.getMenu(id, startDate);
    }

    @GetMapping
    public List<Restaurant> getAllWithMenu() {
        log.info("get all restaurants menu");
        return restaurantRepository.findAllWithMenu(LocalDate.now());
    }

    @GetMapping("/votes")
    public List<RestaurantTo> getRatings() {
        log.info("get restaurants rating");
        List<Vote> todayVotes = voteRepository.getVotes(LocalDate.now().atStartOfDay());
        List<Restaurant> restaurants = getAllWithMenu();
        List<RestaurantTo> result = RestaurantUtil.getRestTos(restaurants, todayVotes);
        result.sort(Comparator.comparing(RestaurantTo::getRating).reversed());
        return result;
    }

    @PostMapping("/{id}/votes")
    public ResponseEntity<VoteTo> addVote(@PathVariable int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id), id);
        User user = userRepository.getOne(SecurityUtil.authUserId());
        List<Vote> votes = voteRepository.getAfterByUserId(SecurityUtil.authUserId(), LocalDate.now().atStartOfDay());
        Vote saved = saveVote(prepareVotes(votes, restaurant, user));
        VoteTo vTo = VoteUtil.asTo(saved);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/votes/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vTo);
    }

    @PutMapping(value = "/{id}/votes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id), id);
        List<Vote> votes = voteRepository.getAfterByUserId(SecurityUtil.authUserId(), LocalDate.now().atStartOfDay());
        saveVote(prepareVotes(votes, restaurant, userRepository.getOne(SecurityUtil.authUserId())));
    }

    private Vote prepareVotes(List<Vote> votes, Restaurant restaurant, User user) {
        if (!votes.isEmpty() && LocalDateTime.now().isAfter(LocalDate.now().atStartOfDay().plusHours(11))) {
            throw new VotingException("More than 11 hours, to late to change your vote");
        }
        Vote vote = votes.isEmpty() ? new Vote() : votes.get(0);
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        return vote;
    }

    private Vote saveVote(Vote vote) {
        if (!vote.isNew()) {
            vote.setVotingDateTime(LocalDateTime.now());
        }
        return voteRepository.save(vote);
    }
}
