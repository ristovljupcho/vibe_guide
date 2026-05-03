# Vibe Guide

Vibe Guide is a Spring Boot backend for discovering places, events, offers, and trait-based recommendations in Skopje.

This repository currently contains the backend application only. It exposes REST endpoints for places, events, offers,
reviews, favourites, wishlists, visited places, place admins, traits, trait likes, and working hours.

## Stack

- Java 23
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- PostgreSQL
- Neon for managed PostgreSQL hosting
- Liquibase
- Cloudinary for hosted image storage and delivery
- Lombok

## Current project structure

Main domain modules live under `src/main/java/com/vibe_guide`:

- `place`
- `event`
- `offer`
- `review`
- `trait`
- `traitlike`
- `placegallery`
- `eventgallery`
- `placeadmin`
- `favouriteplace`
- `wishlistplace`
- `visitedplace`
- `workinghours`
- `storage`

Liquibase changelogs live under `src/main/resources/db/changelog`.

## Neon and Cloudinary

This project uses [Neon](https://neon.com/) as the hosted PostgreSQL database and [Cloudinary](https://cloudinary.com/) as the hosted image storage service.

Useful links:

- Neon homepage: [https://neon.com/](https://neon.com/)
- Neon docs: [https://neon.com/docs](https://neon.com/docs)
- Cloudinary homepage: [https://cloudinary.com/](https://cloudinary.com/)
- Cloudinary docs: [https://cloudinary.com/documentation](https://cloudinary.com/documentation)

Neon usage:

- stores the application data
- is used through the Spring datasource and Liquibase configuration
- can be reset and recreated cleanly for demo environments

Cloudinary usage:

- stores uploaded place and event gallery images
- returns hosted image URLs that are saved in the database
- serves those images directly to clients
- removes hosted assets when gallery records are deleted

## Environment variables

The backend reads its configuration from environment variables.

### Required

- `CLOUDINARY_URL` (must be set)

The app can start without datasource env vars only if your local defaults are valid (`localhost:5432`, user `postgres`, password `postgres`).
In most setups, you should still set the datasource values explicitly.

### Commonly set (recommended)

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `APP_STORAGE_CLOUDINARY_FOLDER`

### Optional

- `SPRING_LIQUIBASE_URL`
- `SPRING_LIQUIBASE_USER`
- `SPRING_LIQUIBASE_PASSWORD`
- `SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE`
- `SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE`

### `.env` template

Create a `.env` file in the project root with:

```dotenv
# Required
CLOUDINARY_URL=cloudinary://your_api_key:your_api_secret@your_cloud_name

# Recommended datasource values
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/vibe_guide?sslmode=disable
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# Optional app setting
APP_STORAGE_CLOUDINARY_FOLDER=vibe-guide-demo

# Optional Liquibase overrides
# SPRING_LIQUIBASE_URL=
# SPRING_LIQUIBASE_USER=
# SPRING_LIQUIBASE_PASSWORD=

# Optional pool tuning
# SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=5
# SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=1
```

`CLOUDINARY_URL` format:

```text
cloudinary://<api_key>:<api_secret>@<cloud_name>
```

Cloudinary dashboard already provides this full value as **API Environment variable**, so you can copy-paste it directly.

## Database and Liquibase

The app runs Liquibase on startup.

Neon is the intended hosted database for this project. The backend connects to Neon with the standard Spring datasource variables, and Liquibase builds the schema on startup.

Important for the current setup:

- the changelog is written for a fresh database
- if you make incompatible schema-history edits, reset the database and let Liquibase recreate it from scratch

Default local fallback if datasource env vars are missing:

```text
jdbc:postgresql://localhost:5432/vibe_guide?sslmode=disable
username: postgres
password: postgres
```

## Run locally

Use JDK 23.

PowerShell example:

```powershell
$env:JAVA_HOME="$HOME\.jdks\corretto-23.0.2"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw spring-boot:run
```

Compile only:

```powershell
.\mvnw -DskipTests compile
```
