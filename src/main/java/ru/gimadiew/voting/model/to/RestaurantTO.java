package ru.gimadiew.voting.model.to;

import org.springframework.util.CollectionUtils;
import ru.gimadiew.voting.model.Dish;

import java.util.*;

public class RestaurantTO extends BaseTo {
    
    private String name;
    
    private Integer rating;

//    private List<Dish> menu;

    public RestaurantTO(Integer id, String name, Integer rating) {
        super(id);
        this.name = name;
        this.rating = rating;
//        setMenu(dishes);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

//    public List<Dish> getMenu() {
//        return menu;
//    }
//
//    public void setMenu(Collection<Dish> dishes) {
//        this.menu = CollectionUtils.isEmpty(dishes) ? Collections.emptyList() : Collections.cop;
//    }

}
