package uz.pdp.hrmanagementsystem.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.pdp.hrmanagementsystem.dto.errors.ApiError;
import uz.pdp.hrmanagementsystem.exceptions.CommonJwtException;
import uz.pdp.hrmanagementsystem.exceptions.DatabaseException;
import uz.pdp.hrmanagementsystem.exceptions.GenericException;

@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HRMResourceErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError validationException(MethodArgumentNotValidException e, WebRequest request) {
        log.error("Validation exception", e);
        return new ApiError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), request.getDescription(true));
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError databaseException(DatabaseException exception, WebRequest request) {
        log.error(exception.getMessage(),exception);
        return new ApiError(HttpStatus.CONFLICT,exception.getLocalizedMessage(),request.getDescription(true));
    }

    @ExceptionHandler({CommonJwtException.class,BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError jwtException(Exception exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(),request.getDescription(true));
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError genericException(GenericException exception, WebRequest request) {
        log.error(exception.getMessage(), exception);
        return new ApiError(HttpStatus.CONFLICT, exception.getLocalizedMessage(),request.getDescription(true));
    }



}
