package ru.advantum.car.management.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.advantum.car.management.dao.Car;
import ru.advantum.car.management.dao.CarRepository;
import ru.advantum.car.management.dao.Ownership;

import java.util.List;

@Component
public abstract class OwnershipMapperDecorator implements OwnershipMapper {
    @Autowired
    @Qualifier("delegate")
    private OwnershipMapper mapper;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper carMapper;


    public List<CarDto> toCarDtos(List<Ownership> ownerships) {
        List<Long> carIds = ownerships.stream().map(Ownership::getCarId).toList();
        return carRepository.findAllByIdIn(carIds).stream()
                .map(it -> carMapper.toDto(it))
                .toList();
    }
}
