package az.project.blogposter.repository;

import az.project.blogposter.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Integer> {

    List<Poster> findAllByNameContaining(String name);
}
