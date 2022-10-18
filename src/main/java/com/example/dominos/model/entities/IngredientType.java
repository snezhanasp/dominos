package com.example.dominos.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Type;

@Data
@Entity(name = "ingredient_types") //todo refactor table name in DB
public class IngredientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
}
