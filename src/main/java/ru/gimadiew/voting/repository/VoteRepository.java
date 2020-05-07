package ru.gimadiew.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gimadiew.voting.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id = ?1 AND v.votingDateTime >= ?2 ORDER BY v.votingDateTime DESC")
    List<Vote> getAfterByUserId(int id, LocalDateTime dateTime);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.votingDateTime >= ?1")
    List<Vote> getVotes(LocalDateTime dateTime);
}
