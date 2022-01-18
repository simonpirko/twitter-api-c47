package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userStorage;

    public User create(User user) {
        if (userStorage.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exist!");
        } else {
            return userStorage.save(user);
        }
    }

    public User updateUser(User user) {
        if (userStorage.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User not found!");
        } else {
            return userStorage.save(user);
        }
    }

    public User deleteUser(String username) {
        Optional<User> user = userStorage.deleteByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    public String follow(String username, String followUsername) {
        Optional<User> byUsername = userStorage.findByUsername(username);
        Optional<User> byFollowUsername = userStorage.findByUsername(followUsername);
        if (byUsername.isPresent() && byFollowUsername.isPresent()) {
            List<User> subscriptions = byUsername.get().getSubscriptions();
            subscriptions.add(byFollowUsername.get());
            byUsername.get().setSubscriptions(subscriptions);
            userStorage.save(byUsername.get());
            return byFollowUsername.get().getUsername();
        } else {
            throw new RuntimeException("User not found!");
        }
    }
}
