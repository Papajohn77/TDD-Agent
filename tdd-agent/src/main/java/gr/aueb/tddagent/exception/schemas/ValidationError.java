package gr.aueb.tddagent.exception.schemas;

public record ValidationError(
    String field,
    String message
) {}
