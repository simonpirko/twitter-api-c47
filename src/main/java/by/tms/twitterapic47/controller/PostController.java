package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.post.ResponsePostDto;
import by.tms.twitterapic47.dto.post.SavePostDto;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/save/{username}")
    public ResponseEntity<?> save(@RequestBody SavePostDto post, @PathVariable String username) {
        Post savedPost = postService.save(mapper.map(post,Post.class), username);
        return new ResponseEntity<>(mapper.map(savedPost, ResponsePostDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable long postId) {
        Post delete = postService.delete(postId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @GetMapping("/getPosts/{username}")
    public ResponseEntity<?> getPosts(@PathVariable String username) {
        List<ResponsePostDto> postDto = postService.getPosts(username)
                .stream()
                .map(post -> mapper.map(post, ResponsePostDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/getFollowersPosts/{username}")
    public ResponseEntity<?> getFollowersPosts(@PathVariable String username) {
        List<ResponsePostDto> postDto = postService.getFollowersPosts(username)
                .stream()
                .map(post -> mapper.map(post, ResponsePostDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
}
