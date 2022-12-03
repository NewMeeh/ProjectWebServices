package fr.uge.ebcserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.FORBIDDEN,
        reason = "You must be logged."
)
public class ForbiddenException
        extends RuntimeException {

    public ForbiddenException(Throwable t) {
        super(t);
    }
}