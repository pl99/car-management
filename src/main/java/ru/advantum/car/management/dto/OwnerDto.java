package ru.advantum.car.management.dto;

import lombok.Value;

/**
 * DTO for {@link ru.advantum.car.management.dao.Owner}
 */
@Value
public class OwnerDto {
    Long id;
    String name;
    String contactInfo;
}