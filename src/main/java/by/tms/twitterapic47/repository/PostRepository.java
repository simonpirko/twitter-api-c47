package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<List<Post>> findAllByCreatorUsername(String creatorUsername);

    void deleteAllByCreatorUsername(String creatorUsername);
}
