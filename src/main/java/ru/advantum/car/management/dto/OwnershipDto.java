package ru.advantum.car.management.dto;

import lombok.Value;

import java.time.LocalDate;

/**
 * DTO for {@link ru.advantum.car.management.dao.Ownership}
 */
@Value
public class OwnershipDto {
    Long id;
    Long carId;
    Long ownerId;
    LocalDate purchaseDate;
    LocalDate saleDate;
}