package ru.gimadiew.voting.model.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    
    private Integer rating;

    public RestaurantTo() {}

    public RestaurantTo(Integer id, String name, Integer rating) {
        super(id);
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

}
