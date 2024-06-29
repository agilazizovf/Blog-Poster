package az.project.blogposter.dto.request;

import lombok.Data;

@Data
public class PosterRequestDTO {
    private String name;
    private String content;
    private Integer userId;
}