package com.vibe_guide.trait.services.impl;

import com.vibe_guide.trait.services.TraitManagementService;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.exceptions.TraitAlreadyPresentException;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.trait.dtos.TraitInsertRequestDTO;
import com.vibe_guide.trait.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.trait.entities.Trait;
import com.vibe_guide.trait.repositories.TraitRepository;
import com.vibe_guide.trait.utils.TraitResponseMessages;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TraitManagementServiceImpl implements TraitManagementService {

  private final TraitRepository traitRepository;

  @Override
  @Transactional
  public String insert(TraitInsertRequestDTO traitInsertRequestDTO) {
    TraitType traitType = traitInsertRequestDTO.traitType();
    String name = traitInsertRequestDTO.name();
    checkIfTraitExistsByTraitTypeAndName(traitType, name);

    Trait trait = new Trait();
    trait.setTraitType(traitType);
    trait.setName(name);
    traitRepository.save(trait);

    return String.format(TraitResponseMessages.TRAIT_INSERT_MESSAGE, traitType, name);
  }

  @Override
  @Transactional
  public String update(TraitUpdateRequestDTO traitUpdateRequestDTO) {
    TraitType traitType = traitUpdateRequestDTO.traitType();
    String name = traitUpdateRequestDTO.name();
    checkIfTraitExistsByTraitTypeAndName(traitType, name);

    UUID traitId = traitUpdateRequestDTO.traitId();
    Trait trait =
        traitRepository.findById(traitId).orElseThrow(() -> new TraitNotFoundException(traitId));
    trait.setTraitType(traitType);
    trait.setName(name);
    traitRepository.save(trait);

    return String.format(TraitResponseMessages.TRAIT_UPDATE_MESSAGE, traitId);
  }

  @Override
  @Transactional
  public String delete(UUID traitId) {
    Optional<Trait> traitOptional = traitRepository.findById(traitId);
    if (traitOptional.isEmpty()) throw new TraitNotFoundException(traitId);

    traitRepository.deleteById(traitId);

    return String.format(TraitResponseMessages.TRAIT_DELETE_MESSAGE, traitId);
  }

  private void checkIfTraitExistsByTraitTypeAndName(TraitType traitType, String name) {
    Optional<Trait> traitOptional = traitRepository.getTraitByTraitTypeAndName(traitType, name);
    if (traitOptional.isPresent()) throw new TraitAlreadyPresentException(traitType, name);
  }
}

