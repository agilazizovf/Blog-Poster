package az.project.blogposter.model.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @Size(max = 1000)
    private String content;
    private Integer userId;
    private Integer posterId;
}
