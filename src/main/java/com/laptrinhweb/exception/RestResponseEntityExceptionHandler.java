package com.laptrinhweb.exception;

import com.laptrinhweb.exception.EmailExistedException;
import com.laptrinhweb.exception.ServerErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        String errorMessage = "Access denied message here";
        ex.printStackTrace();
        return new ResponseEntity<>(
                "{\"message\": \"" + errorMessage + "\"}",
                new HttpHeaders(),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ex.printStackTrace();
        return new ResponseEntity<>(
                "{\"message\": \"" + errorMessage + "\"}",
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({ ServerErrorException.class })
    public ResponseEntity<Object> handleServerErrorException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ex.printStackTrace();
        return new ResponseEntity<>(
                "{\"message\": \"" + errorMessage + "\"}",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler({ EmailExistedException.class })
    public ResponseEntity<Object> handleEmailExistedException(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ex.printStackTrace();
        return new ResponseEntity<>(
                "{\"message\": \"" + errorMessage + "\"}",
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleException01(Exception ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        ex.printStackTrace();
        return new ResponseEntity<>(
                "{\"message\": \"" + errorMessage + "\"}",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

