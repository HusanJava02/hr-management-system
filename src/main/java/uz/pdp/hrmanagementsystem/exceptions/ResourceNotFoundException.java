package uz.pdp.hrmanagementsystem.exceptions;

public class ResourceNotFoundException extends DatabaseException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
