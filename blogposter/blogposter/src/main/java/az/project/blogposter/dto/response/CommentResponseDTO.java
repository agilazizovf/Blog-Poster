package az.project.blogposter.dto.response;

import az.project.blogposter.entity.Poster;
import az.project.blogposter.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponseDTO {

    private Integer id;
    private String content;
    private Date createdAt;
    private String postedBy;
    private Integer posterId;

}
