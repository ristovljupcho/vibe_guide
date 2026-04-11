# AGENTS.md

## Purpose

This file defines the required implementation standards for contributors and coding agents working in this repository. Treat these rules as project policy, not suggestions.

## Directory Structure

1. Each model MUST have its own top-level directory.
2. Every model directory MUST contain the following subdirectories:
   - `entities`
   - `repositories`
   - `dtos`
   - `mappers`
   - `services`
   - `utils`
   - `controllers`
3. The `services` directory MUST contain both:
   - the service interface
   - the service implementation class

## Shared Directories

The following directories are shared and MUST remain outside model-specific directories:

- `enums`
- `exceptions`
- `specifications`

## Code Structure Rules

1. Repeated logic MUST be extracted into helper methods.
2. Logic SHOULD also be extracted into helper methods when a method becomes too long, hard to read, or too complex.
3. Helper methods SHOULD be used to keep service and controller code focused, readable, and maintainable.
4. N+1 query problems in entity relations MUST be avoided.

## JavaDoc Rules

1. Complex methods MUST include JavaDocs.
2. Helper methods MUST include JavaDocs when their purpose, behavior, or usage is not immediately obvious.
3. JavaDocs SHOULD explain intent and non-obvious behavior, not restate trivial code.

## CRUD Standards

1. Service methods and controller methods MUST follow CRUD conventions consistently.
2. CRUD implementations MUST be consistent in:
   - naming
   - method order
   - logical flow
   - expected responsibility
3. New service and controller methods SHOULD align with the existing CRUD pattern used across the project.
4. Avoid introducing custom naming or ordering patterns when a standard CRUD approach already applies.
