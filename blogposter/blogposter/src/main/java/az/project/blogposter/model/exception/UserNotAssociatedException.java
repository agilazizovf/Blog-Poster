package az.project.blogposter.model.exception;

public class UserNotAssociatedException extends RuntimeException{

    public UserNotAssociatedException(String message) {
        super(message);
    }
}
