package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.Post;
import by.tms.twitterapic47.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<List<Post>> findAllByCreator(User user);
}
