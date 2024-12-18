package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.enums.GameScoreEnum;
import java.util.Set;

public interface CompetitionService {

    void joinGame(int gameId, User user);

    void joinGame(int gameId, User user, double score);

    void exitGame(int gameId, int userId);

    void updateScore(int gameId, int userId, double score, GameScoreEnum gameScoreEnum);

    Set<User> allUserRank(int gameId);

    Set<User> userRankByScore(int gameId, double min, double max);

    Set<User> userRankByOrder(int gameId, int start, int end);
}
