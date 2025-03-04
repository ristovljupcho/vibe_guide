package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.exceptions.TraitAlreadyPresentException;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.services.TraitManagementService;
import com.vibe_guide.utils.TraitResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TraitManagementServiceImpl implements TraitManagementService {

    private final TraitRepository traitRepository;

    /**
     * Inserts a new {@link Trait} with provided {@link TraitInsertRequestDTO}.
     *
     * @param traitInsertRequestDTO DTO used to insert new {@link Trait} by providing TraitType traitType and String
     *                              name.
     * @return Response message of type {@link TraitResponseMessages}.
     */
    @Override
    @Transactional
    public String insertTrait(TraitInsertRequestDTO traitInsertRequestDTO) {
        TraitType traitType = traitInsertRequestDTO.traitType();
        String name = traitInsertRequestDTO.name();
        checkIfTraitExistsByTraitTypeAndName(traitType, name);

        Trait trait = new Trait();
        trait.setTraitType(traitType);
        trait.setName(name);
        traitRepository.save(trait);

        return TraitResponseMessages.TRAIT_INSERT_MESSAGE;
    }

    /**
     * Updates a {@link Trait} object with provided {@link TraitUpdateRequestDTO}.
     *
     * @param traitUpdateRequestDTO DTO used to update {@link Trait} object by providing UUID traitId, TraitType
     *                              traitType and String name.
     * @return Response message of type {@link TraitResponseMessages}.
     */
    @Override
    @Transactional
    public String updateTrait(TraitUpdateRequestDTO traitUpdateRequestDTO) {
        TraitType traitType = traitUpdateRequestDTO.traitType();
        String name = traitUpdateRequestDTO.name();
        checkIfTraitExistsByTraitTypeAndName(traitType, name);

        UUID traitId = traitUpdateRequestDTO.traitId();
        Trait trait = traitRepository.findById(traitId).orElseThrow(() -> new TraitNotFoundException(traitId));
        trait.setTraitType(traitType);
        trait.setName(name);
        traitRepository.save(trait);

        return TraitResponseMessages.TRAIT_UPDATE_MESSAGE;
    }

    /**
     * Deletes a {@link Trait} object with provided <b><i>UUID traitId</i></b>.
     *
     * @param traitId UUID of the {@link Trait} object that needs to be deleted.
     * @return Response message of type {@link TraitResponseMessages}.
     */
    @Override
    @Transactional
    public String deleteTrait(UUID traitId) {
        Optional<Trait> traitOptional = traitRepository.findById(traitId);
        if (traitOptional.isEmpty())
            throw new TraitNotFoundException(traitId);

        traitRepository.deleteById(traitId);

        return TraitResponseMessages.TRAIT_DELETE_MESSAGE;
    }

    /**
     * Checks if provided TraitType traitType and String name matches any other {@link Trait} in database.
     *
     * @param traitType {@link TraitType} type of {@link Trait} object.
     * @param name      Name of {@link Trait} object.
     */
    private void checkIfTraitExistsByTraitTypeAndName(TraitType traitType, String name) {
        Optional<Trait> traitOptional = traitRepository.getTraitByTraitTypeAndName(traitType, name);
        if (traitOptional.isPresent())
            throw new TraitAlreadyPresentException(traitType, name);
    }
}