package ru.advantum.car.management.dto;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.advantum.car.management.dao.Ownership;

import java.util.List;

@Mapper
@DecoratedWith(OwnershipMapperDecorator.class)
public interface OwnershipMapper {
    Ownership toEntity(OwnershipDto ownershipDto);

    OwnershipDto toDto(Ownership ownership);

    @Mapping(target = "vin", ignore = true)
    @Mapping(target = "registrationNumber", ignore = true)
    @Mapping(target = "productionDate", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "make", ignore = true)
    List<CarDto> toCarDtos(List<Ownership> ownerships);

    @Mapping(target = "vin", ignore = true)
    @Mapping(target = "registrationNumber", ignore = true)
    @Mapping(target = "productionDate", ignore = true)
    @Mapping(target = "model", ignore = true)
    @Mapping(target = "make", ignore = true)
    CarDto toCarDto(Ownership ownership);

}