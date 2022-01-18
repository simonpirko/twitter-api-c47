package by.tms.twitterapic47.repository;

import by.tms.twitterapic47.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> deleteByUsername(String username);
}
