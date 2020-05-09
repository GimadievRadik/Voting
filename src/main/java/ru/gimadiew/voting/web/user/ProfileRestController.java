package ru.gimadiew.voting.web.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.model.to.VoteTo;
import ru.gimadiew.voting.service.UserService;
import ru.gimadiew.voting.service.VoteService;
import ru.gimadiew.voting.util.VoteUtil;
import ru.gimadiew.voting.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.gimadiew.voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController extends AbstractUserController {

    static final String REST_URL = "/rest/profile";

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @GetMapping
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

    @GetMapping(value = "/votes")
    public List<VoteTo> getVotingHistory(@RequestParam @Nullable LocalDate date) {
        int id = SecurityUtil.authUserId();
        log.info("getVotingHistory for user {}, date filter is {}", id, date);
        userService.get(id);
        return VoteUtil.getTos(voteService.getVotingHistory(id, date));
    }
}