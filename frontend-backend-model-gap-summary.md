# Frontend to Backend Model Gap Summary

## Matches

These frontend concepts already have clear backend equivalents:

- `VibeEvent` -> `event` domain
- `VibeOffer` -> `offer` domain
- `VibePlace` -> `place` domain
- `SavedCollections` -> favorites, wishlist, visited domains
- traits and place traits
- working hours
- basic user/profile concepts

Relevant backend files:

- `src/main/java/com/vibe_guide/event`
- `src/main/java/com/vibe_guide/offer`
- `src/main/java/com/vibe_guide/place`
- `src/main/java/com/vibe_guide/favouriteplace`
- `src/main/java/com/vibe_guide/wishlistplace`
- `src/main/java/com/vibe_guide/visitedplace`
- `src/main/java/com/vibe_guide/user`

## Partial Matches

### `VibeEvent`

Current backend DTO: `EventResponseDTO`

Already available:

- `name`
- `placeName`
- `description`
- `startDate`
- `endDate`
- `image`

Missing or mismatched:

- `id`
- `title` naming
- `date` as separate formatted field
- `time` as separate formatted field
- `type`
- `timeframe`

### `VibeOffer`

Current backend DTO: `OfferResponseDTO`

Already available:

- `name`
- `placeName`
- `description`
- `startDate`
- `endDate`
- `image`

Missing or mismatched:

- `id`
- `title` naming
- `discount`
- `validUntil`

### `VibePlace`

Current backend DTOs: `PlaceResponseDTO`, `PlacePreviewResponseDTO`

Already available somewhere in backend:

- `id`
- `name`
- `description`
- `rating`
- `type`
- `price`
- gallery data is stored
- traits data is stored
- active/upcoming events and offers can be queried separately
- visited place stores `dateVisited` and `note`

Missing from a single frontend-ready place response:

- `image`
- `gallery`
- `traits: string[]` in the exact frontend shape
- `location`
- `distance`
- `activeEvents`
- `dailyOffers`
- `upcomingEvents`
- `upcomingOffers`
- `userRating`
- `userNote`
- `dateVisited`
- `mapPosition`

### `SavedCollections`

Concept exists, but only through three separate endpoints:

- favorites
- wishlist
- visited

Missing:

- one combined response model returning all three collections together

### `ProfileData`

Backend currently has partial user DTO coverage only.

Missing or not exposed as one payload:

- `initials`
- `fullName` in a dedicated profile response
- `phone`
- `location`
- `bio`
- `stats`

Important note:

- user services exist
- user controller is currently missing from `src/main/java/com/vibe_guide/user/controllers`

## Missing Backend Models or API Shapes

No real backend equivalent exists yet for:

- `HomeFeed`
- `FilterCategory`
- `ProfileStat`
- full `ProfileData`
- `ContactOption`
- `FaqItem`
- `HelpSupportContent`
- unified `SavedCollections`

## Probably Frontend-Only

These may not need backend persistence unless the API should drive UI behavior:

- `MapPosition`
- `ProfileStat.icon`
- `ContactOption.icon`
- `quickFilters`
- `quickLinks`
- `EventDateFilter`
- `SavedCollectionType`

## Extra Concepts Already on Backend

Backend also contains concepts not represented in the provided frontend models:

- working hours
- place admins
- trait likes and trait priority
- auth/register/login/change password services
- separate event and place gallery entities

## Short Summary

### Backend additions most likely needed

1. Extend `EventResponseDTO` with `id` and frontend-ready date/time fields, and possibly event `type` and `timeframe`.
2. Extend `OfferResponseDTO` with `id`, `discount`, and `validUntil`.
3. Add a richer place details DTO that aggregates:
   - gallery
   - traits
   - active events
   - daily offers
   - upcoming events
   - upcoming offers
   - user-specific fields such as `note` and `dateVisited`
4. Add a combined `SavedCollections` response.
5. Add a user profile controller and a dedicated `ProfileData` response model.
6. Add dedicated API responses for `HomeFeed` if the homepage should be backend-driven.
7. Add dedicated API responses for `HelpSupportContent` if FAQ/support content should come from backend.
8. Expose images in a frontend-friendly format consistently, such as URL or base64 string.
