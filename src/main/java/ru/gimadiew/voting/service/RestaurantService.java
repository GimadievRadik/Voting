package ru.gimadiew.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.RestaurantTo;
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
    VoteRepository voteRepository;

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    @Cacheable("restaurants")
    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(restaurantRepository.findWithMenu(id, LocalDate.now()), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllWithMenu() {
        return restaurantRepository.findAllWithMenu(LocalDate.now());
    }

    public List<RestaurantTo> getRatings() {
        List<Vote> todayVotes = voteRepository.getVotes(LocalDate.now().atStartOfDay());
        List<RestaurantTo> result = RestaurantUtil.getRestTos(getAllWithMenu(), todayVotes);
        result.sort(Comparator.comparing(RestaurantTo::getRating).reversed());
        return result;
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(int id, RestaurantTo restaurantTo) {
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id), id);
        restaurant.setName(restaurantTo.getName());
        restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }
}
