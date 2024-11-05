package ru.advantum.car.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findAllByOwnerId(Long ownerId);


    Optional<Ownership> findByCarIdAndOwnerIdAndSaleDateNull(Long carId, Long ownerId);

    Optional<Ownership> findAllByOwnerIdAndCarIdAndSaleDateNull(Long ownerId, Long carId);
}