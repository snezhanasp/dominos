package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.PizzaSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<PizzaSpecification.Size, Long> {
}
