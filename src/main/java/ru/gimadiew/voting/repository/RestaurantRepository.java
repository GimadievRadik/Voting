package ru.gimadiew.voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gimadiew.voting.model.Dish;
import ru.gimadiew.voting.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findById(int id);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE r.id = ?1 AND m.dateTime = ?2")
    Restaurant getWithMenu(int id, LocalDateTime dateTime);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE m.dateTime = ?1")
    List<Restaurant> findAll(LocalDateTime dateTime);

    @EntityGraph(attributePaths = {"menu", "votes"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu m WHERE m.dateTime = ?1")
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> findAllWithVotes(LocalDateTime dateTime);


}
