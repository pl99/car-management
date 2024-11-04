package ru.advantum.car.management.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.advantum.car.management.dao.Ownership;

@Mapper
public interface PurchaseCarMapper {

    @Mapping(target = "saleDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Ownership toOwnership(PurchaseCarDto dto);
}
