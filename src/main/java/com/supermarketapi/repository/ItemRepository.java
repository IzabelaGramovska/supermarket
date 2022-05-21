package com.supermarketapi.repository;

import com.supermarketapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    @Query(value = "select * from Item where id = :itemId", nativeQuery = true)
    Optional<List<Item>> findItemsById(String itemId);
}
