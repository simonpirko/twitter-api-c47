package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.comment.ResponseCommentDto;
import by.tms.twitterapic47.dto.comment.SaveCommentDto;
import by.tms.twitterapic47.dto.comment.UpdateCommentDto;
import by.tms.twitterapic47.entity.Comment;
import by.tms.twitterapic47.service.CommentService;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add/{postId}")
    public ResponseEntity<?> add(@PathVariable @Positive long postId, @Valid @RequestBody SaveCommentDto saveCommentDto) {
        Comment add = commentService.add(mapper.map(saveCommentDto, Comment.class), postId);
        return new ResponseEntity<>(mapper.map(add, ResponseCommentDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable @Positive long commentId) {
        Comment delete = commentService.delete(commentId);
        return new ResponseEntity<>(mapper.map(delete, ResponseCommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/{postId}/getAll")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable @Positive long postId) {
        List<ResponseCommentDto> commentDto = commentService.getCommentsByPostId(postId)
                .stream()
                .map(comment -> mapper.map(comment, ResponseCommentDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> edit(@PathVariable @Positive long commentId,@Valid @RequestBody UpdateCommentDto updateComment) {
        Comment edit = commentService.edit(commentId, updateComment.getDescription());
        return new ResponseEntity<>(mapper.map(edit, ResponseCommentDto.class), HttpStatus.OK);
    }
}
