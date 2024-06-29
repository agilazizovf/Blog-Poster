package az.project.blogposter.controller;

import az.project.blogposter.dto.request.PosterRequestDTO;
import az.project.blogposter.dto.response.PosterResponseDTO;
import az.project.blogposter.exception.GeneralException;
import az.project.blogposter.service.PosterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posters")
@RequiredArgsConstructor
public class PosterController {

    @Autowired
    private PosterService posterService;

    @GetMapping
    public ResponseEntity<List<PosterResponseDTO>> getAllPosters() {
        try {
            List<PosterResponseDTO> posters = posterService.getAllPosters();
            return ResponseEntity.status(HttpStatus.OK).body(posters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/create-poster")
    public ResponseEntity<PosterResponseDTO> createPoster(@RequestBody PosterRequestDTO posterRequestDTO) {
        try {
            PosterResponseDTO createdPost = posterService.savePost(posterRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        try {
            posterService.likePost(postId, userId);
            return ResponseEntity.ok("Post liked successfully.");
        } catch (EntityNotFoundException | GeneralException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> findPosterById(@PathVariable Integer postId, @RequestParam Integer userId) {
        try {
            PosterResponseDTO poster = posterService.findPosterById(postId, userId);
            return ResponseEntity.ok(poster);
        } catch (EntityNotFoundException | GeneralException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        try {
            List<PosterResponseDTO> posters = posterService.searchByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(posters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePoster(@PathVariable("id") Integer postId) {
        try {
            posterService.deleteById(postId);
            return ResponseEntity.ok("Poster deleted successfully!.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
