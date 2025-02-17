package az.project.blogposter.model.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PosterRequestDTO {

    @Size(max = 500)
    private String name;

    @Size(max = 5000)
    private String content;

    private Integer userId;
}