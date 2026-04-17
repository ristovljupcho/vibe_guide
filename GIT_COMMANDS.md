# GIT_COMMANDS.md

## Purpose

This file defines the preferred shorthand command mappings for Git work in this repository.

Treat these mappings as the default interpretation for human shorthand and agent-assisted Git actions.

## Core Rule

1. Prefer explicit, non-interactive Git commands.
2. Avoid ambiguous shorthand when it can hide behavior.
3. Never assume `commit -a` includes untracked files.

## Command Mappings

### Status And Inspection

- `status` -> `git status --short`
- `status full` -> `git status`
- `diff` -> `git diff`
- `diff staged` -> `git diff --cached`
- `log` -> `git log --oneline --decorate -n 10`
- `branches` -> `git branch -a`

### Staging

- `stage <file>` -> `git add <file>`
- `stage all` -> `git add -A`
- `unstage <file>` -> `git restore --staged <file>`

### Commit

- `commit` -> commit currently staged changes only
- `commit all` -> `git add -A && git commit`
- `commit tracked` -> `git commit -a`

Important:

- `git commit -a` stages only modified and deleted tracked files
- it does not include new untracked files
- use `commit all` when the intention is truly all current changes

### Branching

- `new branch <name>` -> `git checkout -b <name>`
- `switch <name>` -> `git checkout <name>`
- `current branch` -> `git branch --show-current`

### Sync

- `push` -> `git push`
- `push upstream` -> `git push -u origin <current-branch>`
- `pull` -> `git pull`
- `fetch` -> `git fetch --all`

## Recommendations

1. Prefer `commit all` over `commit -a` when working quickly.
2. Use `status` before every commit.
3. Use `diff staged` before committing.
4. Keep one logical change per commit.
5. Prefer branch names tied to issue numbers when available.
6. Prefer small, descriptive commit messages in imperative mood.

## Recommended Shorthand Set

If you want a compact set of daily-use commands, use these:

- `status`
- `diff`
- `diff staged`
- `stage all`
- `commit all`
- `push`
- `branches`
- `current branch`

## Anti-Patterns

1. Do not use `commit -a` if new files were added.
2. Do not use vague commit messages.
3. Do not commit unrelated changes together unless explicitly intended.
4. Do not use destructive Git commands without explicit confirmation.
