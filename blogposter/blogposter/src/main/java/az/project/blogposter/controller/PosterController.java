package az.project.blogposter.controller;

import az.project.blogposter.model.dto.request.PosterRequestDTO;
import az.project.blogposter.model.dto.response.PosterResponseDTO;
import az.project.blogposter.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posters")
@RequiredArgsConstructor
public class PosterController {


    private final PosterService posterService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<PosterResponseDTO>> getAllPosters() {
        try {
            List<PosterResponseDTO> posters = posterService.getAllPosters();
            return ResponseEntity.status(HttpStatus.OK).body(posters);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/create-poster")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<PosterResponseDTO> createPoster(@RequestBody PosterRequestDTO posterRequestDTO) {
            return posterService.savePost(posterRequestDTO);
    }

    @PutMapping("/{postId}/like")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void likePost(@PathVariable Integer postId) {
        posterService.like(postId);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<PosterResponseDTO> findPosterById(@PathVariable Integer postId) {
        return posterService.findPosterById(postId);
    }

    @GetMapping("/search/{name}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<PosterResponseDTO>searchByName(@PathVariable String name) {
        return posterService.searchByName(name);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void deletePoster(@PathVariable("id") Integer postId) {
        posterService.deleteById(postId);
    }
}
