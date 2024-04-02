package com.example.shopping.Repository;

import com.example.shopping.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You can add custom query methods here if needed

    @Query("SELECT i.price FROM Inventory i WHERE i.id = :id")
    Integer findPriceById(@Param("id") Long id);

    @Query("SELECT i.available FROM Inventory i WHERE i.id = :id")
    Integer findAvailableById(@Param("id") Long id);

    @Query("SELECT i.ordered FROM Inventory i WHERE i.id = :id")
    Integer findOrderedById(@Param("id") Long id);
}