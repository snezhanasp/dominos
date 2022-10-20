package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {
}
