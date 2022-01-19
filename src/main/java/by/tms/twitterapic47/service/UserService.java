package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userStorage;

    public User save(User user) {
        if (userStorage.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exist!");
        } else {
            return userStorage.save(user);
        }
    }

    public User update(String username, User user) {
        User byUsername = userStorage.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        user.setId(byUsername.getId());
        return userStorage.save(user);
    }

    public User delete(String username) {
        Optional<User> user = userStorage.deleteByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found!");
        }
    }

    public String follow(String username, String followUsername) {
        User byUsername = userStorage.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        User followUser = userStorage.findByUsername(followUsername).orElseThrow(()->new RuntimeException("Follower not found"));
        if (byUsername.getSubscriptions().contains(followUser)) {
            throw new RuntimeException("Subscribe already exist");
        } else {
            byUsername.getSubscriptions().add(followUser);
            userStorage.save(byUsername);
            return followUsername;
        }
    }

    public String unfollow(String username, String unfollowUsername) {
        User byUsername = userStorage.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        User unfollow = userStorage.findByUsername(unfollowUsername).orElseThrow(()->new RuntimeException("Subscription not found"));
        if (byUsername.getSubscriptions().remove(unfollow)) {
            userStorage.save(byUsername);
            return unfollowUsername;
        } else {
            throw new RuntimeException("Subscription not found");
        }
    }
}
