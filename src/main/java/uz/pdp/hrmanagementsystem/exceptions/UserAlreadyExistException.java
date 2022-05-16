package uz.pdp.hrmanagementsystem.exceptions;

public class UserAlreadyExistException extends DatabaseException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
