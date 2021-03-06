package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.post.ResponsePostDto;
import by.tms.twitterapic47.dto.post.SavePostDto;
import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.service.PostService;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@Validated
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/save/{username}")
    public ResponseEntity<?> save(@PathVariable @Length(min = 1, max = 255) String username, @Valid @RequestBody SavePostDto post) {
        Post savedPost = postService.save(mapper.map(post, Post.class), username);
        return new ResponseEntity<>(mapper.map(savedPost, ResponsePostDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable @Positive long postId) {
        Post delete = postService.delete(postId);
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @GetMapping("/getPosts/{username}")
    public ResponseEntity<?> getPosts(@PathVariable @Length(min = 1, max = 255) String username,
                                      @RequestParam Optional<Integer> page,
                                      @RequestParam Optional<Integer> size,
                                      @RequestParam Optional<String> sortBy) {
        List<ResponsePostDto> postDto = postService.getPostsByPostCreatorUsername(username, (PageRequest.of(page.orElse(0),
                        size.orElse(5),
                        Sort.Direction.ASC,
                        sortBy.orElse("id"))))
                .stream()
                .map(post -> mapper.map(post, ResponsePostDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/getFollowersPosts/{username}")
    public ResponseEntity<?> getFollowersPosts(@PathVariable @Length(min = 1, max = 255) String username,
                                               @RequestParam Optional<Integer> page,
                                               @RequestParam Optional<Integer> size,
                                               @RequestParam Optional<String> sortBy) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, sortBy.orElse("id"));
        List<ResponsePostDto> postDto = postService.getFollowersPosts(username)
                .stream()
                .map(post -> mapper.map(post, ResponsePostDto.class))
                .collect(Collectors.toList());
        PagedListHolder listHolder = new PagedListHolder(postDto);
        listHolder.setPageSize(pageable.getPageSize());
        listHolder.setPage(pageable.getPageNumber());
        return new ResponseEntity<>(listHolder.getPageList(), HttpStatus.OK);
    }
}
