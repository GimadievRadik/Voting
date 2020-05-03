package ru.gimadiew.voting.model.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.gimadiew.voting.model.Vote;

import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime votingDateTime;

    private String restaurantName;

    private Integer restaurantId;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDateTime votingDateTime, Integer restaurantId, String restaurantName) {
        super(id);
        this.votingDateTime = votingDateTime;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public LocalDateTime getVotingDateTime() {
        return votingDateTime;
    }

    public void setVotingDateTime(LocalDateTime votingDateTime) {
        this.votingDateTime = votingDateTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
