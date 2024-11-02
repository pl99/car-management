package ru.advantum.car.management.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO for {@link ru.advantum.car.management.dao.Owner}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class OwnerDto {
    Long id;
    String name;
    String contactInfo;

    List<CarDto> ownership;
}