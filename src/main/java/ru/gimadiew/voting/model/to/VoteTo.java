package ru.gimadiew.voting.model.to;

import java.time.LocalDate;

public class VoteTo extends BaseTo {

    private LocalDate votingDate;

    private String restaurantName;

    private Integer restaurantId;

    public VoteTo() {
    }

    public VoteTo(Integer id, Integer restaurantId, String restaurantName, LocalDate votingDate) {
        super(id);
        this.votingDate = votingDate;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
