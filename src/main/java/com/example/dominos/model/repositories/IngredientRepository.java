package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientsByItems(Item item);
}
