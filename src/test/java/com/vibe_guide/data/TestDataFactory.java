package com.vibe_guide.data;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;
import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.dtos.OfferInsertDTO;
import com.vibe_guide.dtos.OfferResponseDTO;
import com.vibe_guide.dtos.OfferUpdateDTO;
import com.vibe_guide.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.dtos.ReviewInsertRequestDTO;
import com.vibe_guide.dtos.ReviewResponseDTO;
import com.vibe_guide.dtos.ReviewSearchCriteriaDTO;
import com.vibe_guide.dtos.ReviewUpdateRequestDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitLikeRequestDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import com.vibe_guide.entities.FavouritePlace;
import com.vibe_guide.entities.Offer;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceGallery;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.Review;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.TraitLike;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.VisitedPlace;
import com.vibe_guide.entities.WishlistPlace;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.entities.composite_keys.FavouritePlaceId;
import com.vibe_guide.entities.composite_keys.PlaceAdminId;
import com.vibe_guide.entities.composite_keys.WishlistPlaceId;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.TraitPriority;
import com.vibe_guide.enums.TraitType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Centralized factory for building deterministic test data used across service and controller unit tests.
 */
public final class TestDataFactory {

    private static final byte[] SAMPLE_IMAGE = "image-bytes".getBytes(StandardCharsets.UTF_8);

    private TestDataFactory() {
    }

    public static UUID uuid() {
        return UUID.randomUUID();
    }

    // region Places
    public static Place place(UUID id) {
        Place place = new Place();
        place.setId(id != null ? id : uuid());
        place.setName("Cafe Aurora");
        place.setDescription("Cozy all-day cafe");
        place.setMapsUri("https://maps.example.com/cafe");
        place.setPhoneNumber("123-456");
        place.setAddress("1 Infinite Loop");
        place.setRating(4.7);
        place.setMenuLink("https://example.com/menu");
        place.setPrimaryType(PrimaryType.CAFE);
        place.setPriceLevel(PriceLevel.MODERATE);
        return place;
    }

    public static PlaceRequestDTO placeRequestDto(UUID placeId) {
        return new PlaceRequestDTO(
                placeId != null ? placeId : uuid(),
                "Cafe Aurora",
                "Updated description",
                "https://maps.example.com/cafe",
                "123-456",
                "1 Infinite Loop",
                "https://example.com/menu",
                PrimaryType.CAFE,
                PriceLevel.MODERATE,
                List.of()
        );
    }

    public static PlacePreviewResponseDTO placePreviewResponseDto(UUID id) {
        return new PlacePreviewResponseDTO(
                id != null ? id : uuid(),
                "Cafe Aurora",
                "A boutique coffee spot",
                4.8,
                PrimaryType.CAFE,
                PriceLevel.MODERATE,
                new String[]{"Latte art", "Live music"}
        );
    }

    public static PlaceResponseDTO placeResponseDto() {
        return new PlaceResponseDTO(
                "Cafe Aurora",
                "A boutique coffee spot",
                "https://maps.example.com/cafe",
                "123-456",
                "1 Infinite Loop",
                4.8,
                "https://example.com/menu",
                PrimaryType.CAFE,
                PriceLevel.MODERATE
        );
    }
    // endregion

    // region Traits & Place traits
    public static Trait trait(UUID id) {
        Trait trait = new Trait();
        trait.setId(id != null ? id : uuid());
        trait.setTraitType(TraitType.FOOD);
        trait.setName("Signature dishes");
        return trait;
    }

    public static TraitResponseDTO traitResponseDto(UUID id) {
        return new TraitResponseDTO(id != null ? id : uuid(), TraitType.FOOD, "Signature dishes");
    }

    public static TraitCarouselResponseDTO traitCarouselResponseDto(String name) {
        return new TraitCarouselResponseDTO(name);
    }

    public static TraitInsertRequestDTO traitInsertRequestDto() {
        return new TraitInsertRequestDTO(TraitType.FOOD, "Fusion bites");
    }

    public static TraitUpdateRequestDTO traitUpdateRequestDto(UUID traitId) {
        return new TraitUpdateRequestDTO(traitId != null ? traitId : uuid(), TraitType.FOOD, "Fusion bites");
    }

    public static PlaceTraitRequestDTO placeTraitRequestDto(UUID placeId, UUID traitId) {
        return new PlaceTraitRequestDTO(
                placeId != null ? placeId : uuid(),
                traitId != null ? traitId : uuid(),
                "Chef special",
                TraitPriority.FAVOURITE
        );
    }

    public static BatchInsertTraitsInPlace batchInsertTraitsRequest(List<PlaceTraitRequestDTO> dtos) {
        return new BatchInsertTraitsInPlace(dtos);
    }

    public static BatchDeleteTraitsInPlace batchDeleteTraitsRequest(UUID placeId, List<UUID> traitIds) {
        return new BatchDeleteTraitsInPlace(placeId != null ? placeId : uuid(), traitIds);
    }

    public static PlaceTrait placeTrait(UUID id, Place place, Trait trait) {
        PlaceTrait placeTrait = new PlaceTrait();
        placeTrait.setId(id != null ? id : uuid());
        placeTrait.setPlace(place);
        placeTrait.setTrait(trait);
        placeTrait.setAdditionalInformation("Chef special");
        placeTrait.setPriority(TraitPriority.DEFAULT);
        placeTrait.setLikeCounter(3);
        return placeTrait;
    }

    public static TraitLikeRequestDTO traitLikeRequestDto(UUID userId, UUID placeId, List<UUID> traitIds) {
        return new TraitLikeRequestDTO(
                userId != null ? userId : uuid(),
                placeId != null ? placeId : uuid(),
                traitIds
        );
    }

    public static TraitLike traitLike(PlaceTrait placeTrait, User user) {
        TraitLike like = TraitLike.builder()
                .placeTrait(placeTrait)
                .user(user)
                .build();
        like.setId(uuid());
        return like;
    }
    // endregion

    // region Offers
    public static Offer offer(UUID id, Place place) {
        Offer offer = new Offer();
        offer.setId(id != null ? id : uuid());
        offer.setName("Happy hour");
        offer.setStartDate(LocalDateTime.now());
        offer.setEndDate(LocalDateTime.now().plusHours(2));
        offer.setDescription("Two-for-one drinks");
        offer.setImage(SAMPLE_IMAGE);
        offer.setPlace(place);
        return offer;
    }

    public static OfferInsertDTO offerInsertDto(UUID placeId) {
        return new OfferInsertDTO(
                placeId != null ? placeId : uuid(),
                "Happy hour",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                "Two-for-one drinks",
                SAMPLE_IMAGE
        );
    }

    public static OfferUpdateDTO offerUpdateDto(UUID placeId, UUID offerId) {
        return new OfferUpdateDTO(
                placeId != null ? placeId : uuid(),
                offerId != null ? offerId : uuid(),
                "Late night bites",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                "Snacks and cocktails",
                SAMPLE_IMAGE
        );
    }

    public static OfferResponseDTO offerResponseDto() {
        return new OfferResponseDTO(
                "Happy hour",
                "Cafe Aurora",
                "Two-for-one drinks",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                SAMPLE_IMAGE
        );
    }
    // endregion

    // region Events & galleries
    public static Event event(UUID id, Place place) {
        Event event = new Event();
        event.setId(id != null ? id : uuid());
        event.setName("Acoustic Nights");
        event.setDescription("Live indie music");
        event.setStartDate(LocalDateTime.now().plusDays(1));
        event.setEndDate(LocalDateTime.now().plusDays(1).plusHours(3));
        event.setPlace(place);
        return event;
    }

    public static EventInsertRequestDTO eventInsertRequestDto(UUID placeId) {
        return new EventInsertRequestDTO(
                "Acoustic Nights",
                "Live indie music",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(3),
                placeId != null ? placeId : uuid(),
                List.of(mockImage("event.png"))
        );
    }

    public static EventUpdateRequestDTO eventUpdateRequestDto(UUID eventId, UUID placeId) {
        return new EventUpdateRequestDTO(
                eventId != null ? eventId : uuid(),
                "Acoustic Nights",
                "Updated description",
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(3),
                placeId != null ? placeId : uuid()
        );
    }

    public static EventResponseDTO eventResponseDto() {
        return new EventResponseDTO(
                "Acoustic Nights",
                "Cafe Aurora",
                "Live indie music",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                SAMPLE_IMAGE
        );
    }

    public static EventSearchCriteriaDTO eventSearchCriteriaDto() {
        return new EventSearchCriteriaDTO("Acoustic", LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }

    public static EventGallery eventGallery(UUID id, Event event) {
        EventGallery gallery = new EventGallery();
        gallery.setId(id != null ? id : uuid());
        gallery.setEvent(event);
        gallery.setPhoto(SAMPLE_IMAGE);
        return gallery;
    }

    public static PlaceGallery placeGallery(UUID id, Place place) {
        PlaceGallery gallery = new PlaceGallery();
        gallery.setId(id != null ? id : uuid());
        gallery.setPlace(place);
        gallery.setPhoto(SAMPLE_IMAGE);
        return gallery;
    }
    // endregion

    // region Reviews
    public static Review review(UUID id, User user, Place place) {
        Review review = new Review();
        review.setId(id != null ? id : uuid());
        review.setUser(user);
        review.setPlace(place);
        review.setRating(4.5f);
        review.setDescription("Amazing experience");
        review.setDateCreated(LocalDate.now().minusDays(2));
        review.setDateModified(LocalDate.now().minusDays(1));
        return review;
    }

    public static ReviewInsertRequestDTO reviewInsertRequestDto(UUID userId, UUID placeId) {
        return new ReviewInsertRequestDTO(
                userId != null ? userId : uuid(),
                placeId != null ? placeId : uuid(),
                "Amazing experience",
                4.5f
        );
    }

    public static ReviewUpdateRequestDTO reviewUpdateRequestDto(UUID reviewId) {
        return new ReviewUpdateRequestDTO(reviewId != null ? reviewId : uuid(), "Updated description", 4.0f);
    }

    public static ReviewResponseDTO reviewResponseDto() {
        return new ReviewResponseDTO(
                "john_doe",
                "Cafe Aurora",
                4.5f,
                LocalDate.now().minusDays(2),
                LocalDate.now().minusDays(1),
                "Amazing experience"
        );
    }

    public static ReviewSearchCriteriaDTO reviewSearchCriteriaDto(UUID placeId, UUID userId) {
        return new ReviewSearchCriteriaDTO(placeId, userId, 4.0f);
    }
    // endregion

    // region Users & admins
    public static User user(UUID id) {
        User user = new User();
        user.setId(id != null ? id : uuid());
        user.setUsername("john_doe");
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now().minusDays(30));
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static UserPreviewResponseDTO userPreviewResponseDto(UUID id) {
        return new UserPreviewResponseDTO(
                id != null ? id : uuid(),
                "john_doe",
                "John Doe",
                "john@example.com",
                Role.USER
        );
    }

    public static PlaceAdminRequestDTO placeAdminRequestDto(UUID userId) {
        return new PlaceAdminRequestDTO(userId != null ? userId : uuid());
    }

    public static PlaceAdminResponseDTO placeAdminResponseDto(UUID userId) {
        return new PlaceAdminResponseDTO(userId != null ? userId : uuid(), "john@example.com", "john_doe");
    }

    public static PlaceAdminId placeAdminId(UUID placeId, UUID userId) {
        return new PlaceAdminId(placeId, userId);
    }
    // endregion

    // region Working hours
    public static WorkingHoursRequestDTO workingHoursRequestDto(DayOfWeek dayOfWeek) {
        return new WorkingHoursRequestDTO(
                dayOfWeek != null ? dayOfWeek : DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
    }

    public static WorkingHoursResponseDTO workingHoursResponseDto(DayOfWeek dayOfWeek) {
        return new WorkingHoursResponseDTO(
                dayOfWeek != null ? dayOfWeek : DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
    }

    public static WorkingHoursMissingDaysResponseDTO workingHoursMissingDayDto(DayOfWeek dayOfWeek) {
        return new WorkingHoursMissingDaysResponseDTO(dayOfWeek != null ? dayOfWeek : DayOfWeek.SUNDAY);
    }

    public static WorkingHoursDeleteRequestDTO workingHoursDeleteRequestDto(List<DayOfWeek> days) {
        return new WorkingHoursDeleteRequestDTO(days);
    }

    public static WorkingHours workingHoursEntity(Place place, DayOfWeek dayOfWeek) {
        WorkingHours workingHours = new WorkingHours();
        workingHours.setPlace(place);
        workingHours.setDayOfWeek(dayOfWeek != null ? dayOfWeek : DayOfWeek.MONDAY);
        workingHours.setStartTime(LocalTime.of(9, 0));
        workingHours.setEndTime(LocalTime.of(17, 0));
        return workingHours;
    }
    // endregion

    // region Wishlist / Favourite / Visited
    public static FavouritePlace favouritePlace(User user, Place place) {
        FavouritePlace favouritePlace = new FavouritePlace();
        favouritePlace.setId(new FavouritePlaceId(user.getId(), place.getId()));
        favouritePlace.setUser(user);
        favouritePlace.setPlace(place);
        return favouritePlace;
    }

    public static FavouritePlaceResponseDTO favouritePlaceResponseDto(UUID placeId) {
        return new FavouritePlaceResponseDTO(placeId != null ? placeId : uuid(), "Cafe Aurora", 4.8, "Description");
    }

    public static WishlistPlace wishlistPlace(User user, Place place) {
        WishlistPlace wishlistPlace = new WishlistPlace();
        wishlistPlace.setId(new WishlistPlaceId(user.getId(), place.getId()));
        wishlistPlace.setUser(user);
        wishlistPlace.setPlace(place);
        wishlistPlace.setDateAdded(LocalDateTime.now());
        return wishlistPlace;
    }

    public static WishlistPlaceResponseDTO wishlistPlaceResponseDto(UUID placeId) {
        return new WishlistPlaceResponseDTO(placeId != null ? placeId : uuid(), "Cafe Aurora", 4.8, "Description");
    }

    public static VisitedPlace visitedPlace(User user, Place place) {
        return new VisitedPlace(user, place, LocalDateTime.now().minusDays(1), "Evening visit");
    }

    public static VisitedPlaceResponseDTO visitedPlaceResponseDto(UUID placeId) {
        return new VisitedPlaceResponseDTO(
                placeId != null ? placeId : uuid(),
                "Cafe Aurora",
                4.8,
                "Description",
                LocalDateTime.now().minusDays(1),
                "Evening visit"
        );
    }
    // endregion

    // region Multipart helpers
    public static MultipartFile mockImage(String name) {
        return new MockMultipartFile(name, name, "image/png", SAMPLE_IMAGE);
    }
    // endregion
}
