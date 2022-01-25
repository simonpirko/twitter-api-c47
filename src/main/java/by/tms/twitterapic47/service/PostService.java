package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Post save(Post post, String username) {
        log.info(String.format("Request Post %s save by %s", post.getDescription(), username));
        User byUsername = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        post.setCreatorUsername(byUsername.getUsername());
        return postRepository.save(post);
    }

    @Transactional
    public Post delete(long postId) {
        log.info(String.format("Request delete Post by id%s", postId));
        Post postById = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(String.format("Post id%s not found", postId)));
        commentRepository.deleteAllByPostId(postById.getId());
        postRepository.delete(postById);
        return postById;
    }

    public Post update(Post updatedPost, long postId) {
        log.info(String.format("Request update Post by id%s", postId));
        Post postById = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(String.format("Post id%s not found", postId)));
        if (postById.getCreatorUsername().equals(updatedPost.getCreatorUsername())) {
            postById.setTitle(updatedPost.getTitle());
            postById.setDescription(updatedPost.getDescription());
            return postRepository.save(postById);
        } else {
            throw new RuntimeException(String.format("Post id%s not found", postId));
        }
    }

    public Page<Post> getPostsByPostCreatorUsername(String username, Pageable pageable) {
        log.info(String.format("Request list post by username %s", username));
        postRepository.findAllByCreatorUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("List posts by %s are empty", username)));
        Page<Post> posts = postRepository.findAllByCreatorUsername(username, pageable);
        if (posts.isEmpty()) {
            throw new RuntimeException("Comments is empty");
        } else {
            return posts;
        }
    }

    public List<Post> getFollowersPosts(String username) {
        log.info(String.format("Request list FollowersPosts by username %s", username));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("User %s not found", username)));
        List<Post> postList = new ArrayList<>();
        for (String subscription : user.getSubscriptions()) {
            Optional<List<Post>> currentUserSubscription = postRepository.findAllByCreatorUsername(subscription);
            currentUserSubscription.ifPresent(postList::addAll);
        }

        if (postList.isEmpty()) {
            throw new RuntimeException(String.format("List FollowersPosts by user %s are empty", username));
        } else {

            return postList;
        }
    }
}
