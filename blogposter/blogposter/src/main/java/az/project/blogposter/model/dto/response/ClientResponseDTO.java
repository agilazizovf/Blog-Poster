package az.project.blogposter.model.dto.response;

import az.project.blogposter.model.enums.Status;
import lombok.Data;

@Data
public class ClientResponseDTO {

    private String username;
    private Status status;
}
