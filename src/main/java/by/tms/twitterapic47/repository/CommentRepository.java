package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
