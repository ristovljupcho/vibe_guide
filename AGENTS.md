# AGENTS.md

## Purpose

This file defines the required implementation standards for contributors and coding agents working in this repository. Treat these rules as project policy, not suggestions.

## Directory Structure

1. Each model MUST live under the shared `domain` root directory.
2. Each model MUST have its own directory under `domain`.
   - Example: `com.vibe_guide.domain.place`
3. Shared technical or cross-cutting packages MUST stay outside `domain`.
4. Every model directory MUST contain the following subdirectories:
   - `entities`
   - `repositories`
   - `dtos`
   - `mappers`
   - `services`
   - `utils`
   - `controllers`
5. The `services` directory MUST contain both:
   - the service interface
   - the service implementation class
6. Service implementation classes MUST live in:
   - `services.impl`
7. `.gitkeep` files MAY be used only to preserve intentionally empty directories.
8. If a directory is populated with real source files, its `.gitkeep` file MUST be removed.

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
5. Response messages, exception messages, and other repeated application messages MUST be defined as constants instead of inline string literals.

## Entity Lifecycle Rules

1. If an entity has `createdAt`, it MUST initialize it with an `@PrePersist` method inside the entity.
2. If an entity has `updatedAt`, it MUST maintain it with an `@PreUpdate` method inside the entity.
3. Services SHOULD NOT manually set lifecycle timestamps when those timestamps are entity-managed.

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
5. When creating new CRUD-style methods, use generic CRUD names instead of model-specific names.
6. Prefer the following naming conventions for new service and controller methods:
   - `insert`
   - `update`
   - `delete`
   - `getById`
   - `getAll`
   - `getAllBy...`
   - `getPaginated...`
   - `getTop...`
   - `insertAll`
   - `deleteAll`
   - `deleteById`
   - `toggle` for state-switching or save/unsave style flows when CRUD naming does not fit cleanly
7. Do NOT introduce model-prefixed method names such as:
   - `insertCar`
   - `updatePlace`
   - `deleteReview`
   - `getOfferById`
   when the method already lives inside the corresponding model service or controller.
8. Repository method names MAY stay more descriptive when required by Spring Data query derivation or query intent, but service and controller methods MUST keep the standard CRUD naming style.

## Commit Message Standards

Commit grouping rules:

1. If pending changes are all connected and contribute to the same logical change, they SHOULD be grouped into a single commit.
2. If pending changes are unrelated or belong to separate logical changes, they MUST be split into multiple commits.
3. A commit SHOULD represent one coherent change that can be understood, reviewed, and reverted independently.

1. Subject line MUST be in imperative mood.
   - Good: `Add favourites toggle endpoint`
   - Bad: `Added favourites toggle endpoint`
2. Subject line MUST start with a capital letter and MUST NOT end with punctuation.
3. Subject line SHOULD be concise and ideally <= 50 characters.
4. If more context is needed, include a body separated by a blank line.
5. Body lines SHOULD wrap at ~72 characters.
6. Body MUST explain the `what` and `why`, not only the `how`.
7. Avoid vague or filler commit messages such as:
   - `fix stuff`
   - `oops`
   - `I think this works`
8. Prefer one logical change per commit.

Preferred structure:

`<type>: <imperative summary>`

Optional body:

- what changed
- why it changed
- impact/risk notes (if relevant)

Suggested commit types:

- `feat` for new functionality
- `fix` for bug fixes
- `refactor` for code restructuring without behavior changes
- `docs` for documentation changes
- `test` for tests
- `chore` for maintenance/non-feature work
