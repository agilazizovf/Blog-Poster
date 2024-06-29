package az.project.blogposter.dto.response;

import az.project.blogposter.entity.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class UserResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Collection<Role> roles;
}
