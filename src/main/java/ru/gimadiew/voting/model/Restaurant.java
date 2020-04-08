package ru.gimadiew.voting.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "RESTAURANT", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "rest_unique_name")})
public class Restaurant extends AbstractNamedEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Vote> votes;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? Collections.emptyList() : new ArrayList<>(dishes);
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
