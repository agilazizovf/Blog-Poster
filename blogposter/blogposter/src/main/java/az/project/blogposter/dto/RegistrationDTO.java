package az.project.blogposter.dto;

import lombok.Data;


@Data
public class RegistrationDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
