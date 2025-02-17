package az.project.blogposter.model.dto.response;


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
