package by.tms.twitterapic47.dto.post;

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
public class SavePostDto {

    @NotBlank
    @Length(min = 1, max = 255)
    private String title;

    @NotBlank
    @Length(min = 1, max = 255)
    private String description;
}
