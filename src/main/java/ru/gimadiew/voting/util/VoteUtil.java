package ru.gimadiew.voting.util;

import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    public static VoteTo asTo(Vote v) {
        return new VoteTo(v.getId(), v.getRestaurant().getId(), v.getRestaurant().getName(), v.getVotingDateTime());
    }

    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream().map(VoteUtil::asTo).collect(Collectors.toList());
    }

}
