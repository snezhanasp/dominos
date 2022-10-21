package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemsByCategory(Category category);
}
