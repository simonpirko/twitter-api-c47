package by.tms.twitterapic47.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponsePostDto {

    private long postId;

    private String creatorUsername;

    private String title;

    private String description;

    private LocalDateTime dateCreating;
}
