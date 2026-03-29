# Vibe Guide

**Vibe Guide** is a Java Spring Boot backend with a React Native (Expo Go) mobile app.  
It helps users discover the best places in Skopje to hang out, enjoy food, drinks, and events.  

---

## ✨ Features
- Categorization of places based on their traits:
  - Music genre
  - Type of venue (restaurant, lounge bar, coffee shop, pub, etc.)
  - Type of food and drinks offered
  - Atmosphere (casual, fancy, cozy, etc.)
- Scoring and grading places based on traits for more accurate recommendations.
- Individual venue profiles where owners can:
  - Publish events (concerts, parties, celebrations, etc.)
  - Share daily highlights (special offers or important announcements).

---

## 🎯 Motivation
We often face the same question: *"Where should we go out with friends?"*  
Vibe Guide was created to make that decision easier — not just for us, but for anyone who wants to explore and get to know Skopje better.  
By applying filters, users can quickly discover a selection of venues that match their preferences.  

---

## 🛠️ Technologies
- **Backend:** Java Spring Boot  
- **Frontend:** React Native (Expo Go)  
- **Database:** PostgreSQL  
- **APIs:** Google Maps API  

---

## 👥 User Roles

### 👤 User
- Edit personal profile.  
- Browse venues, events, and news.  
- Add venues to favorites.  

### 🏪 Admin (Venue Owner/Manager)
- Edit their profile and their venue’s profile.  
- Post events related to their venue.  
- Publish news and daily offers.  

### 🛡 SuperAdmin
- Manage user and venue profiles.  
- Approve new Admin accounts (venue owners/managers).  

---

## 🚀 Getting Started

### Backend Setup
1. Create a Neon project at [neon.com](https://neon.com/) and open the `Connect` modal for your database.
2. Copy the direct `Java / JDBC` connection details.
3. Set the Spring datasource variables before starting the backend.

PowerShell example:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://ep-xxxxx.us-east-2.aws.neon.tech/neondb?sslmode=require&channelBinding=require"
$env:SPRING_DATASOURCE_USERNAME="neondb_owner"
$env:SPRING_DATASOURCE_PASSWORD="your-neon-password"
```

Optional: keep Liquibase on a direct Neon endpoint even if the runtime datasource later uses a pooled `-pooler` host.

```powershell
$env:SPRING_LIQUIBASE_URL=$env:SPRING_DATASOURCE_URL
$env:SPRING_LIQUIBASE_USER=$env:SPRING_DATASOURCE_USERNAME
$env:SPRING_LIQUIBASE_PASSWORD=$env:SPRING_DATASOURCE_PASSWORD
```

Notes:
- This app runs Liquibase migrations on startup, so a direct Neon connection is the safest default.
- Neon recommends TLS plus channel binding for secure Postgres connections, and the JDBC URL above uses the PostgreSQL Java driver's `channelBinding=require` parameter.
- For local Postgres development, the app still falls back to `jdbc:postgresql://localhost:5432/vibe_guide` with `postgres/postgres`.

4. Build and run the backend:  
   ```bash
   ./mvnw spring-boot:run
   ```
