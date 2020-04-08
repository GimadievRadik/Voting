package ru.gimadiew.voting.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.repository.DataJpaUserRepository;
import ru.gimadiew.voting.repository.UserRepository;

import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.assureIdConsistent;
import static ru.gimadiew.voting.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataJpaUserRepository userRepository;

    public List<User> getAll() {
        log.info("getAll");
        return userRepository.findAll();
    }
//
//    public User get(int id) {
//        log.info("get {}", id);
//        return userRepository.get(id);
//    }
//
//    public User create(User user) {
//        log.info("create {}", user);
//        checkNew(user);
//        return userRepository.save(user);
//    }
//
//    public void delete(int id) {
//        log.info("delete {}", id);
//        userRepository.delete(id);
//    }
//
//    public void update(User user, int id) {
//        log.info("update {} with id={}", user, id);
//        assureIdConsistent(user, id);
//        userRepository.update(user);
//    }
//
//    public User getByMail(String email) {
//        log.info("getByEmail {}", email);
//        return userRepository.getByEmail(email);
//    }
}