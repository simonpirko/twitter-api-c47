package by.tms.twitterapic47.service;

import by.tms.twitterapic47.entity.Comment;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.repository.CommentRepository;
import by.tms.twitterapic47.repository.PostRepository;
import by.tms.twitterapic47.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment add(Comment comment, long postId) {
        log.info(String.format("Request add comment by postId %s", postId));
        userRepository.findByUsername(comment.getCreatorUsername())
                .orElseThrow(() -> new RuntimeException(String.format("User not found")));
        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(String.format("Post %s not found", postId)));
        comment.setPostId(currentPost.getId());
        return commentRepository.save(comment);
    }

    public Comment delete(long commentId) {
        log.info(String.format("Request delete comment by commentId %s", commentId));
        Comment commentById = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException(String.format("Comment %s not found",commentId)));
        commentRepository.delete(commentById);
        return commentById;
    }

    public Page<Comment> getCommentsByPostId(long postId, Pageable pageable) {
        log.info(String.format("Request list Comments by postId %s", postId));
        postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(String.format("Post %s not found",postId)));
        Page<Comment> allByPostId = commentRepository.findAllByPostId(postId,pageable);
        if (allByPostId.isEmpty()) {
            throw new RuntimeException(String.format("Comments is empty"));
        } else {
            return allByPostId;
        }
    }

    public Comment edit(long commentId, String description) {
        log.info(String.format("Request edit Comments by commentId %s", commentId));
        Comment commentById = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException(String.format("Comment %s not found",commentId)));
        commentById.setDescription(description);
        commentRepository.save(commentById);
        return commentById;
    }
}
