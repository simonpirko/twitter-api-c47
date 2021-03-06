package by.tms.twitterapic47.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseCommentDto {

    private long id;

    private long postId;

    private String creatorUsername;

    private String description;

    private LocalDateTime dateCreating;
}
