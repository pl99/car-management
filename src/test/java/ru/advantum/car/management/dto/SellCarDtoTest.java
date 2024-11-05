package ru.advantum.car.management.dto;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.advantum.car.management.dao.Ownership;

import java.time.LocalDate;

@Slf4j
class SellCarDtoTest {


    @SneakyThrows
    @Test
    void makeOwnership() {
        LocalDate expected = LocalDate.of(2024, 11, 4);
        SellCarDto dto = SellCarDto.builder()
                .carId(5L)
                .ownerId(1L)
                .saleDate(expected)
                .build();
        SellCarDtoMapper mapper = new SellCarDtoMapperImpl();
        dto.setMapper(mapper);
        Ownership ownership = dto.makeOwnership();
        log.info("{}", ownership);
        Assertions.assertEquals(expected, ownership.getSaleDate());
    }
}