package com.example.demo.service;

public interface BuyService {
    void select(int userId, int goodId, int count);

    int pay(int orderId);


    int payWithoutException(int orderId);

    int refund(int orderId);

    int refundWithoutException(int orderId);
}
