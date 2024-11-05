package ru.advantum.car.management.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.advantum.car.management.dao.Ownership;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class PurchaseCarDto {
    Long carId;
    Long ownerId;
    LocalDate purchaseDate;

    public Ownership toOwnership(){
        return Mapper.mapper.toOwnership(this);
    }

    @Component("dddMapperPurchaseCarDto")
    private static class Mapper {
        private static PurchaseCarMapper mapper;

        @Autowired
        void setMapper(PurchaseCarMapper component) {
            mapper = component;
        }
    }

}
