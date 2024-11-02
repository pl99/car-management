package ru.advantum.car.management.dto;

import lombok.Value;
import ru.advantum.car.management.dao.Car;

import java.time.LocalDate;

/**
 * DTO for {@link Car}
 */
@Value
public class CarDto {
    Long id;
    String registrationNumber;
    String vin;
    String make;
    String model;
    LocalDate productionDate;
}