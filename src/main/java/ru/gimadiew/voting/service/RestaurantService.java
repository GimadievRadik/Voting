package ru.gimadiew.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import static ru.gimadiew.voting.util.ValidationUtil.assureIdConsistent;
import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    VoteRepository voteRepository;

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(restaurantRepository.findWithMenu(id, LocalDate.now()), id);
    }

    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.findAllWithMenu(LocalDate.now()); //TODO without date
    }

    public List<RestaurantTo> getRatings() {
        List<Vote> todayVotes = voteRepository.getVotes(LocalDate.now().atStartOfDay());
        List<RestaurantTo> result = RestaurantUtil.getRestTos(getAllWithMenu(), todayVotes);
        result.sort(Comparator.comparing(RestaurantTo::getRating).reversed());
        return result;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void update(int id, RestaurantTo restaurantTo) {
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id), id);
        restaurant.setName(restaurantTo.getName());
        restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }




}
