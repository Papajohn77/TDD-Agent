package gr.aueb.tddagent.exception.schemas;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<ValidationError> validationErrors
) {}
