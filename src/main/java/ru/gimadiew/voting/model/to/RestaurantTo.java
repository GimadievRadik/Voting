package ru.gimadiew.voting.model.to;

public class RestaurantTo extends BaseTo {
    
    private String name;
    
    private Integer rating;

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

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
