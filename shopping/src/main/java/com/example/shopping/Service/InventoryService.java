package com.example.shopping.Service;

import com.example.shopping.Model.Inventory;
import com.example.shopping.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory updateInventory(int odr, int avl){
        Inventory inventory = inventoryRepository.findById(1L).orElse(null);

        int available=inventoryRepository.findAvailableById(1L);
        int ordered= inventoryRepository.findOrderedById(1L);

        available=available-odr;

        ordered+=odr;

        // Update the fetched inventory data with new values
        inventory.setOrdered(ordered);
        inventory.setAvailable(available);

        // Save the updated inventory data back to the database
        return inventoryRepository.save(inventory);
    }
}
