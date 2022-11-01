package com.example.dominos.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private IngredientType ingredientType;

    @ManyToMany(mappedBy = "ingredients")
    private List<Item> items;

    @ManyToMany(mappedBy = "ingredients")
    private List<OrderedItem> orderedItems;
}
