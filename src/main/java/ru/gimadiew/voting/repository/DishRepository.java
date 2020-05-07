package ru.gimadiew.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gimadiew.voting.model.Dish;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = ?1 AND d.dateTime >= ?2 ORDER BY d.dateTime DESC")
    List<Dish> getMenu(int id, LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id = ?1 AND d.restaurant.id = ?2")
    void deleteById(int dishId, int restaurantId);

}
