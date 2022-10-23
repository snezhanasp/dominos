package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.PizzaSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaSpecificationRepository extends JpaRepository<PizzaSpecification, Long> {

    Optional<PizzaSpecification> findBySize_IdAndDoughType_Id(long sizeID, long doughTypeId);
}
