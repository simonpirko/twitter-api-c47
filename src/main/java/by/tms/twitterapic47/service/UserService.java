package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.entity.UserStatus;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
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
            throw new RuntimeException(String.format("User {} already exist! %s", user.getUsername()));
        } else {
            user.setStatus(UserStatus.ACTIVE);
            log.info("User {} save", user.getUsername());
            return userStorage.save(user);

        }
    }

    public User update(String username, User user) {
        log.info("Request update {}", username);
        User byUsername = userStorage.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        user.setId(byUsername.getId());
        user.setStatus(byUsername.getStatus());
        return userStorage.save(user);
    }

    @Transactional
    public User delete(String username) {
        log.info("Request delete {}", username);
        User user = userStorage.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        postRepository.deleteAllByCreatorUsername(user.getUsername());
        commentRepository.deleteAllByCreatorUsername(user.getUsername());
        userStorage.delete(user);
        return user;
    }

    public String follow(String username, String followUsername) {
        log.info(String.format("Request follow {} by %s", username, followUsername));
        User user = userStorage.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        User followUser = userStorage.findByUsername(followUsername)
                .orElseThrow(() -> new RuntimeException(String.format("Follower %s not found", username)));
        if (user.getSubscriptions().contains(followUser.getUsername())) {
            throw new RuntimeException(String.format("Subscribe %s already exist", followUser.getUsername()));
        } else {
            user.getSubscriptions().add(followUser.getUsername());
            userStorage.save(user);
            return followUsername;
        }
    }

    public String unfollow(String username, String unfollowUsername) {
        log.info(String.format("Request unfollow {} by %s", username, unfollowUsername));
        User byUsername = userStorage.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        User unfollow = userStorage.findByUsername(unfollowUsername)
                .orElseThrow(() -> new RuntimeException(String.format("Subscription %s not found", unfollowUsername)));
        if (byUsername.getSubscriptions().remove(unfollow.getUsername())) {
            userStorage.save(byUsername);
            return unfollowUsername;
        } else {
            throw new RuntimeException(String.format("Subscription %s not found", unfollow.getUsername()));
        }
    }

    public List<String> getAllSubscriptions(String username) {
        log.info(String.format("Request getAllSubscriptions by %s", username));
        User user = userStorage.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        if (user.getSubscriptions().isEmpty()) {
            throw new RuntimeException(String.format("Subscription %s empty", user.getSubscriptions()));
        } else {
            return user.getSubscriptions();
        }
    }
}
