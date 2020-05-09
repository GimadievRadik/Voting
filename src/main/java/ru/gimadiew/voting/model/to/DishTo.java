package ru.gimadiew.voting.model.to;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class DishTo extends BaseTo {

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private Float price;

    @ConstructorProperties({"description", "price"})
    public DishTo(String description, Float price) {
        super(null);
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }
}
