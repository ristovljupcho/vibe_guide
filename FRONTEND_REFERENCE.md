# Frontend Reference

This file is the frontend-facing reference for the current backend contract.

Use it as the quick source of truth for:
- database tables and columns
- controller base paths
- endpoint request shapes
- endpoint response shapes

## How To Read This File

- `Request body` means JSON unless `multipart/form-data` is explicitly stated.
- `Response` shows the DTO or wrapper returned by the endpoint.
- `Page<...>` means Spring pagination, so the response includes paginated metadata plus a `content` array.

## Database Schema

### Core Catalog Tables

#### `place`

| Column | Type / Meaning |
| --- | --- |
| `id` | Place UUID |
| `name` | Place name |
| `description` | Place description |
| `maps_uri` | Google Maps or external map link |
| `phone_number` | Contact phone number |
| `address` | Address text |
| `rating` | Calculated place rating |
| `menu_link` | Menu URL |
| `price_level` | Enum value |
| `primary_type` | Enum value |
| `created_at` | Created timestamp |
| `updated_at` | Updated timestamp |
| `created_by` | Creator identifier |
| `updated_by` | Last updater identifier |

#### `trait`

| Column | Type / Meaning |
| --- | --- |
| `id` | Trait UUID |
| `name` | Trait name |
| `trait_type` | Trait category enum |

#### `place_trait`

| Column | Type / Meaning |
| --- | --- |
| `id` | Place-trait UUID |
| `place_id` | Related place |
| `trait_id` | Related trait |
| `like_counter` | Number of likes |
| `priority` | Trait priority enum |
| `additional_information` | Extra free-text note |

#### `working_hours`

| Column | Type / Meaning |
| --- | --- |
| `day_of_week` | Day enum |
| `start_time` | Opening time |
| `end_time` | Closing time |
| `place_id` | Related place |

### Media And Promotions

#### `offer`

| Column | Type / Meaning |
| --- | --- |
| `id` | Offer UUID |
| `name` | Offer name |
| `date_created` | Created timestamp |
| `start_date` | Offer start |
| `end_date` | Offer end |
| `description` | Offer description |
| `image` | Image URL |
| `place_id` | Related place |

#### `event`

| Column | Type / Meaning |
| --- | --- |
| `id` | Event UUID |
| `name` | Event name |
| `date_created` | Created timestamp |
| `description` | Event description |
| `start_date` | Event start |
| `end_date` | Event end |
| `place_id` | Related place |
| `entry_fee` | Event price / fee |

#### `event_gallery`

| Column | Type / Meaning |
| --- | --- |
| `id` | Gallery row UUID |
| `event_id` | Related event |
| `photo` | Image URL |

#### `place_gallery`

| Column | Type / Meaning |
| --- | --- |
| `id` | Gallery row UUID |
| `place_id` | Related place |
| `photo` | Image URL |

### Users And User Activity

#### `user_table`

| Column | Type / Meaning |
| --- | --- |
| `id` | User UUID |
| `username` | Username |
| `name` | Full name |
| `email` | Email |
| `role` | User role enum |
| `password` | Stored password hash/value |
| `created_at` | Created timestamp |
| `updated_at` | Updated timestamp |

#### `review`

| Column | Type / Meaning |
| --- | --- |
| `id` | Review UUID |
| `date_created` | Created date |
| `date_modified` | Last modified date |
| `rating` | Review rating |
| `description` | Review text |
| `user_id` | Related user |
| `place_id` | Related place |

#### `trait_like`

| Column | Type / Meaning |
| --- | --- |
| `id` | Like UUID |
| `place_trait_id` | Related place trait |
| `user_id` | Related user |
| `created_at` | Created timestamp |

#### `visited_place`

| Column | Type / Meaning |
| --- | --- |
| `user_id` | Related user |
| `place_id` | Related place |
| `created_at` | Saved timestamp |
| `note` | Optional user note |

#### `wishlist_place`

| Column | Type / Meaning |
| --- | --- |
| `user_id` | Related user |
| `place_id` | Related place |
| `created_at` | Saved timestamp |
| `note` | Optional user note |

#### `favourite_place`

| Column | Type / Meaning |
| --- | --- |
| `user_id` | Related user |
| `place_id` | Related place |
| `created_at` | Saved timestamp |
| `note` | Optional user note |

#### `place_admin`

| Column | Type / Meaning |
| --- | --- |
| `place_id` | Related place |
| `user_id` | Related user |
| `date_created` | Created timestamp |

### Materialized Views

#### `trait_likes_summary`

| Column | Meaning |
| --- | --- |
| `place_id` | Place UUID |
| `trait_id` | Trait UUID |
| `total_likes` | Aggregated likes |

#### `place_top_traits`

| Column | Meaning |
| --- | --- |
| `place_id` | Place UUID |
| `trait_id` | Trait UUID |
| `rank_num` | Ranking position |

## API Reference

## Events

Controller: `EventController`  
Base path: `/events`

### `GET /events/paginated`

Returns paginated events with optional filtering by date range.

**Query params**

| Name | Type | Required |
| --- | --- | --- |
| `startDate` | `LocalDateTime` | No |
| `endDate` | `LocalDateTime` | No |
| `page` | `int` | Yes |
| `size` | `int` | Yes |

**Response**

`Page<EventResponseDTO>`

`EventResponseDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |
| `placeName` | `String` |
| `description` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `imageUrl` | `String` |

### `GET /events/past/{placeId}`

Returns past events for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<EventResponseDTO>`

### `GET /events/upcoming`

Returns upcoming events across all places.

**Response**

`List<EventResponseDTO>`

### `GET /events/upcoming/{placeId}`

Returns upcoming events for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<EventResponseDTO>`

### `GET /events/active`

Returns currently active events across all places.

**Response**

`List<EventResponseDTO>`

### `GET /events/active/{placeId}`

Returns currently active events for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<EventResponseDTO>`

### `POST /events/insert`

Creates a new event.

**Request type**

`multipart/form-data`

**Request DTO**

`EventInsertRequestDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |
| `description` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `placeId` | `UUID` |
| `images` | `List<MultipartFile>` |

**Response**

`String`

### `PUT /events/update`

Updates an existing event.

**Request DTO**

`EventUpdateRequestDTO`

| Field | Type |
| --- | --- |
| `eventId` | `UUID` |
| `name` | `String` |
| `description` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `placeId` | `UUID` |

**Response**

`String`

### `DELETE /events/{eventId}/delete`

Deletes an event.

**Path params**

| Name | Type |
| --- | --- |
| `eventId` | `UUID` |

**Response**

`String`

## Favourite Places

Controller: `FavouritePlaceController`  
Base path: `/favourite-places`

### `GET /favourite-places/{userId}`

Returns all favourite places for one user.

**Path params**

| Name | Type |
| --- | --- |
| `userId` | `UUID` |

**Response**

`List<FavouritePlaceResponseDTO>`

`FavouritePlaceResponseDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `name` | `String` |
| `rating` | `double` |
| `description` | `String` |
| `createdAt` | `LocalDateTime` |
| `note` | `String` |

### `POST /favourite-places/toggle`

Toggles a place as favourite for a user.

**Request DTO**

`FavouritePlaceToggleRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `note` | `String` |

**Response**

`String`

## Offers

Controller: `OfferController`  
Base path: `/offers`

### `GET /offers/active`

Returns currently active offers across all places.

**Response**

`List<OfferResponseDTO>`

`OfferResponseDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |
| `placeName` | `String` |
| `description` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `imageUrl` | `String` |

### `GET /offers/active/{placeId}`

Returns active offers for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<OfferResponseDTO>`

### `GET /offers/upcoming`

Returns upcoming offers across all places.

**Response**

`List<OfferResponseDTO>`

### `GET /offers/upcoming/{placeId}`

Returns upcoming offers for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<OfferResponseDTO>`

### `POST /offers/insert`

Creates a new offer.

**Request DTO**

`OfferInsertDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `name` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `description` | `String` |
| `imageUrl` | `String` |

**Response**

`String`

### `PUT /offers/update`

Updates an existing offer.

**Request DTO**

`OfferUpdateDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `dailyOfferId` | `UUID` |
| `name` | `String` |
| `startDate` | `LocalDateTime` |
| `endDate` | `LocalDateTime` |
| `description` | `String` |
| `imageUrl` | `String` |

**Response**

`String`

### `DELETE /offers/delete/{dailyOfferId}`

Deletes an offer.

**Path params**

| Name | Type |
| --- | --- |
| `dailyOfferId` | `UUID` |

**Response**

`String`

## Places

Controller: `PlaceController`  
Base path: `/places`

### `GET /places`

Returns all places with optional filtering and sorting.

**Query params**

| Name | Type | Required |
| --- | --- | --- |
| `traits` | `List<String>` | No |
| `sortBy` | `String` | No |
| `sortDirection` | `String` | No |

**Response**

`List<PlacePreviewResponseDTO>`

`PlacePreviewResponseDTO`

| Field | Type |
| --- | --- |
| `id` | `UUID` |
| `name` | `String` |
| `description` | `String` |
| `rating` | `double` |
| `primaryType` | `PrimaryType` |
| `priceLevel` | `PriceLevel` |
| `topTraits` | `String[]` |

### `GET /places/top`

Returns top places.

**Response**

`List<PlacePreviewResponseDTO>`

### `GET /places/{placeId}`

Returns full details for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`PlaceResponseDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |
| `description` | `String` |
| `mapsUri` | `String` |
| `phoneNumber` | `String` |
| `address` | `String` |
| `rating` | `double` |
| `menuLink` | `String` |
| `primaryType` | `PrimaryType` |
| `priceLevel` | `PriceLevel` |
| `imageUrls` | `List<String>` |

### `POST /places/create`

Creates a place.

**Request type**

`multipart/form-data`

**Request DTO**

`PlaceCreateDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |
| `description` | `String` |
| `mapsUri` | `String` |
| `phoneNumber` | `String` |
| `address` | `String` |
| `menuLink` | `String` |
| `primaryType` | `PrimaryType` |
| `priceLevel` | `PriceLevel` |
| `images` | `List<MultipartFile>` |
| `traits` | `List<PlaceCreateTraitDTO>` |

`PlaceCreateTraitDTO`

| Field | Type |
| --- | --- |
| `traitId` | `UUID` |
| `additionalInformation` | `String` |
| `priority` | `TraitPriority` |

**Response**

`PlaceResponseDTO`

### `PUT /places/update`

Updates a place.

**Request type**

`multipart/form-data`

**Request DTO**

`PlaceUpdateDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `name` | `String` |
| `description` | `String` |
| `mapsUri` | `String` |
| `phoneNumber` | `String` |
| `address` | `String` |
| `menuLink` | `String` |
| `primaryType` | `PrimaryType` |
| `priceLevel` | `PriceLevel` |

**Response**

`String`

### `DELETE /places/delete/{placeId}`

Deletes a place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`String`

## Place Admins

Controller: `PlaceAdminController`  
Base path: `/places/{placeId}`

### `GET /places/{placeId}/admins`

Returns admins for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<PlaceAdminResponseDTO>`

`PlaceAdminResponseDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `email` | `String` |
| `username` | `String` |

### `POST /places/{placeId}/admins/insert`

Adds an admin to a place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Request DTO**

`PlaceAdminRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |

**Response**

`String`

### `DELETE /places/{placeId}/admins/delete/{userId}`

Removes an admin from a place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |
| `userId` | `UUID` |

**Response**

`String`

## Place Traits

Controller: `PlaceTraitController`  
Base path: `/places`

### `GET /places/{placeId}/traits/carousel`

Returns simple trait names for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<TraitCarouselResponseDTO>`

`TraitCarouselResponseDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |

### `GET /places/{placeId}/top-traits`

Returns top-ranked traits for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<TraitResponseDTO>`

`TraitResponseDTO`

| Field | Type |
| --- | --- |
| `traitId` | `UUID` |
| `traitType` | `TraitType` |
| `name` | `String` |

### `GET /places/{placeId}/missing-traits`

Returns traits not yet assigned to the place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<TraitResponseDTO>`

### `POST /places/insert-trait`

Adds one trait to a place.

**Request DTO**

`PlaceTraitRequestDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `traitId` | `UUID` |
| `additionalInformation` | `String` |
| `priority` | `TraitPriority` |

**Response**

`String`

### `POST /places/batch-insert-traits`

Adds multiple traits to a place.

**Request DTO**

`BatchInsertTraitsInPlace`

| Field | Type |
| --- | --- |
| `placeTraitRequestDTOs` | `List<PlaceTraitRequestDTO>` |

**Response**

`String`

### `PUT /places/traits/update`

Updates one place-trait relation.

**Request DTO**

`PlaceTraitRequestDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `traitId` | `UUID` |
| `additionalInformation` | `String` |
| `priority` | `TraitPriority` |

**Response**

`String`

### `DELETE /places/traits/delete/{placeTraitId}`

Deletes one place-trait relation.

**Path params**

| Name | Type |
| --- | --- |
| `placeTraitId` | `UUID` |

**Response**

`String`

### `DELETE /places/batch-delete-traits`

Deletes multiple traits from a place.

**Request DTO**

`BatchDeleteTraitsInPlace`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `traitIds` | `List<UUID>` |

**Response**

`String`

## Reviews

Controller: `ReviewController`  
Base path: `/reviews`

### `GET /reviews/{placeId}`

Returns paginated reviews for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Query params**

| Name | Type | Required |
| --- | --- | --- |
| `sortBy` | `String` | No |
| `sortDirection` | `String` | No |
| `page` | `int` | Yes |
| `size` | `int` | Yes |

**Response**

`Page<ReviewResponseDTO>`

`ReviewResponseDTO`

| Field | Type |
| --- | --- |
| `username` | `String` |
| `placeName` | `String` |
| `rating` | `Float` |
| `dateCreated` | `LocalDate` |
| `dateModified` | `LocalDate` |
| `description` | `String` |

### `GET /reviews/{placeId}/top`

Returns top reviews for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<ReviewResponseDTO>`

### `POST /reviews/insert`

Creates a review.

**Request DTO**

`ReviewInsertRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `description` | `String` |
| `rating` | `Float` |

**Response**

`String`

### `PUT /reviews/update`

Updates a review.

**Request DTO**

`ReviewUpdateRequestDTO`

| Field | Type |
| --- | --- |
| `reviewId` | `UUID` |
| `description` | `String` |
| `rating` | `Float` |

**Response**

`String`

### `DELETE /reviews/{reviewId}/delete`

Deletes a review.

**Path params**

| Name | Type |
| --- | --- |
| `reviewId` | `UUID` |

**Response**

`String`

## Traits

Controller: `TraitController`  
Base path: `/traits`

### `GET /traits/paginated`

Returns paginated traits with optional filtering and sorting.

**Query params**

| Name | Type | Required |
| --- | --- | --- |
| `traitType` | `TraitType` | No |
| `sortBy` | `String` | No |
| `sortDirection` | `String` | No |
| `page` | `int` | Yes |
| `size` | `int` | Yes |

**Response**

`Page<TraitResponseDTO>`

`TraitResponseDTO`

| Field | Type |
| --- | --- |
| `traitId` | `UUID` |
| `traitType` | `TraitType` |
| `name` | `String` |

### `GET /traits`

Returns trait names for carousel-style usage.

**Response**

`List<TraitCarouselResponseDTO>`

`TraitCarouselResponseDTO`

| Field | Type |
| --- | --- |
| `name` | `String` |

### `POST /traits/insert`

Creates a trait.

**Request DTO**

`TraitInsertRequestDTO`

| Field | Type |
| --- | --- |
| `traitType` | `TraitType` |
| `name` | `String` |

**Response**

`String`

### `PUT /traits/update`

Updates a trait.

**Request DTO**

`TraitUpdateRequestDTO`

| Field | Type |
| --- | --- |
| `traitId` | `UUID` |
| `traitType` | `TraitType` |
| `name` | `String` |

**Response**

`String`

### `DELETE /traits/delete/{traitId}`

Deletes a trait.

**Path params**

| Name | Type |
| --- | --- |
| `traitId` | `UUID` |

**Response**

`String`

## Trait Likes

Controller: `TraitLikeController`  
Base path: `/traits`

### `GET /traits/likes`

Returns traits liked by a user for a specific place.

**Query params**

| Name | Type | Required |
| --- | --- | --- |
| `userId` | `UUID` | Yes |
| `placeId` | `UUID` | Yes |

**Response**

`List<TraitResponseDTO>`

### `POST /traits/like`

Adds likes for one or more traits.

**Request DTO**

`TraitLikeRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `traitIds` | `List<UUID>` |

**Response**

`String`

### `POST /traits/unlike`

Removes likes for one or more traits.

**Request DTO**

`TraitLikeRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `traitIds` | `List<UUID>` |

**Response**

`String`

## Visited Places

Controller: `VisitedPlaceController`  
Base path: `/visited-places`

### `GET /visited-places/{userId}`

Returns visited places for one user.

**Path params**

| Name | Type |
| --- | --- |
| `userId` | `UUID` |

**Response**

`List<VisitedPlaceResponseDTO>`

`VisitedPlaceResponseDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `placeName` | `String` |
| `rating` | `double` |
| `description` | `String` |
| `createdAt` | `LocalDateTime` |
| `note` | `String` |

### `POST /visited-places/toggle`

Toggles a place as visited for a user.

**Request DTO**

`VisitedPlaceToggleRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `note` | `String` |

**Response**

`String`

## Wishlist Places

Controller: `WishlistPlaceController`  
Base path: `/wishlist-places`

### `GET /wishlist-places/{userId}`

Returns wishlist places for one user.

**Path params**

| Name | Type |
| --- | --- |
| `userId` | `UUID` |

**Response**

`List<WishlistPlaceResponseDTO>`

`WishlistPlaceResponseDTO`

| Field | Type |
| --- | --- |
| `placeId` | `UUID` |
| `name` | `String` |
| `rating` | `double` |
| `description` | `String` |
| `createdAt` | `LocalDateTime` |
| `note` | `String` |

### `POST /wishlist-places/toggle`

Toggles a place into or out of the wishlist for a user.

**Request DTO**

`WishlistPlaceToggleRequestDTO`

| Field | Type |
| --- | --- |
| `userId` | `UUID` |
| `placeId` | `UUID` |
| `note` | `String` |

**Response**

`String`

## Working Hours

Controller: `WorkingHoursController`  
Base path: `/places/{placeId}`

### `GET /places/{placeId}/working-hours`

Returns working hours for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Response**

`List<WorkingHoursResponseDTO>`

`WorkingHoursResponseDTO`

| Field | Type |
| --- | --- |
| `dayOfWeek` | `DayOfWeek` |
| `startTime` | `LocalTime` |
| `endTime` | `LocalTime` |

### `POST /places/{placeId}/working-hours/insert`

Inserts multiple working-hours rows for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Request DTO**

`List<WorkingHoursRequestDTO>`

`WorkingHoursRequestDTO`

| Field | Type |
| --- | --- |
| `dayOfWeek` | `DayOfWeek` |
| `startTime` | `LocalTime` |
| `endTime` | `LocalTime` |

**Response**

`String`

### `PUT /places/{placeId}/working-hours/update`

Updates one working-hours row for one place.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Request DTO**

`WorkingHoursRequestDTO`

| Field | Type |
| --- | --- |
| `dayOfWeek` | `DayOfWeek` |
| `startTime` | `LocalTime` |
| `endTime` | `LocalTime` |

**Response**

`String`

### `DELETE /places/{placeId}/working-hours/delete`

Deletes working-hours rows for selected days.

**Path params**

| Name | Type |
| --- | --- |
| `placeId` | `UUID` |

**Request DTO**

`WorkingHoursDeleteRequestDTO`

| Field | Type |
| --- | --- |
| `daysToDelete` | `List<DayOfWeek>` |

**Response**

`String`
