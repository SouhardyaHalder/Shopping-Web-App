package com.example.shopping.Controller;

import com.example.shopping.Model.Coupon;
import com.example.shopping.Model.Inventory;
import com.example.shopping.Model.OrderData;
import com.example.shopping.Repository.CouponRepository;
import com.example.shopping.Repository.InventoryRepository;
import com.example.shopping.Repository.OrderRepository;
import com.example.shopping.Repository.TransactionRepository;
import com.example.shopping.Model.Transaction;
import com.example.shopping.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {

    @Autowired
    public InventoryRepository inventoryRepository;
    @Autowired
    public CouponRepository couponRepository;
    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public InventoryService inventoryService;
    @Autowired
    public TransactionRepository transactionRepository;

    // Question 1
    @GetMapping("/inventory")
    public ResponseEntity<Object> getInventory() {
        // Fetch of the inventory data from the database
        Inventory inventory = inventoryRepository.findById(1L).orElse(null); // Assuming the inventory record has an ID of 1

        //  when inventory is not found
        if (inventory == null) {
            // Build of the JSON response with a custom description
            String description = "Inventory empty.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"description\": \"" + description + "\"}"); //"{\"description\": ": This represents the starting of a JSON object with a key "description". In a Java string, to include a double quote, you need to escape it with a backslash \, which is why you see \".
        }
        return ResponseEntity.ok().body(inventory);
    }

    // Question 2
    @GetMapping("/fetchCoupons")
    public List<Coupon> getCoupons() {
        // Fetch of coupons
        return couponRepository.findAll();
    }

    // Question 3

    @PostMapping("/{userId}/order")
    public ResponseEntity<Object> createOrder(@PathVariable int userId, @RequestParam int qty, @RequestParam String coupon){
        int price=inventoryRepository.findPriceById(1L);
        int available=inventoryRepository.findAvailableById(1L);
        int ordered=inventoryRepository.findOrderedById(1L);

        // using two bool variables to track that if user has used that coupon
        boolean flag10=true,flag5=true;
        List<OrderData>orderDataList=orderRepository.findByUserId(userId);
        for(OrderData orderData:orderDataList){
            if(orderData.getCoupon().equals("OFF5"))flag5=false;
            if(orderData.getCoupon().equals("OFF10"))flag10=false;
        }
        OrderData orderData=null;
        Random random = new Random();
        int orderId = random.nextInt(1000);

//        String temp="";                 //a temp string is used to capture coupon code some unprecedented value was coming along with coupon value
//                                        //to avoid ambiguity temp variable is used
//
//        for(int i=0;i<coupon.length()-1;i++){
//            temp += coupon.charAt(i);
//        }

        //Handling quantity test case
        if(qty<1 || qty>available){
            String description = "Invalid quantity. Quantity is either less than 1 or more than the maximum quantity of product available";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"description\": \"" + description + "\"}");
        }
        if(coupon.equals("OFF5")){

            // handling the case where user has already user the coupon
            if(!flag5){
                String description="Invalid coupon . User has already used the coupon before ";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"description\": \"" + description + "\"}");
            }

            int totalPrice= (int) ((qty*price)*.95);
            orderData=new OrderData( orderId ,userId ,qty , totalPrice , coupon);
            inventoryService.updateInventory(qty,available);
        }
        else if(coupon.equals("OFF10")){
            // handling the case where user has already user the coupon
            if(!flag10){
                String description="\"Invalid coupon . User has already used the coupon before ";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"description\": \"" + description + "\"}");
            }

            int totalPrice= (int) ((qty*price)*.90);
            orderData=new OrderData( orderId ,userId ,qty , totalPrice , coupon);
            inventoryService.updateInventory(qty,available);
        }
        else{ // handling invalid coupon code test case
            String description="Invalid Coupon code , coupon does not exist";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"description\": \"" + description + "\"}");
        }

        // Create a map to hold only the desired attributes
        // LinkedHashMap is used to get the response value excluding the orderDate
        //specially LinkedHashMap is used because to get in right order only HashMap would not give in right order
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("orderid", orderData.getOrderid());
        response.put("userid", orderData.getUserid());
        response.put("qty", orderData.getQuantity());
        response.put("amount", orderData.getAmount());
        response.put("coupon", orderData.getCoupon());

        orderRepository.save(orderData);

        return ResponseEntity.ok().body(response);
    }


    // Question 4

    @PostMapping("/{userId}/{orderId}/pay")
    public ResponseEntity<Object>createTransaction(@PathVariable int userId,@PathVariable int orderId,@RequestParam int amount){
        String transactionid="tran0"+generateRandom(10101000,10100001);

        Random random = new Random();       int flag = random.nextInt(1000);

        Transaction transaction=null;
        Map<String, Object> response = new LinkedHashMap<>();

        // using a random number posting Successful or Failed transaction
        //one of the status is randomly returned

        if(flag<400){
            String status="Successful";
            transaction=new Transaction(transactionid,userId,orderId,status,amount);
            response.put("orderid", transaction.getOrderId());
            response.put("userid", transaction.getUserId());
            response.put("transactionid", transaction.getTransactionId());
            response.put("status", transaction.getStatus());
        }
        else {
            String status="failed";
            transaction=new Transaction(transactionid,userId,orderId,status,amount);
            String description="Payment Failed from bank";

            response.put("orderid", transaction.getOrderId());
            response.put("userid", transaction.getUserId());
            response.put("transactionid", transaction.getTransactionId());
            response.put("status", transaction.getStatus());
            response.put("description", description);
        }

        transactionRepository.save(transaction);
        return ResponseEntity.ok().body(response);
    }


    // Question 5
    // Retrieving orderdata using userid
    @GetMapping("/{userId}/orders")
    public List<OrderData>orders( @PathVariable int userId){
        return orderRepository.findByUserId(userId);
    }



    //Question 6

    @GetMapping("/{userId}/orders/{orderId}")
    public   ResponseEntity<Object>transactions(@PathVariable int userId,@PathVariable int orderId){
        List<Transaction> transactionsList =transactionRepository.findByUserOrderId(userId,orderId);
        List<OrderData>orderDataList=orderRepository.findByOrderId(orderId);
        List<Map<String, Object>> ResponseList = new ArrayList<>(); // To generate the response an Arraylist of Maps has been used

        String description="Order not found";

        if(orderDataList.isEmpty()){    // Handling the case where order deos not exist
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("orderid",orderId);
            response.put("description",description);
            return ResponseEntity.ok().body(response);
        }
        else{

            for (Transaction transaction : transactionsList){       // using map to retrieve data from transaction table and ordered data
                Map<String, Object> response = new LinkedHashMap<>();
                OrderData orderData = orderDataList.get(0);         // Note orderId is primary key in orderdata so only one list will be returned in orderDataList
                response.put("orderid",orderData.getOrderid());
                response.put("amount",orderData.getAmount());
                response.put("date",orderData.getOrderDate());
                response.put("coupon",orderData.getCoupon());
                response.put("transactionid",transaction.transactionId);
                response.put("status",transaction.getStatus());

                ResponseList.add(response);
            }

        }
    return ResponseEntity.ok().body(ResponseList);
    }

    //By assumption generating random number for transaction id
    public String generateRandom(int max,int min){
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return Integer.toString(randomNumber);
    }
}
