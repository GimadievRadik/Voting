package ru.gimadiew.voting.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.repository.DataJpaUserRepository;

import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.*;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataJpaUserRepository userRepository;

    public List<User> getAll() {
        log.info("getAll");
        return userRepository.findAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return checkNotFoundWithId(userRepository.findById(id).orElse(null), id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.id());
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }
}