package com.example.dominos.model.entities;

import com.example.dominos.model.compositeKeys.OrderOrderedItemKey;

import javax.persistence.*;

@Entity(name = "orders_have_items")
public class OrderedItemQuantity {

    @EmbeddedId
    private OrderOrderedItemKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("orderedItemId")
    @JoinColumn(name = "orderedItem_id")
    private OrderedItem orderedItem;

    @Column
    private int quantity;
}
