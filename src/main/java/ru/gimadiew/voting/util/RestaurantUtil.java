package ru.gimadiew.voting.util;

import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static List<RestaurantTo> getRestTos(Collection<Restaurant> restaurants, Collection<Vote> votes) {
        return addVotes(restaurants, votes);
    }

    public static List<RestaurantTo> addVotes(Collection<Restaurant> restaurants, Collection<Vote> votes) {
        Map<Restaurant, Long> votesSumByRest = votes.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        return restaurants.stream().map(r -> createTo(r, votesSumByRest)).collect(Collectors.toList());
    }

    public static RestaurantTo createTo(Restaurant restaurant, Map<Restaurant, Long> ratingMap) {
        Integer rating = (Optional.ofNullable(ratingMap.get(restaurant)).orElse(0L)).intValue();
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), rating);
    }
}
