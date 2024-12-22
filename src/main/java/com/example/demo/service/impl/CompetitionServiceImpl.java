package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.enums.GameScoreEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRankRepository;
import com.example.demo.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    private UserRankRepository userRankRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void joinGame(int gameId, int userId, double score) {
        User user = userMapper.findById(userId);
        //todo: exception + add log(before exception)
        if(user == null) {
            throw new RuntimeException();
        }
        if(checkUserExist(gameId, userId, user.getName())) {
            throw new RuntimeException();
        }
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
        joinGame(gameId, userId, updatedScore);
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

    private boolean checkUserExist(int gameId, int userId, String name) {
        Set<User> users = allUserRank(gameId);
        for(User user : users) {
            if(user.getId() == userId && user.getName().equals(name)) {
                return true;
            }
        }
        return false;
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
