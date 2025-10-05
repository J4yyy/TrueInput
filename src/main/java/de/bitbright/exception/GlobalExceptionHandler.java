package de.bitbright.exception;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import de.bitbright.util.GenRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public final class GlobalExceptionHandler {

    @Autowired
    private Environment environment;

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
        GenRes res = new GenRes(null, ex.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerNotFoundException.class)
    public ResponseEntity<Object> handleHandlerNotFoundException(HandlerNotFoundException ex) {
        GenRes res = new GenRes(null, ex.getMessage());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if(cause instanceof InvalidTypeIdException typeError) {
            GenRes res = new GenRes(null, "'" + typeError.getTypeId() + "' validator could not be found");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        if(isDevEnv()) {
            GenRes res = new GenRes(null, ex.getClass().getCanonicalName());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            GenRes res = new GenRes(null, "body must be set");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        if(isDevEnv()) {
            GenRes res = new GenRes(null, ex.getMessage());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            GenRes res = new GenRes(null, "error");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isDevEnv() {
        return Arrays.stream(this.environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("dev"));
    }
}