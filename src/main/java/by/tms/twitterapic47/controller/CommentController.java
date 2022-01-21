package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @PostMapping("/add/{postId}")
//    public ResponseEntity<?> add(@PathVariable("postId") long postId, @RequestBody SaveCommentDto saveCommentDto) {
//        Comment add = commentService.add(convertSaveCommentDtoToComment(saveCommentDto), postId, saveCommentDto.getUsername());
//        GetSaveCommentDto getSaveComment = convertCommentToGetSaveCommentDto(add);
//        return new ResponseEntity<>(getSaveComment, HttpStatus.OK);
//    }
//
//
//    private Comment convertSaveCommentDtoToComment(SaveCommentDto saveCommentDto) {
//        Comment comment = new Comment();
//        comment.setDescription(saveCommentDto.getDescription());
//        return comment;
//    }
//
//    private GetSaveCommentDto convertCommentToGetSaveCommentDto(Comment comment) {
//        GetSaveCommentDto getSaveComment = new GetSaveCommentDto();
//        getSaveComment.setId(comment.getId());
//        getSaveComment.setCreatorName(comment.getCreator().getUsername());
//        getSaveComment.setDescription(comment.getDescription());
//        getSaveComment.setDateCreating(comment.getDateCreating());
//        getSaveComment.setPostName(comment.getPost().getTitle());
//        return getSaveComment;
//    }
}
