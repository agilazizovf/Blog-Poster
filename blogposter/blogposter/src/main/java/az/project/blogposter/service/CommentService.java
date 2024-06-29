package az.project.blogposter.service;

import az.project.blogposter.dto.request.CommentRequestDTO;
import az.project.blogposter.dto.response.CommentResponseDTO;
import az.project.blogposter.entity.Comment;
import az.project.blogposter.entity.Poster;
import az.project.blogposter.entity.User;
import az.project.blogposter.exception.GeneralException;
import az.project.blogposter.repository.CommentRepository;
import az.project.blogposter.repository.PosterRepository;
import az.project.blogposter.repository.UserRepository;
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
    private UserRepository userRepository;

    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new GeneralException("User not found with id: " + commentRequestDTO.getUserId()));
        Poster poster = posterRepository.findById(commentRequestDTO.getPosterId())
                .orElseThrow(() -> new GeneralException("Poster not found with id: " + commentRequestDTO.getPosterId()));

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
