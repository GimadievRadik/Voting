package ru.gimadiew.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Vote> votes;

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Collection<Dish> dishes) {
        this.menu = CollectionUtils.isEmpty(dishes) ? Collections.emptyList() : new ArrayList<>(dishes);
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
