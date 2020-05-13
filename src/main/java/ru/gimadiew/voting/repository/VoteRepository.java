package ru.gimadiew.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gimadiew.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id = ?1 AND v.votingDate >= ?2 ORDER BY v.votingDate DESC")
    List<Vote> getAfterByUserId(int id, LocalDate date);

    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.votingDate >= ?1")
    List<Vote> getVotes(LocalDate date);
}
