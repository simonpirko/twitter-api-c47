package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post save(Post post, String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (byUsername.getPosts() != null) {
            byUsername.setPosts(new ArrayList<>());
        }
        byUsername.getPosts().add(post);
        userRepository.save(byUsername);
        return post;
    }

    public Post delete(long postId, String username) {
        Post postById = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (byUsername.getPosts().remove(postById)) {
            userRepository.save(byUsername);
            return postById;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public Post update(Post post, String username) {
        Post postById = postRepository.findById(post.getId()).orElseThrow(()->new RuntimeException("Post not found"));
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        if (byUsername.getPosts().stream()
                .anyMatch(x -> x.getId() == postById.getId())) {
            postRepository.save(post);
            return post;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public List<Post> getPosts(String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        List<Post> posts = postRepository.findAllByCreator(byUsername).orElseThrow(() -> new RuntimeException("List posts are empty"));
        return posts;
    }

    public List<Post> getFollowersPosts(String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        List<Post> posts = new ArrayList<>();
        for (User subscription : byUsername.getSubscriptions()) {
            posts.addAll(subscription.getPosts());
        }
        if (posts.isEmpty()) {
            throw new RuntimeException("List posts are empty");
        }
        return posts;
    }
}
