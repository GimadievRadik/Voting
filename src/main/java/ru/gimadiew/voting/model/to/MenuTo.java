package ru.gimadiew.voting.model.to;

import javax.validation.Valid;
import java.util.List;

public class MenuTo {

    @Valid
    private List<DishTo> menu;

    public List<DishTo> getMenu() {
        return menu;
    }

    public MenuTo() {
    }

}
