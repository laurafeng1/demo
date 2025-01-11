package com.example.demo.service;

import com.example.demo.entity.Good;

public interface GoodService {
    void addGood(Good good);

    void deleteGood(int id);

    void updateGood(int id, Good good);

    Good getGood(int id);
}
