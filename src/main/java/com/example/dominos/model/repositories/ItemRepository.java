package com.example.dominos.model.repositories;

import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findItemsByCategory(Category category);
}
