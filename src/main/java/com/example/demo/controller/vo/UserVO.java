package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;

@Getter
@Setter
@ToString
public class UserVO implements Comparable<UserVO> {
    private int id;

    private String name;

    private String password;

    private int age;

    private String gender;

    private String job;

    private double score;

    @Override
    public int compareTo(UserVO o) {
        int scoreCompare = (int) (o.score - this.score);
        if(scoreCompare != 0) {
            return scoreCompare;
        }
        return this.name.compareTo(o.name);
    }

}
