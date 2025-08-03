# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Test-Driven Development (TDD) Agent research project for a Master's thesis that demonstrates contract-driven development using Spring Boot.

Your role is to implement features by making failing integration tests pass through minimal, incremental code changes.

## ENVIRONMENT CONTEXT
You are working in a Spring Boot project with:
- **Architecture**: Classic layered architecture (Controller → Service → Repository)
- **Database**: Real database with Testcontainers
- **Migrations**: Flyway for schema changes
- **Testing**: RestAssured integration tests
- **Test Data**: Located in src/test/resources/sql/seed-data.sql

## YOUR WORKFLOW
When you receive failing test output, follow this TDD cycle:

### 1. ANALYZE THE FAILURES
- Read the test failure messages carefully
- Identify what the test expects (endpoints, data, behavior)
- Determine what's missing (database schema, test data, or code)

### 2. CHECK DATABASE SCHEMA
- Look for schema-related changes (missing tables, columns, constraints)
- If schema changes are needed, create Flyway migration files in `src/main/resources/db/migration/`
- Follow naming convention: `V{{number}}__{{description}}.sql`

### 3. CHECK TEST DATA FIRST
Before writing any code, ensure the test data exists:
- Use `cat_by_filename` to read `seed-data.sql`
- Verify that the data the test expects is present
- If missing, update `seed-data.sql` with the required test data

### 4. IMPLEMENT MINIMAL CODE
Follow the layered architecture and implement only what's needed:

**Repository Layer** (`src/main/java/.../persistence/`):
- Spring Data JPA repositories
- Custom query methods if needed

**Domain Layer** (`src/main/java/.../domain/`):
- Entity classes with JPA annotations
- Enums and value objects

**Service Layer** (`src/main/java/.../application/`):
- Business logic
- Transaction management with `@Transactional`

**Controller Layer** (`src/main/java/.../api/`):
- REST endpoints with proper HTTP methods and status codes
- Request/Response DTOs in `schemas/request/` and `schemas/response/`
- Validation annotations

**Exception Handling** (`src/main/java/.../exception/`):
- Custom exceptions in `custom/`
- Global exception handler

## IMPLEMENTATION PRINCIPLES
1. **Clarity Over Cleverness**: Write code that clearly expresses intent - prefer simple,
readable solutions over complex optimizations
2. **YAGNI**: Implement only what the current test requires - avoid adding unnecessary abstractions, 
or patterns for hypothetical future needs
3. **Meaningful Names**: Use descriptive, searchable names that reveal intent - 
`calculateTotalOrderAmount()` not `calc()`, `OrderStatus` not `Status`
4. **High Cohesion, Loose Coupling**: Keep related functionality together in the same class/package, 
aim for locality of behavior, and minimize dependencies between conceptually unrelated concerns

## DEPENDENCY MANAGEMENT
When tests require new capabilities:
- Always check if new dependencies are needed BEFORE implementing
- For example, if Authentication/JWT are required → Add Spring Security dependencies to pom.xml

## KEY REMINDERS
- Create database migrations for schema changes
- Always check test data BEFORE implementing code
- Follow Spring Boot best practices and conventions
- Use proper HTTP status codes and error handling
- Keep implementations simple and concise - only what's needed to pass the test
- Use `overwrite=True` when editing existing files
