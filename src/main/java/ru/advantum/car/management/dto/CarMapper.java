package ru.advantum.car.management.dto;

import org.mapstruct.Mapper;
import ru.advantum.car.management.dao.Car;

import java.util.List;

@Mapper
public interface CarMapper {
    Car toEntity(CarDto carDto);

    CarDto toDto(Car car);

    List<CarDto> toDtos(List<Car> cars);

}