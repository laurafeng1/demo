package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserSubscribe;

import java.util.List;

public interface UserService {
    void register(String name, String password, int age, String gender, String job);

    void login(String name, String password);

    void modifyPassword(String name, String password);

    void deleteUser(int id);

    List<User> findAllUser();

    User findUserByName(String name);

    void addUserSubscribe(User subed, User sub);

    List<UserSubscribe> showUserSubscribe(int userId);

    List<UserSubscribe> showUserSubscribed(int userId);

//    void removeUserSubscribe(User userSubscribed, User userSubscriber);

    void cancelUserSubscribe(User subed, User sub);
}
