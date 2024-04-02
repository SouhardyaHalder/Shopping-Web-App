package com.example.shopping.Service;

import com.example.shopping.Model.Inventory;
import com.example.shopping.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InventoryInitializer implements ApplicationRunner {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if inventory data already exists
        if (inventoryRepository.count() == 0) {
            // Insert initial inventory data
            Inventory inventory = new Inventory(0,100,100);
            inventoryRepository.save(inventory);
        }
    }
}