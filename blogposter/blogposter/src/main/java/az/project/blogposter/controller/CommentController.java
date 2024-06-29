package az.project.blogposter.controller;

import az.project.blogposter.dto.request.CommentRequestDTO;
import az.project.blogposter.dto.response.CommentResponseDTO;
import az.project.blogposter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create-comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        try {
            CommentResponseDTO createdComment = commentService.createComment(commentRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getCommentsByPosterId(@PathVariable Integer postId) {
        try {
            List<CommentResponseDTO> comments = commentService.getCommentsByPosterId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }
}
