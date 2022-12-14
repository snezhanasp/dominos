package com.example.dominos.model.repositories;

import com.example.dominos.model.compositeKeys.OrderOrderedItemKey;
import com.example.dominos.model.entities.OrderItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemQuantityRepository extends JpaRepository<OrderItemQuantity, OrderOrderedItemKey> {
}
