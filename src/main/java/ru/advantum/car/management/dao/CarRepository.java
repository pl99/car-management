package ru.advantum.car.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByIdIn(List<Long> ids);
}