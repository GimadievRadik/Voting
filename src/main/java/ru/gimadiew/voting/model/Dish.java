package ru.gimadiew.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "DISH", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "description", "date"}, name = "dish_unique_rest_description_date_idx")})
public class Dish extends AbstractBaseEntity {

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    private Integer price;

    @Column(nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rest_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String description, Integer price, LocalDate date) {
        super(id);
        this.description = description;
        this.price = price;
        this.date = date;
    }
}
