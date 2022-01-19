package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Comment;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment add(Comment comment, long postId, String username) {
        User byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
        Post postById = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        if (byUsername.getPosts().stream()
                .anyMatch(x -> x.getId() == postById.getId())) {
            comment.setDateCreating(setDate());
            comment.setCreator(byUsername);
            comment.setPost(postById);
            commentRepository.save(comment);
            postById.getComments().add(comment);
            postRepository.save(postById);
            return comment;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    private String setDate() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(
                "dd.MM.yyyy hh:mm");
        return format1.format(date);
    }

}
