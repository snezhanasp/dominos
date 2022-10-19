package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findItemsByCategory(String category);
}
