package com.example.demo.service;

import com.example.demo.enums.RegisterEnum;

import java.util.Date;

public interface RegisterService {
    void signIn(int userId);

    void cancelSignIn(int userId);

    boolean checkUserSignIn(int userId, int monthOfYear, int weekOfMonth, int dayOfWeek);

    long countUserSignIn(int userId, RegisterEnum registerEnum, int...values);

    boolean checkSignInCompany(Date date, int userId);

    long countSignInCompany(Date date);
}
