package ru.gimadiew.voting.web.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.model.Vote;
import ru.gimadiew.voting.model.to.VoteTo;
import ru.gimadiew.voting.repository.UserRepository;
import ru.gimadiew.voting.repository.VoteRepository;
import ru.gimadiew.voting.util.VoteUtil;
import ru.gimadiew.voting.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.gimadiew.voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {

    static final String REST_URL = "/rest/profile";

    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        super.update(user, authUserId());
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }

    @GetMapping(value = "/votes")
    public List<VoteTo> getVotingHistory(@RequestParam @Nullable LocalDate date) {
        LocalDateTime dateTime = date == null ? LocalDateTime.of(2000, 1, 1, 0, 0) : date.atStartOfDay();
        int id = SecurityUtil.authUserId();
        checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
        log.info("getVotingHistory for user {}, date filter is {}", id, dateTime);
        List<Vote> votes = voteRepository.getAfterByUserId(id, dateTime);
        return VoteUtil.getTos(votes);
    }
}