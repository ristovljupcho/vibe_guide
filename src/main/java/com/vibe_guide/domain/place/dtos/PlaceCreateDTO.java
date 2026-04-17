package com.vibe_guide.domain.place.dtos;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PlaceCreateDTO(
    @NotEmpty String name,
    @NotEmpty String description,
    @NotEmpty String mapsUri,
    @NotEmpty String phoneNumber,
    @NotEmpty String address,
    @NotEmpty String menuLink,
    @NotNull PrimaryType primaryType,
    @NotNull PriceLevel priceLevel,
    List<MultipartFile> images,
    List<@Valid PlaceCreateTraitDTO> traits) {}


