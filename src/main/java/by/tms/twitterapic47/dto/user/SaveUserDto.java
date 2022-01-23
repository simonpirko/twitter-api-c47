package by.tms.twitterapic47.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SaveUserDto {

    @NotBlank
    @NotEmpty
    @Length(max = 255)
    private String username;

    @NotBlank
    @NotEmpty
    @Length(min = 3, max = 255)
    private String password;

    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 255)
    @Email
    private String email;

    @NotBlank
    @NotEmpty
    @Length(max = 255)
    private String firstName;

    @NotBlank
    @NotEmpty
    @Length(max = 255)
    private String lastName;
}
