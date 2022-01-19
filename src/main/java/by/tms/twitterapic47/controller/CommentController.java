package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.comment.GetSaveCommentDto;
import by.tms.twitterapic47.dto.comment.SaveCommentDto;
import by.tms.twitterapic47.entity.Comment;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SaveCommentDto saveCommentDto) {
        Comment add = commentService.add(convertSaveCommentDtoToComment(saveCommentDto),
                saveCommentDto.getPostId(), saveCommentDto.getUsername());

        GetSaveCommentDto getSaveComment = convertCommentToGetSaveCommentDto(add);
        return new ResponseEntity<>(getSaveComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteByCommentId(@PathVariable("commentId") long commentId) {
        GetSaveCommentDto delete = commentService.delete(commentId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable("postId") long postId) {
        List<GetSaveCommentDto> comments = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    private Comment convertSaveCommentDtoToComment(SaveCommentDto saveCommentDto) {
        Comment comment = new Comment();
        comment.setDescription(saveCommentDto.getDescription());
        return comment;
    }

    private GetSaveCommentDto convertCommentToGetSaveCommentDto(Comment comment) {
        GetSaveCommentDto getSaveComment = new GetSaveCommentDto();
        getSaveComment.setId(comment.getId());
        getSaveComment.setCreatorName(comment.getCreator().getUsername());
        getSaveComment.setDescription(comment.getDescription());
        getSaveComment.setDateCreating(comment.getDateCreating());
        getSaveComment.setPostName(comment.getPost().getTitle());
        return getSaveComment;
    }
}
