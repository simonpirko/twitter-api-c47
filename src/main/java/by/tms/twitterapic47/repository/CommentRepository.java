package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(long postId);

    void deleteAllByCreatorUsername(String creatorUsername);

    void deleteAllByPostId(long postId);
}
