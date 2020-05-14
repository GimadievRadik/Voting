package ru.gimadiew.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.gimadiew.voting.HasId;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "RESTAURANT", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "rest_unique_name")})
public class Restaurant extends AbstractBaseEntity implements HasId {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    @Valid
    private Set<Dish> menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(String name) {
        super(null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
