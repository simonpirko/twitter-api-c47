package by.tms.twitterapic47.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @OneToMany
    private List<User> subscriptions;

    @OneToMany
    private List<Post> posts;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status;
}
