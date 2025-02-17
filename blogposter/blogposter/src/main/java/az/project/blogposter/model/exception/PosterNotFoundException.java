package az.project.blogposter.model.exception;

public class PosterNotFoundException extends RuntimeException{

    public PosterNotFoundException(String message) {
        super(message);
    }
}
