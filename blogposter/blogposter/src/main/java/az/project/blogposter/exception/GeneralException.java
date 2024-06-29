package az.project.blogposter.exception;

public class GeneralException extends RuntimeException{

    String message;

    public GeneralException(String message) {
        super(message);
    }
}
