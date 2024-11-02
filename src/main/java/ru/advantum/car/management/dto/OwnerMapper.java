package ru.advantum.car.management.dto;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.advantum.car.management.dao.Owner;

@Mapper
@DecoratedWith(OwnerMapperDecorator.class)
public interface OwnerMapper {
    Owner toEntity(OwnerDto ownerDto);

    @Mapping(target = "ownership", ignore = true)
    OwnerDto toDto(Owner owner);
}