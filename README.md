# Vibe Guide

Vibe Guide is a Spring Boot backend for discovering places, events, offers, and trait-based recommendations in Skopje.

This repository currently contains the backend application only. It exposes REST endpoints for places, events, offers, reviews, favourites, wishlists, visited places, place admins, traits, trait likes, and working hours.

## Stack

- Java 23
- Spring Boot 3.4
- Spring Web
- Spring Data JPA
- PostgreSQL
- Liquibase
- Cloudinary for hosted image storage
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

## Image storage

Images are not stored as `byte[]` blobs anymore.

The current implementation:

- uploads place and event gallery images to Cloudinary
- stores the returned hosted URL in the database
- returns image URLs from the API

Relevant code:

- [CloudinaryImageStorageService.java](C:/Users/Ljupcho.Ristov/IdeaProjects/vibe_guide/src/main/java/com/vibe_guide/storage/CloudinaryImageStorageService.java)
- [CloudinaryConfig.java](C:/Users/Ljupcho.Ristov/IdeaProjects/vibe_guide/src/main/java/com/vibe_guide/storage/CloudinaryConfig.java)

## Environment variables

The backend reads its configuration from environment variables.

Required:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `CLOUDINARY_URL`

Optional:

- `SPRING_LIQUIBASE_URL`
- `SPRING_LIQUIBASE_USER`
- `SPRING_LIQUIBASE_PASSWORD`
- `SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE`
- `SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE`
- `APP_STORAGE_CLOUDINARY_FOLDER`

`CLOUDINARY_URL` format:

```text
cloudinary://<api_key>:<api_secret>@<cloud_name>
```

Example PowerShell session:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://ep-xxxxx-pooler.eu-central-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require"
$env:SPRING_DATASOURCE_USERNAME="neondb_owner"
$env:SPRING_DATASOURCE_PASSWORD="your-password"
$env:CLOUDINARY_URL="cloudinary://<api_key>:<api_secret>@<cloud_name>"
$env:APP_STORAGE_CLOUDINARY_FOLDER="vibe-guide-demo"
```

## Database and Liquibase

The app runs Liquibase on startup.

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

## API notes

Notable current API behavior:

- `PUT /places/update` uses `multipart/form-data` via `@ModelAttribute`
- `POST /events/insert` uses `multipart/form-data` via `@ModelAttribute`
- offer create/update uses image URLs, not raw image bytes
- place responses include `imageUrls`
- event and offer responses include `imageUrl`

## Quick image test

Example place image upload:

```powershell
curl.exe -X PUT "http://localhost:8080/places/update" `
  -F "placeId=22222222-2222-2222-2222-222222222001" `
  -F "name=Pulse Coffee Lab" `
  -F "description=Modern specialty coffee spot with single-origin brews and relaxed daytime energy." `
  -F "mapsUri=https://maps.google.com/?q=Pulse+Coffee+Lab+Skopje" `
  -F "phoneNumber=+389 2 111 0101" `
  -F "address=Partizanski Odredi 15, Skopje" `
  -F "menuLink=https://pulsecoffeelab.mk/menu" `
  -F "primaryType=COFFEE_SHOP" `
  -F "priceLevel=MODERATE" `
  -F "images=@C:\Users\Ljupcho.Ristov\Desktop\placeImage.jpg"
```

Then fetch the place:

```powershell
Invoke-WebRequest -UseBasicParsing http://localhost:8080/places/22222222-2222-2222-2222-222222222001
```

The response should include a Cloudinary URL in `imageUrls`.
