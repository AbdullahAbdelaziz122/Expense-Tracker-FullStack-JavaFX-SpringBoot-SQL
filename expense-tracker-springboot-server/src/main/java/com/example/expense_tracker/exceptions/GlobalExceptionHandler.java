package com.example.expense_tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "error", ex.getMessage(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }



    // Handel Validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing // in case of duplicate fields
                ));

        Map<String, Object> response = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", fieldErrors,
                "timestamp", LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UserNotAuthorizedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            Map.of(
                    "error", ex.getMessage(),
                    "status", HttpStatus.UNAUTHORIZED.value(),
                    "timestamp", LocalDateTime.now()
            )
        );
    }

    @ExceptionHandler(EmailAlreadyExist.class)
    public ResponseEntity<Map<String, Object>> handelEmailAlreadyExist(EmailAlreadyExist ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "error", ex.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(TransactionCategoryAlreadyExist.class)
    public ResponseEntity<Map<String, Object>> handleTransactionCategoryAlreadyExist(TransactionCategoryAlreadyExist ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "error", ex.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "timestamp", LocalDateTime.now()
                )
        );
    }

}
