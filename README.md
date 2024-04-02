### In this application mysql DB is used so to run the application first locally a schema has to be created and in the application.properties file change it with shopping in spring.datasource.url=jdbc:mysql://localhost:3306/shopping
### the corresponding name and password has to be given in different system it will be different

### for all the API calls Postman software has been used 

From the given assignment in the postman these following queries has been executed 

1. GET {{url}}/inventory
2. GET {{url}}/fetchCoupons
3. POST {{url}}/{userId}/order?qty=10&coupon=OFF5      // note it is not coupon="OFF5" 
4. POST {{url}}/{userId}/{orderId}/pay?amount=950
5. GET {{url}}/{userId}/orders
6. Get {{url}}/{userId}/orders/{orderId}

for example set of results given below 

1. GET localhost:8080/inventory
{
    "ordered": 0,
    "price": 100,
    "available": 100
}


2. GET localhost:8080/fetchCoupons

[
    {
        "coupon": "OFF5"
    },
    {
        "coupon": "OFF10"
    }
]

3. POST localhost:8080/3/order?qty=7&coupon=OFF10
{
    "orderid": 845,
    "userid": 3,
    "qty": 7,
    "amount": 630,
    "coupon": "OFF10"
}

  IF WE USE QTY>100
  POST localhost:8080/3/order?qty=108&coupon=OFF10

  {"description": "Invalid quantity. Quantity is either less than 1 or more than the maximum quantity of product available"}

4. POST localhost:8080/1/78/pay?amount=665

upon executing this query first it gave transaction failed then it was successful 
 
{
    "orderid": 78,
    "userid": 3,
    "transactionid": "tran010100868",
    "status": "failed",
    "description": "Payment Failed from bank"
}
{
    "orderid": 78,
    "userid": 3,
    "transactionid": "tran010100972",
    "status": "Successful"
}

5. GET localhost:8080/3/orders  
  [
    {
        "orderid": 78,
        "userid": 3,
        "quantity": 7,
        "amount": 665,
        "coupon": "OFF5",
        "orderDate": "2024-04-02"
    },
    {
        "orderid": 845,
        "userid": 3,
        "quantity": 7,
        "amount": 630,
        "coupon": "OFF10",
        "orderDate": "2024-04-02"
    }
]


6. GET localhost:8080/3/orders/78
[
    {
        "orderid": 78,
        "amount": 665,
        "date": "2024-04-02",
        "coupon": "OFF5",
        "transactionid": "tran010100868",
        "status": "failed"
    },
    {
        "orderid": 78,
        "amount": 665,
        "date": "2024-04-02",
        "coupon": "OFF5",
        "transactionid": "tran010100972",
        "status": "Successful"
    }
]
