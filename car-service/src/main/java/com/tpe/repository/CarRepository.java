package com.tpe.repository;

import com.tpe.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {


    boolean existsByNumberPlate(String numberPlate);


}
