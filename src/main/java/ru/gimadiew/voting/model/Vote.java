package ru.gimadiew.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "VOTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "vote_unique_user_date_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime votingDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    public Vote() {
        super();
        votingDateTime = LocalDateTime.now();
    }

    public Vote(User user, Restaurant restaurant) {
        this();
        this.user = user;
        this.restaurant = restaurant;

    }

    public LocalDateTime getVotingDateTime() {
        return votingDateTime;
    }

    public void setVotingDateTime(LocalDateTime votingDateTime) {
        this.votingDateTime = votingDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
