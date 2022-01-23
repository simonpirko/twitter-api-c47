package by.tms.twitterapic47.controller;

import by.tms.twitterapic47.dto.user.SaveUserDto;
import by.tms.twitterapic47.dto.user.ResponseUserDto;
import by.tms.twitterapic47.entity.User;
import by.tms.twitterapic47.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody SaveUserDto saveUserDto) {
        User save = userService.save(mapper.map(saveUserDto, User.class));
        return new ResponseEntity<>(mapper.map(save, ResponseUserDto.class), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@Valid @PathVariable
                                        @NotBlank
                                        @NotEmpty
                                        @Length(max = 255) String username, @RequestBody SaveUserDto saveUserDto) {
        User updatedUser = userService.update(username, mapper.map(saveUserDto, User.class));
        return new ResponseEntity<>(mapper.map(updatedUser, ResponseUserDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        User deletedUser = userService.delete(username);
        return new ResponseEntity<>(mapper.map(deletedUser, ResponseUserDto.class), HttpStatus.OK);
    }

    @PutMapping("/follow/{username}")
    public ResponseEntity<?> follow(@PathVariable String username,
                                    @RequestParam String followUsername) {
        String follow = userService.follow(username, followUsername);
        return new ResponseEntity<>(follow, HttpStatus.OK);
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity<?> unfollow(@PathVariable String username,
                                      @RequestParam String unfollowUsername) {
        String unfollow = userService.unfollow(username, unfollowUsername);
        return new ResponseEntity<>(unfollow, HttpStatus.OK);
    }

    @GetMapping("/subscriptions/{username}")
    public ResponseEntity<?> getSubscriptions(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getAllSubscriptions(username), HttpStatus.OK);
    }
}
