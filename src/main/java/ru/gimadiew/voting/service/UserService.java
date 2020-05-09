package ru.gimadiew.voting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gimadiew.voting.model.User;
import ru.gimadiew.voting.repository.UserRepository;

import java.util.List;

import static ru.gimadiew.voting.util.ValidationUtil.*;

@Service
public class UserService {

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Autowired
    UserRepository repository;

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(User user) {
        repository.save(user);
    }

    public User getByEmail(String email) {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }
}
