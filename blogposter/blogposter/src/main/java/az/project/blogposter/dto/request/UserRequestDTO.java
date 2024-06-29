package az.project.blogposter.dto.request;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}
