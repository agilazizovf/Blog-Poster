package az.project.blogposter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Poster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(length = 5000)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Client postedBy;

    private Date creation_date;

    private Integer likes = 0;

    private Integer views = 0;

    @ElementCollection
    @CollectionTable(name = "poster_likes", joinColumns = @JoinColumn(name = "poster_id"))
    @Column(name = "user_id")
    private Set<Integer> likedByUsers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "poster_views", joinColumns = @JoinColumn(name = "poster_id"))
    @Column(name = "user_id")
    private Set<Integer> viewedByUsers = new HashSet<>();





}
