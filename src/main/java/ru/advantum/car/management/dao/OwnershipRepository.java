package ru.advantum.car.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findAllByOwnerId(Long ownerId);
}