package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "ingredient_types")
public class IngredientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String type;
    @Column
    private double price;

    @OneToMany(mappedBy = "ingredientType")
    private List<Ingredient> ingredients;
}
