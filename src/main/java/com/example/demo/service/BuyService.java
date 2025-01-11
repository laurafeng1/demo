package com.example.demo.service;

public interface BuyService {
    void select(int userId, int goodId, int count);

    void pay(int orderId);


    void payWithoutException(int orderId);
}
