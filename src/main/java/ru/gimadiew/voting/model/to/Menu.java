package ru.gimadiew.voting.model.to;

import javax.validation.Valid;
import java.util.List;

public class Menu {

    @Valid
//    @JsonProperty
    private List<DishTo> dishTos;

    public List<DishTo> getDishTos() {
        return dishTos;
    }

    public void setDishTos(List<DishTo> dishTos) {
        this.dishTos = dishTos;
    }

    public Menu() {
    }

    public Menu(List<DishTo> dishTos) {
        this.dishTos = dishTos;
    }


}
