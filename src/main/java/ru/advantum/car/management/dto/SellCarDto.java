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
import ru.advantum.car.management.dao.OwnershipRepository;

import java.time.LocalDate;

    @AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
    public class SellCarDto {
        Long carId;
        Long ownerId;
        LocalDate saleDate;


        public Ownership findForSale() {
            return SellCarDto.Repository.repository.findAllByOwnerId(this.getOwnerId())
                    .stream()
                    .filter(it -> it.getCarId().equals(this.getCarId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("car not found in this ownership!"));
        }
        //<editor-fold>

        public Ownership makeOwnership() {
            return Mapper.mapper.toOwnership(this);
        }

        @Component("dddMapperSellCarDto")
        private static class Mapper {
            private static SellCarDtoMapper mapper;

            @Autowired
            void setMapper(SellCarDtoMapper component) {
                mapper = component;
            }
        }
        @Component("dddRepositorySellCarDto")
            private static class Repository {
                private static OwnershipRepository repository;

                @Autowired
                void setRepository(OwnershipRepository component) {
                    repository = component;
                }
            }

        void setMapper(SellCarDtoMapper mapper){
            Mapper.mapper=mapper;
        }
        //</editor-fold>
    }
