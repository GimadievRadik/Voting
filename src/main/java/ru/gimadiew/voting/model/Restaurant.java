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
    private Set<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    public Set<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Collection<Dish> dishes) {
        this.menu = CollectionUtils.isEmpty(dishes) ? Collections.emptySet() : new HashSet<>(dishes);
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = CollectionUtils.isEmpty(votes) ? Collections.emptySet() : new HashSet<>(votes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
