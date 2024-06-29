package az.project.blogposter.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class PosterResponseDTO {
    private Integer id;
    private String name;
    private String content;
    private String postedBy;
    private Date creation_date;
    private Integer likes;
    private Integer views;
}
