package ru.advantum.car.management.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import lombok.RequiredArgsConstructor;
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
import ru.advantum.car.management.dao.Ownership;
import ru.advantum.car.management.dao.OwnershipRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/ownerships")
@RequiredArgsConstructor
public class OwnershipController {

    private final OwnershipRepository ownershipRepository;

    private final ObjectPatcher objectPatcher;

    @GetMapping
    public List<Ownership> getList() {
        return ownershipRepository.findAll();
    }

    @GetMapping("{idOwner}")
    public List<Ownership> getListByOwner(@PathVariable Long idOwner) {
        return ownershipRepository.findAllByOwnerId(idOwner);
    }

    @GetMapping("/by-ids")
    public List<Ownership> getMany(@RequestParam List<Long> ids) {
        return ownershipRepository.findAllById(ids);
    }

    @PostMapping
    public Ownership create(@RequestBody Ownership ownership) {
        return ownershipRepository.save(ownership);
    }

    @PatchMapping("/{id}")
    public Ownership patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Ownership ownership = ownershipRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        ownership = objectPatcher.patchAndValidate(ownership, patchNode);

        return ownershipRepository.save(ownership);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Ownership> ownerships = new ArrayList<>(ownershipRepository.findAllById(ids));

        ownerships.replaceAll(ownership -> objectPatcher.patchAndValidate(ownership, patchNode));

        List<Ownership> resultOwnerships = ownershipRepository.saveAll(ownerships);
        return resultOwnerships.stream()
                .map(Ownership::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public Ownership delete(@PathVariable Long id) {
        Ownership ownership = ownershipRepository.findById(id).orElse(null);
        if (ownership != null) {
            ownershipRepository.delete(ownership);
        }
        return ownership;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        ownershipRepository.deleteAllById(ids);
    }
}
