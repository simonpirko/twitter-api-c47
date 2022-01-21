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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        Post currentPost = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        comment.setCreatorUsername(byUsername.getUsername());
        comment.setPostId(currentPost.getId());
        comment.setDateCreating(setDate());
        return commentRepository.save(comment);
    }

    public Comment delete(long commentId) {
        Comment commentById = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(commentById);
        return commentById;
    }

    public Comment getCommentById(long id){
        return commentRepository.findById(id).orElseThrow(()->new RuntimeException("Comments not found"));
    }

    public List<Comment> getAllCommentsByPostId(long postId) {
        Post postById = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        List<Comment> allByPostId = commentRepository.findAllByPostId(postId);
        if(allByPostId.isEmpty()){
            throw new RuntimeException("Comments not found");
        }else{
            return allByPostId;
        }
    }

    private String setDate() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(
                "dd.MM.yyyy hh:mm");
        return format1.format(date);
    }

}
