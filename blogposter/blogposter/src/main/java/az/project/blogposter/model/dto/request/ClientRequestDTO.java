package az.project.blogposter.model.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRequestDTO {

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50)")
    private String firstName;

    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 3, max = 50, message = "Surname must be between 3 and 50 characters")
    @Column(columnDefinition = "VARCHAR(50)")
    private String lastName;

    @Size(min = 1, message = "Username must be at least 1 character")
    @Size(max = 30, message = "Username must be maximum 30 characters")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Size(min = 1, message = "Password must be at least 1 character")
    @Size(max = 30, message = "Password must be maximum 30 characters")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Email must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email must be valid.Example: firstname-lastname@example.com  ")
    @Column(columnDefinition = "VARCHAR(70)")
    @Size(min = 6, max = 70, message = "Email must be between 3 and 70 characters")
    private String email;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthday;

}
