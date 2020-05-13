package ru.gimadiew.voting;

import ru.gimadiew.voting.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId() {
        return user.id();
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
