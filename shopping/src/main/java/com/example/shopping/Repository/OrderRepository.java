package com.example.shopping.Repository;

import com.example.shopping.Model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderData, Long> {

    // You can add custom query methods here if needed
    @Query(value = "SELECT * FROM ordered_data i WHERE i.userid = :userId", nativeQuery = true)
    List<OrderData> findByUserId(int userId);
    @Query(value = "SELECT * FROM ordered_data i WHERE i.orderid = :orderId", nativeQuery = true)
    List<OrderData> findByOrderId(int orderId);
}
