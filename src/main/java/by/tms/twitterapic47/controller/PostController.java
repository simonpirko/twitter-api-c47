package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.post.GetPostDto;
import by.tms.twitterapic47.dto.post.SavePostDto;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/save/{username}")
    public ResponseEntity<?> save(@RequestBody SavePostDto post, @PathVariable("username") String username) {
        Post save = postService.save(conversionSavePostDtoToPost(post), username);
        return new ResponseEntity<>(conversionPostToGetPostDto(save), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable("postId") long postId) {
        Post delete = postService.delete(postId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @GetMapping("/getPosts/{username}")
    public ResponseEntity<?> getPosts(@PathVariable("username") String username) {
        List<GetPostDto> responseList=new ArrayList<>();
        for (Post post : postService.getPosts(username)) {
            responseList.add(conversionPostToGetPostDto(post));
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/getFollowersPosts/{username}")
    public ResponseEntity<?> getFollowersPosts(@PathVariable("username") String username) {
        List<Post> followersPosts = postService.getFollowersPosts(username);
        return new ResponseEntity<>(followersPosts, HttpStatus.OK);
    }

    private Post conversionSavePostDtoToPost(SavePostDto savePostDto) {
        Post post = new Post();
        post.setTitle(savePostDto.getTitle());
        post.setDescription(savePostDto.getDescription());
        return post;
    }

    private GetPostDto conversionPostToGetPostDto(Post post) {
        GetPostDto getPostDto = new GetPostDto();
        getPostDto.setPostId(post.getId());
        getPostDto.setCreatorUsername(post.getCreator().getUsername());
        getPostDto.setTitle(post.getTitle());
        getPostDto.setDescription(post.getDescription());
        getPostDto.setDateCreating(post.getDateCreating());
        return getPostDto;
    }
}
