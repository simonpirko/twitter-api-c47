package by.tms.twitterapic47.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn
    private User creator;

    private String description;

    private String dateCreating;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JsonIgnore
    private Post post;

    @PreRemove
    public void delete(){
        creator.getPosts().remove(this);
        post.getComments().remove(this);
    }
}
