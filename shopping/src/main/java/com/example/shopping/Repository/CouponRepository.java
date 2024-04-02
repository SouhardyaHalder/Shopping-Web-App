package com.example.shopping.Repository;

import com.example.shopping.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    // You can add custom query methods here if needed
}
