package gr.aueb.tddagent.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import gr.aueb.tddagent.exception.custom.AlreadyExistsException;
import gr.aueb.tddagent.exception.custom.NotFoundException;
import gr.aueb.tddagent.exception.schemas.ApiError;
import gr.aueb.tddagent.exception.schemas.ValidationError;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
        MethodArgumentNotValidException ex,
        WebRequest request
    ) {
        List<ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ValidationError(
                error.getField(),
                error.getDefaultMessage()
            ))
            .toList();

        return createErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Validation Error",
            "Invalid request parameters",
            request,
            validationErrors
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
        ConstraintViolationException ex,
        WebRequest request
    ) {
        List<ValidationError> validationErrors = ex.getConstraintViolations()
            .stream()
            .map(violation -> new ValidationError(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            ))
            .toList();

        return createErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Validation Error",
            "Constraint violation",
            request,
            validationErrors
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
        NotFoundException ex,
        WebRequest request
    ) {
        return createErrorResponse(
            HttpStatus.NOT_FOUND,
            "Not Found",
            ex.getMessage(),
            request,
            null
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
        AlreadyExistsException ex,
        WebRequest request
    ) {
        return createErrorResponse(
            HttpStatus.CONFLICT,
            "Conflict",
            ex.getMessage(),
            request,
            null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaughtException(
        Exception ex,
        WebRequest request
    ) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "An unexpected error occurred",
            request,
            null
        );
    }

    private ResponseEntity<ApiError> createErrorResponse(
        HttpStatus status,
        String error,
        String message,
        WebRequest request,
        List<ValidationError> validationErrors
    ) {
        ApiError apiError = new ApiError(
            LocalDateTime.now(),
            status.value(),
            error,
            message,
            request.getDescription(false),
            validationErrors
        );
        
        return new ResponseEntity<>(apiError, status);
    }
}
