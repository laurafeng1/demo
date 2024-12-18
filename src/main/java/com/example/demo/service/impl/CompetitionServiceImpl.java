package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.enums.GameScoreEnum;
import com.example.demo.repository.UserRankRepository;
import com.example.demo.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    private UserRankRepository userRankRepository;

    @Override
    public void joinGame(int gameId, User user) {
        //todo: check if user exists
        user.setScore(0.0);
        userRankRepository.addUser(gameId, user, 0.0);
    }

    @Override
    public void joinGame(int gameId, User user, double score) {
        user.setScore(score);
        userRankRepository.addUser(gameId, user, score);
    }

    @Override
    public void exitGame(int gameId, int userId) {
        User user = searchByUserId(gameId, userId);
        userRankRepository.removeUser(gameId, user);
    }

    @Override
    public void updateScore(int gameId, int userId, double delta, GameScoreEnum gameScoreEnum) {
        User user = searchByUserId(gameId, userId);
        //todo: 添加异常
        if(user == null) {
            throw new RuntimeException();
        }
        double updatedScore;
        if(gameScoreEnum == GameScoreEnum.INCREASEMENT) {
            updatedScore = user.getScore() + delta;
        } else {
            updatedScore = user.getScore() - delta;
        }
        exitGame(gameId, userId);
        user.setScore(updatedScore);
        joinGame(gameId, user, updatedScore);
    }

    private User searchByUserId(int gameId, int userId) {
        Set<User> users = allUserRank(gameId);
        for(User user : users) {
            if(user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Set<User> allUserRank(int gameId) {
        return userRankRepository.listByRank(gameId, 0, -1);
    }

    @Override
    public Set<User> userRankByScore(int gameId, double min, double max) {
        return userRankRepository.listByScore(gameId, min, max);
    }

    @Override
    public Set<User> userRankByOrder(int gameId, int start, int end) {
        return userRankRepository.listByRank(gameId, start, end);
    }
}
