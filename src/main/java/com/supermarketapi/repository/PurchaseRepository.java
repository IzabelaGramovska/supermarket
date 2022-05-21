package com.supermarketapi.repository;

import com.supermarketapi.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {
    Page<Purchase> findAll(Specification<Purchase> specification, Pageable pageable);
}

