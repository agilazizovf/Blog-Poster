package az.project.blogposter.service;

import az.project.blogposter.model.dto.request.CommentRequestDTO;
import az.project.blogposter.model.dto.response.CommentResponseDTO;
import az.project.blogposter.entity.Client;
import az.project.blogposter.entity.Comment;
import az.project.blogposter.entity.Poster;
import az.project.blogposter.model.exception.UserNotFoundException;
import az.project.blogposter.repository.ClientRepository;
import az.project.blogposter.repository.CommentRepository;
import az.project.blogposter.repository.PosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private ClientRepository clientRepository;

    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        Client user = clientRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + commentRequestDTO.getUserId()));
        Poster poster = posterRepository.findById(commentRequestDTO.getPosterId())
                .orElseThrow(() -> new UserNotFoundException("Poster not found with id: " + commentRequestDTO.getPosterId()));

        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        comment.setPostedBy(user);
        comment.setPoster(poster);
        comment.setCreatedAt(new Date());

        Comment savedComment = commentRepository.save(comment);
        return convertToResponseDTO(savedComment);
    }

    public List<CommentResponseDTO> getCommentsByPosterId(Integer posterId) {
        List<Comment> comments = commentRepository.findByPosterId(posterId);
        return comments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private CommentResponseDTO convertToResponseDTO(Comment comment) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setContent(comment.getContent());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt());
        commentResponseDTO.setPostedBy(comment.getPostedBy().getUsername()); // Only the username
        commentResponseDTO.setPosterId(comment.getPoster().getId()); // Only the poster ID
        return commentResponseDTO;
    }
}
