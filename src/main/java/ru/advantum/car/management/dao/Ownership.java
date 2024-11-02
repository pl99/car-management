package ru.advantum.car.management.dao;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Table(name = "ownerships")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ownership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    Car car;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    Owner owner;

    @Column(nullable = false)
    LocalDate purchaseDate;

    @Column
    LocalDate saleDate;
}
