package ru.gimadiew.voting.model.to;

import java.time.LocalDateTime;

public class VoteTo extends BaseTo {

    private LocalDateTime votingDateTime;

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
}
