package ru.gimadiew.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.gimadiew.voting.web.json.DishSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "DISH", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "description", "DATE"}, name = "dish_unique_rest_description_date_idx")})
@JsonSerialize(using = DishSerializer.class)
public class Dish extends AbstractBaseEntity {

    public static final Float CURRENCY_DIVIDER = 100.0f;
    public static final Integer CURRENCY_MULTIPLIER = 100;

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String description, Integer price) {
        this(null, description, price, LocalDate.now());
    }

    public Dish(Integer id, String description, Integer price, LocalDate date) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", dateTime=" + date +
                ", id=" + id +
                '}';
    }
}
