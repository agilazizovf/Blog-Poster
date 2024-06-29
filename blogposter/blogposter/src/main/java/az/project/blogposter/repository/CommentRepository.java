package az.project.blogposter.repository;

import az.project.blogposter.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPosterId(Integer postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.poster.id = :posterId")
    void deleteByPosterId(Integer posterId);
}
