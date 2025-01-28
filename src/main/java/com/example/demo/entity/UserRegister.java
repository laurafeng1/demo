package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    private int id;

    private String name;

    private String password;

    private int age;

    private String gender;

    private String job;

    private double score;

    private String email;
}
