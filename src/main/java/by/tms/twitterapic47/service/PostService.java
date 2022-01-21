package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Post save(Post post, String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        post.setCreatorUsername(byUsername.getUsername());
        post.setDateCreating(setDate());
        return postRepository.save(post);
    }

    @Transactional
    public Post delete(long postId) {
        Post postById = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        commentRepository.deleteAllByPostId(postById.getId());
        postRepository.delete(postById);
        return postById;
    }

    public Post update(Post updatedPost, long postId) {
        Post postById = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (postById.getCreatorUsername().equals(updatedPost.getCreatorUsername())) {
            postById.setTitle(updatedPost.getTitle());
            postById.setDescription(updatedPost.getDescription());
            postById.setDateCreating(setDate());
            return postRepository.save(postById);
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public List<Post> getPosts(String username) {
        return postRepository.findAllByCreatorUsername(username).orElseThrow(() -> new RuntimeException("List posts are empty"));
    }

    public List<Post> getFollowersPosts(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Post> postList = new ArrayList<>();
        for (String subscription : user.getSubscriptions()) {
            Optional<List<Post>> currentUserSubscription = postRepository.findAllByCreatorUsername(subscription);
            currentUserSubscription.ifPresent(postList::addAll);
        }
        if (postList.isEmpty()) {
            throw new RuntimeException("List posts are empty");
        } else {
            return postList;
        }
    }

    private String setDate() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(
                "dd.MM.yyyy hh:mm");
        return format1.format(date);
    }
}
