package ru.gimadiew.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gimadiew.voting.model.User;

@Repository
public interface DataJpaUserRepository extends JpaRepository<User, Integer> {

}
