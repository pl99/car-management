package ru.advantum.car.management.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.advantum.car.management.dao.Owner;
import ru.advantum.car.management.dao.Ownership;
import ru.advantum.car.management.dao.OwnershipRepository;

import java.util.List;

@Component
public abstract class OwnerMapperDecorator implements OwnerMapper{
    @Autowired
    @Qualifier("delegate")
    private OwnerMapper delegate;
    @Autowired
    private OwnershipRepository ownershipRepository;
    @Autowired
    private OwnershipMapper ownershipMapper;

    @Override
    public OwnerDto toDto(Owner owner) {
        OwnerDto dto = delegate.toDto(owner);
        List<Ownership> ownerships = ownershipRepository.findAllByOwnerId(owner.getId());
        List<CarDto> carDtos = ownershipMapper.toCarDtos(ownerships);
        return dto.toBuilder()
                .ownership(carDtos)
                .build();
    }
}
