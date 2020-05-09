package ru.gimadiew.voting.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.service.UserService;

import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.assureIdConsistent;
import static ru.gimadiew.voting.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get user, id = {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete user id = {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        Assert.notNull(user, "user must not be null");
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, "email must not be null");
        return service.getByEmail(email);
    }
}