package ru.advantum.car.management.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.advantum.car.management.dto.OwnershipDto;
import ru.advantum.car.management.dto.OwnershipMapper;
import ru.advantum.car.management.dto.SellCarDto;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "ownerships")
@Getter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ownership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "car_id", nullable = false)
    Long carId;

    @Column(name = "owner_id", nullable = false)
    Long ownerId;

    @Column(nullable = false)
    LocalDate purchaseDate;

    @Column
    LocalDate saleDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Ownership ownership = (Ownership) o;
        return getId() != null && Objects.equals(getId(), ownership.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public OwnershipDto sale(SellCarDto dto) {
        Ownership forSale = findForSale(dto)
                .toBuilder().saleDate(dto.getSaleDate()).build();
        return forSale.save().toDto();

    }

    public OwnershipDto sale() {
//        LocalDate saledate = this.saleDate;
        Ownership forSale = findForSale().toBuilder()
                .saleDate(this.saleDate)
                .build();
        return Mapper.mapper.toDto(forSale.save());
    }

    public Ownership findForSale() {
        return Repository.repository.findAllByOwnerIdAndCarIdAndSaleDateNull(this.getOwnerId(), this.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("car not found in this ownership!"));
    }

    public Ownership  findForSale(SellCarDto dto) {
        return Repository.repository.findAllByOwnerId(dto.getOwnerId())
                .stream()
                .filter(it -> it.getCarId().equals(dto.getCarId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("car not found in this ownership!"));
    }

    public Ownership save() {
        return Repository.repository.saveAndFlush(this);
    }


    public OwnershipDto toDto() {
        return Mapper.mapper.toDto(this);
    }


    @Component("dddRepositoryOwnership")
    private static class Repository {
        private static OwnershipRepository repository;

        @Autowired
        void setRepository(OwnershipRepository component) {
            repository = component;
        }
    }

    @Component("dddMapperOwnership")
    private static class Mapper {
        private static OwnershipMapper mapper;

        @Autowired
        void setMapper(OwnershipMapper component) {
            mapper = component;
        }
    }

}
