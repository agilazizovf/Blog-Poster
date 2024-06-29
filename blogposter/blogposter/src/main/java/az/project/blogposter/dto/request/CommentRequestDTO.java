package az.project.blogposter.dto.request;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private String content;
    private Integer userId;
    private Integer posterId;
}
