package ru.advantum.car.management.dto;

import org.mapstruct.Mapper;
import ru.advantum.car.management.dao.Ownership;

@Mapper
public interface OwnershipMapper {
    Ownership toEntity(OwnershipDto ownershipDto);

    OwnershipDto toDto(Ownership ownership);

}