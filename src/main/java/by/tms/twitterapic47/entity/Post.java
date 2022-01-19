package by.tms.twitterapic47.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn
    private User creator;

    private String title;

    private String description;

    private String dateCreating;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;

    @PreRemove
    public void delete(){
        creator.getPosts().remove(this);
    }
}
