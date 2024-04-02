package com.example.shopping.Service;

import com.example.shopping.Model.Coupon;
import com.example.shopping.Repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CouponInitializer implements ApplicationRunner {
    @Autowired
    private CouponRepository couponRepository;


    // By assumption initially two coupons are created
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(couponRepository.count()==0){
            Coupon coupon1=new Coupon("OFF5");
            couponRepository.save(coupon1);
            Coupon coupon2=new Coupon("OFF10");
            couponRepository.save(coupon2);
        }
    }
}
