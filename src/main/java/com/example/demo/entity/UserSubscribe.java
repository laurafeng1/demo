package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
public class UserSubscribe {
    private int userId;
    private String userName;
    private Date subscribeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubscribe that = (UserSubscribe) o;
        return userId == that.userId && Objects.equals(userName, that.userName) && Objects.equals(subscribeTime, that.subscribeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, subscribeTime);
    }
}
