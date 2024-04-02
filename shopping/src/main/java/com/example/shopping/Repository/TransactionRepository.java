package com.example.shopping.Repository;

import com.example.shopping.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , String> {
    @Query(value = "SELECT * FROM transaction t where t.userid=:userId and t.orderid=:orderId", nativeQuery = true)
    List<Transaction>findByUserOrderId(int userId,int orderId);


//    @Query(value = "SELECT * FROM transaction t JOIN ordered_data o ON t.orderid = o.orderid WHERE t.orderid = :orderId", nativeQuery = true)
//    List<Transaction> findByOrderId(int orderId);
}
