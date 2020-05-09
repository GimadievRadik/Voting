package ru.gimadiew.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gimadiew.voting.model.Restaurant;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.VoteTo;
import ru.gimadiew.voting.repository.RestaurantRepository;
import ru.gimadiew.voting.repository.UserRepository;
import ru.gimadiew.voting.repository.VoteRepository;
import ru.gimadiew.voting.util.VoteUtil;
import ru.gimadiew.voting.util.exception.VotingException;
import ru.gimadiew.voting.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    UserRepository userRepository;

    public List<Vote> getVotingHistory(Integer id, LocalDate date) {
        LocalDateTime dateTime = date == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : date.atStartOfDay();
        return voteRepository.getAfterByUserId(id, dateTime);
    }

    public VoteTo addVote(int restaurantId) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId), restaurantId);
        User user = userRepository.getOne(SecurityUtil.authUserId());
        List<Vote> votes = voteRepository.getAfterByUserId(SecurityUtil.authUserId(), LocalDate.now().atStartOfDay());
        Vote saved = saveVote(prepareVotes(votes, restaurant, user));
        return VoteUtil.asTo(saved);
    }

    public void updateVote(int restaurantId) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId), restaurantId);
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
