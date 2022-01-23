package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(long postId, Pageable pageable);

    void deleteAllByCreatorUsername(String creatorUsername);

    void deleteAllByPostId(long postId);
}
