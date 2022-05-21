package com.supermarketapi.repository;

import com.supermarketapi.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, String> {
    @Query(value = "select * from Supermarket where id = :id", nativeQuery = true)
    Optional<Supermarket> findSupermarketById(String id);

    boolean existsSupermarketByName(String name);
}

