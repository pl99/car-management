package ru.advantum.car.management.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.rautils.patch.ObjectPatcher;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
import ru.advantum.car.management.dao.Car;
import ru.advantum.car.management.dao.CarRepository;
import ru.advantum.car.management.dto.CarDto;
import ru.advantum.car.management.dto.CarMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final CarMapper mapper;

    private final ObjectPatcher objectPatcher;

    @GetMapping
    public List<CarDto> getList() {
        return mapper.toDtos(carRepository.findAll());
    }

    @GetMapping("/{id}")
    public Car getOne(@PathVariable Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        return carOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Car> getMany(@RequestParam List<Long> ids) {
        return carRepository.findAllById(ids);
    }

    @PostMapping
    public Car create(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @PatchMapping("/{id}")
    public Car patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Car car = carRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        car = objectPatcher.patchAndValidate(car, patchNode);

        return carRepository.save(car);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Car> cars = new ArrayList<>(carRepository.findAllById(ids));

        cars.replaceAll(car -> objectPatcher.patchAndValidate(car, patchNode));

        List<Car> resultCars = carRepository.saveAll(cars);
        return resultCars.stream()
                .map(Car::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Car delete(@PathVariable Long id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car != null) {
            carRepository.delete(car);
        }
        return car;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        carRepository.deleteAllById(ids);
    }
}
