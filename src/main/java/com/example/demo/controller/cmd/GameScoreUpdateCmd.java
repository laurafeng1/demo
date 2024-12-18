package com.example.demo.controller.cmd;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameScoreUpdateCmd {
    private int gameId;

    private int userId;

    private double delta;
}
