package by.tms.twitterapic47.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SaveCommentDto {

    private long postId;

    private String username;

    private String description;
}
