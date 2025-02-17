package az.project.blogposter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String username;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDate birthday;
    private LocalDate createdTime;
    private LocalDate updatedTime;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
