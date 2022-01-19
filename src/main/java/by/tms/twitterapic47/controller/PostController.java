package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/save/{username}")
    public ResponseEntity<?> save(@RequestBody Post post, @PathVariable("username") String username) {
        Post save = postService.save(post, username);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") long postId, @RequestParam("username") String username) {
        Post delete = postService.delete(postId, username);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @GetMapping("/getPosts/{username}")
    public ResponseEntity<?> getPosts(@PathVariable("username") String username) {
        List<Post> posts = postService.getPosts(username);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getFollowersPosts/{username}")
    public ResponseEntity<?> getFollowersPosts(@PathVariable("username") String username) {
        List<Post> followersPosts = postService.getFollowersPosts(username);
        return new ResponseEntity<>(followersPosts, HttpStatus.OK);
    }
}
