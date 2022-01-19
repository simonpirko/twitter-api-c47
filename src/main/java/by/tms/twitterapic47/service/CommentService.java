package by.tms.twitterapic47.service;

import by.tms.twitterapic47.dto.comment.GetSaveCommentDto;
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
import java.util.stream.Collectors;

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

    public GetSaveCommentDto delete(long commentId) {
        Comment commentById = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        GetSaveCommentDto dto = setFieldsGetSaveCommentDto(commentById);
        commentRepository.delete(commentById);
        return dto;
    }

    public List<GetSaveCommentDto> getAllCommentsByPostId(long postId) {
        Post postById = postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        if (postById.getComments() == null) {
            throw new RuntimeException("Comments not found");
        } else {
            return getSaveCommentDtoListFromPost(postById);
        }
    }


    private String setDate() {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(
                "dd.MM.yyyy hh:mm");
        return format1.format(date);
    }

    private List<GetSaveCommentDto> getSaveCommentDtoListFromPost(Post post) {
        return post.getComments().stream().map(this::setFieldsGetSaveCommentDto).collect(Collectors.toList());
    }

    private GetSaveCommentDto setFieldsGetSaveCommentDto(Comment comment) {
        GetSaveCommentDto commentDto = new GetSaveCommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCreatorName(comment.getCreator().getUsername());
        commentDto.setDescription(comment.getDescription());
        commentDto.setDateCreating(comment.getDateCreating());
        commentDto.setPostName(comment.getPost().getTitle());
        return commentDto;
    }
}
