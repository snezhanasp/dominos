package com.example.dominos.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ordered_items")
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "pizza_specification_id")
    private PizzaSpecification pizzaSpecification;

    @ManyToMany
    @JoinTable(
            name = "ordered_items_have_ingredients",
            joinColumns = @JoinColumn(name = "ordered_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "orderedItem")
    private Set<OrderItemQuantity> ordersAndQuantities;

    @OneToMany(mappedBy = "orderedItem")
    private Set<FavouriteItemName> favourites;

}
