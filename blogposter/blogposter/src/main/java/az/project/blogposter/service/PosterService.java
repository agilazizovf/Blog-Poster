package az.project.blogposter.service;

import az.project.blogposter.model.dto.request.PosterRequestDTO;
import az.project.blogposter.model.dto.response.PosterResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PosterService {

    ResponseEntity<PosterResponseDTO> savePost(PosterRequestDTO requestDTO);
    List<PosterResponseDTO> getAllPosters();
    void like(Integer posterId);
    ResponseEntity<PosterResponseDTO> findPosterById(Integer postId);
    List<PosterResponseDTO> searchByName(String name);
    void deleteById(Integer postId);
}
