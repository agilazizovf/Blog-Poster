package az.project.blogposter.service.impl;

import az.project.blogposter.entity.Comment;
import az.project.blogposter.entity.User;
import az.project.blogposter.model.dto.request.PosterRequestDTO;
import az.project.blogposter.model.dto.response.PosterResponseDTO;
import az.project.blogposter.entity.Client;
import az.project.blogposter.entity.Poster;
import az.project.blogposter.model.exception.*;
import az.project.blogposter.repository.ClientRepository;
import az.project.blogposter.repository.CommentRepository;
import az.project.blogposter.repository.PosterRepository;
import az.project.blogposter.repository.UserRepository;
import az.project.blogposter.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<PosterResponseDTO> savePost(PosterRequestDTO posterRequestDTO) {
        Client client = clientRepository.findById(posterRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + posterRequestDTO.getUserId()));

        Poster poster = new Poster();
        poster.setName(posterRequestDTO.getName());
        poster.setContent(posterRequestDTO.getContent());
        poster.setLikes(0);
        poster.setViews(0);
        poster.setCreation_date(new Date());
        poster.setPostedBy(client);

        Poster savedPoster = posterRepository.save(poster);
        return ResponseEntity.ok(convertToResponseDTO(savedPoster));
    }

    @Override
    public List<PosterResponseDTO> getAllPosters() {
        List<Poster> posters = posterRepository.findAll();
        return posters.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void like(Integer postId) {
        User currentUser = getCurrentUser();
        Poster poster = posterRepository.findById(postId)
                .orElseThrow(() -> new PosterNotFoundException("Poster not found with id: " + postId));

        if (!poster.getLikedByUsers().contains(currentUser.getId())) {
            if (poster.getLikes() == null) {
                poster.setLikes(0);
            }
            poster.getLikedByUsers().add(currentUser.getId());
            poster.setLikes(poster.getLikes() + 1);
            posterRepository.save(poster);
        } else {
            throw new UserLikeException("User has already liked this comment.");
        }
    }

    @Override
    public ResponseEntity<PosterResponseDTO> findPosterById(Integer postId) {
        User user = getCurrentUser();

        Poster poster = posterRepository.findById(postId)
                .orElseThrow(() -> new PosterNotFoundException("Poster not found with id: " + postId));

        if (!poster.getViewedByUsers().contains(user.getId())) {
            poster.setViews(poster.getViews() + 1);
            poster.getViewedByUsers().add(user.getId());
            posterRepository.save(poster);
        }

        return ResponseEntity.ok(convertToResponseDTO(poster));
    }

    @Override
    public List<PosterResponseDTO> searchByName(String name) {
        List<Poster> posters = posterRepository.findAllByNameContaining(name);
        return posters.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer postId) {
        User user = getCurrentUser();

        Poster poster = posterRepository.findById(postId)
                .orElseThrow(() -> new PosterNotFoundException("Poster not found with id: " + postId));

        // Check if the user is the owner of the post
        if (!poster.getPostedBy().getId().equals(user.getId())) {
            throw new AuthenticationException("You are not authorized to delete this post.");
        }
        commentRepository.deleteByPosterId(postId);
        posterRepository.deleteById(postId);
    }

    private PosterResponseDTO convertToResponseDTO(Poster poster) {
        PosterResponseDTO posterResponseDTO = new PosterResponseDTO();
        posterResponseDTO.setId(poster.getId());
        posterResponseDTO.setName(poster.getName());
        posterResponseDTO.setContent(poster.getContent());
        posterResponseDTO.setPostedBy(poster.getPostedBy().getUsername()); // Only the username
        posterResponseDTO.setCreation_date(poster.getCreation_date());
        posterResponseDTO.setLikes(poster.getLikes());
        posterResponseDTO.setViews(poster.getViews());
        return posterResponseDTO;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
