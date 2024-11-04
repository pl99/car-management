package ru.advantum.car.management.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.advantum.car.management.dao.Ownership;
import ru.advantum.car.management.dao.OwnershipRepository;
import ru.advantum.car.management.dto.OwnershipDto;
import ru.advantum.car.management.dto.OwnershipMapper;
import ru.advantum.car.management.dto.PurchaseCarDto;
import ru.advantum.car.management.dto.PurchaseCarMapper;
import ru.advantum.car.management.dto.SellCarDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OwnershipService {
    OwnershipRepository ownershipRepository;

    ObjectPatcher objectPatcher;
    OwnershipMapper mapper;
    PurchaseCarMapper purchaseCarMapper;

    public List<OwnershipDto> getList() {
        return ownershipRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<OwnershipDto> getListByOwner(Long idOwner) {
        return ownershipRepository.findAllByOwnerId(idOwner)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public OwnershipDto patch(Long id, @RequestBody JsonNode patchNode) {
        Ownership ownership = ownershipRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        ownership = objectPatcher.patchAndValidate(ownership, patchNode);
        return mapper.toDto(ownershipRepository.save(ownership));
    }

    @Transactional
    public OwnershipDto sell(SellCarDto dto) {
        Ownership os = ownershipRepository.findAllByOwnerId(dto.getOwnerId())
                .stream()
                .filter(it->it.getCarId().equals(dto.getCarId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("car not found in this ownership!"));

        // Бизнеслогика
        Ownership sell = os.toBuilder().saleDate(dto.getSellDate()).build();

        Ownership ownership = ownershipRepository.save(sell);

        return mapper.toDto(ownership);
    }


    @Transactional
    public OwnershipDto purchase(PurchaseCarDto dto) {
        Optional<Ownership> os = ownershipRepository.findByCarIdAndOwnerIdAndSaleDateNull(dto.getCarId(), dto.getOwnerId());
        if(os.isPresent()){
            throw new IllegalArgumentException("car not selled yet!");
        }

        // Бизнеслогика
        Ownership ownership = purchaseCarMapper.toOwnership(dto);

        Ownership saved = ownershipRepository.saveAndFlush(ownership);

        return mapper.toDto(saved);
    }
}
