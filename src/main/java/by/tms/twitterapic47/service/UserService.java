package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.entity.UserStatus;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userStorage;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public User save(User user) {
        if (userStorage.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exist!");
        } else {
            user.setStatus(UserStatus.ACTIVE);
            return userStorage.save(user);
        }
    }

    public User update(String username, User user) {
        User byUsername = userStorage.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.setId(byUsername.getId());
        user.setStatus(byUsername.getStatus());
        return userStorage.save(user);
    }

    @Transactional
    public User delete(String username) {
        User user = userStorage.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        postRepository.deleteAllByCreatorUsername(user.getUsername());
        commentRepository.deleteAllByCreatorUsername(user.getUsername());
        userStorage.delete(user);
        return user;
    }

    public String follow(String username, String followUsername) {
        User user = userStorage.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        User followUser = userStorage.findByUsername(followUsername).orElseThrow(() -> new RuntimeException("Follower not found"));
        if (user.getSubscriptions().contains(followUser.getUsername())) {
            throw new RuntimeException("Subscribe already exist");
        } else {
            user.getSubscriptions().add(followUser.getUsername());
            userStorage.save(user);
            return followUsername;
        }
    }

    public String unfollow(String username, String unfollowUsername) {
        User byUsername = userStorage.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        User unfollow = userStorage.findByUsername(unfollowUsername).orElseThrow(() -> new RuntimeException("Subscription not found"));
        if (byUsername.getSubscriptions().remove(unfollow.getUsername())) {
            userStorage.save(byUsername);
            return unfollowUsername;
        } else {
            throw new RuntimeException("Subscription not found");
        }
    }

    public List<String> getAllSubscriptions(String username) {
        User user = userStorage.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getSubscriptions().isEmpty()) {
            throw new RuntimeException("Subscription not found");
        } else {
            return user.getSubscriptions();
        }
    }
}
