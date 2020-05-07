package ru.gimadiew.voting.model.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

    private LocalDateTime votingDateTime;

    @Size(min = 2, max = 100)
    @NotBlank
    private String restaurantName;

    private Integer restaurantId;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restaurantId, String restaurantName, LocalDateTime votingDateTime) {
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
