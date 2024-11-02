package ru.advantum.car.management.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import ru.advantum.car.management.dao.Owner;
import ru.advantum.car.management.dao.OwnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerRepository ownerRepository;

    private final ObjectPatcher objectPatcher;

    @GetMapping
    public Page<Owner> getList(@ParameterObject Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Owner getOne(@PathVariable Long id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        return ownerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Owner> getMany(@RequestParam List<Long> ids) {
        return ownerRepository.findAllById(ids);
    }

    @PostMapping
    public Owner create(@RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @PatchMapping("/{id}")
    public Owner patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        owner = objectPatcher.patchAndValidate(owner, patchNode);

        return ownerRepository.save(owner);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Owner> owners = new ArrayList<>(ownerRepository.findAllById(ids));

        owners.replaceAll(owner -> objectPatcher.patchAndValidate(owner, patchNode));

        List<Owner> resultOwners = ownerRepository.saveAll(owners);
        return resultOwners.stream()
                .map(Owner::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public Owner delete(@PathVariable Long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            ownerRepository.delete(owner);
        }
        return owner;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        ownerRepository.deleteAllById(ids);
    }
}
