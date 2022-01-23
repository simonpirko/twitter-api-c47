package by.tms.twitterapic47.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SaveCommentDto {

    @NotBlank
    @Length(min = 1, max = 255)
    private String creatorUsername;

    @NotBlank
    @Length(min = 1, max = 255)
    private String description;
}
