package by.tms.twitterapic47.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SaveUserDto {

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;
}
