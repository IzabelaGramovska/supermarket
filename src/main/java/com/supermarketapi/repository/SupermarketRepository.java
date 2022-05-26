package com.supermarketapi.repository;

import com.supermarketapi.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, String> {
    boolean existsSupermarketByName(String name);
}

