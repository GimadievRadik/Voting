package ru.gimadiew.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "DISH", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "description", "date_time"}, name = "dish_unique_rest_description_date_idx")})
public class Dish extends AbstractBaseEntity {

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    private Integer price;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonIgnore
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String description, Integer price, LocalDateTime dateTime) {
        super(id);
        this.description = description;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
