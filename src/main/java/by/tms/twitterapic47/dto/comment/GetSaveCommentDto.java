package by.tms.twitterapic47.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GetSaveCommentDto {

    private long id;

    private String creatorName;

    private String description;

    private String dateCreating;

    private String postName;
}
