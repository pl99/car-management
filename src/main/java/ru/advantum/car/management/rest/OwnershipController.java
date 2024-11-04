package ru.advantum.car.management.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.advantum.car.management.dao.Ownership;
import ru.advantum.car.management.dao.OwnershipRepository;
import ru.advantum.car.management.dto.OwnershipDto;
import ru.advantum.car.management.dto.PurchaseCarDto;
import ru.advantum.car.management.dto.SellCarDto;
import ru.advantum.car.management.service.OwnershipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/ownerships")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OwnershipController {

    OwnershipService service;

    @PostMapping("purchase")
    public ResponseEntity<OwnershipDto> purchase(@RequestBody PurchaseCarDto dto){
        return ResponseEntity.ok(service.purchase(dto));
    }

    @PostMapping("sell")
    public ResponseEntity<OwnershipDto> sell(@RequestBody SellCarDto dto){
        return ResponseEntity.ok(service.sell(dto));
    }

    @GetMapping
    public ResponseEntity<List<OwnershipDto>> getList() {
        return ResponseEntity.ok(service.getList());
    }

    @GetMapping("{idOwner}")
    public ResponseEntity<List<OwnershipDto>> getListByOwner(@PathVariable Long idOwner) {
        return ResponseEntity.ok(service.getListByOwner(idOwner));
    }

    @PatchMapping("/{id}")
    public OwnershipDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        return service.patch(id, patchNode);
    }

}
