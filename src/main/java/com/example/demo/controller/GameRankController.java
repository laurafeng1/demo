package com.example.demo.controller;

import com.example.demo.controller.cmd.GameScoreUpdateCmd;
import com.example.demo.controller.cmd.GameUserAddCmd;
import com.example.demo.controller.cmd.GameUserExitCmd;
import com.example.demo.controller.converter.UserVOConverter;
import com.example.demo.controller.vo.UserRankVO;
import com.example.demo.controller.vo.BaseVO;
import com.example.demo.controller.vo.UserVO;
import com.example.demo.entity.User;
import com.example.demo.enums.GameScoreEnum;
import com.example.demo.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/game/rank")
public class GameRankController {
    @Autowired
    private CompetitionService competitionService;

    @PostMapping("/addUser")
    public BaseVO joinGame(@RequestBody GameUserAddCmd gameUserAddCmd) {
        long start = System.currentTimeMillis();
        long end;
        try{
            competitionService.joinGame(gameUserAddCmd.getGameId(), gameUserAddCmd.getUserId(), 0.0);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e){
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "加入游戏失败");
        }

    }

    @DeleteMapping("/deleteUser")
    public BaseVO exitGame(@RequestBody GameUserExitCmd gameUserExitCmd) {
        long start = System.currentTimeMillis();
        long end;
        try {
            competitionService.exitGame(gameUserExitCmd.getGameId(), gameUserExitCmd.getUserId());
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e){
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "退出游戏失败");
        }
    }

    @PutMapping("/increaseScoreByDelta")
    public BaseVO increaseScore(@RequestBody GameScoreUpdateCmd gameScoreUpdateCmd) {
        long start = System.currentTimeMillis();
        long end;
        try {
            competitionService.updateScore(gameScoreUpdateCmd.getGameId(), gameScoreUpdateCmd.getUserId(), gameScoreUpdateCmd.getDelta(), GameScoreEnum.INCREASEMENT);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e){
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "分数添加失败");
        }
    }

    @PutMapping("/decreaseScoreByDelta")
    public BaseVO decreaseScore(@RequestBody GameScoreUpdateCmd gameScoreUpdateCmd) {
        long start = System.currentTimeMillis();
        long end;
        try {
            competitionService.updateScore(gameScoreUpdateCmd.getGameId(), gameScoreUpdateCmd.getUserId(), gameScoreUpdateCmd.getDelta(), GameScoreEnum.DECREASEMENT);
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(200, end - start, true, null);
        } catch (Exception e){
            end = System.currentTimeMillis();
            return BaseVO.buildBaseVo(500, end - start, false, "分数扣减失败");
        }
    }

    @GetMapping("/allUserRank")
    public UserRankVO allUserRank(int gameId) {
        UserRankVO userRankVO = new UserRankVO();
        long start = System.currentTimeMillis();
        long end;
        try {
            Set<User> users = competitionService.allUserRank(gameId);
            List<UserVO> userVOs = UserVOConverter.convertUserVOList(users);
            Collections.sort(userVOs);
            userRankVO.setUserVOs(userVOs);
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return userRankVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "查询全部用户排名失败"));
            return userRankVO;
        }
    }

    @GetMapping("/userRankByOrder")
    public UserRankVO userRankByOrder(int gameId, int startOrder, int endOrder) {
        UserRankVO userRankVO = new UserRankVO();
        long start = System.currentTimeMillis();
        long end;
        try {
            Set<User> users = competitionService.userRankByOrder(gameId, startOrder, endOrder);
            List<UserVO> userVOs = UserVOConverter.convertUserVOList(users);
            Collections.sort(userVOs);
            userRankVO.setUserVOs(userVOs);
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return userRankVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "查询全部用户排名失败"));
            return userRankVO;
        }
    }

    @GetMapping("/userRankByScore")
    public UserRankVO userRankByScore(int gameId, double min, double max) {
        UserRankVO userRankVO = new UserRankVO();
        long start = System.currentTimeMillis();
        long end;
        try {
            Set<User> users = competitionService.userRankByScore(gameId, min, max);
            List<UserVO> userVOs = UserVOConverter.convertUserVOList(users);
            Collections.sort(userVOs);
            userRankVO.setUserVOs(userVOs);
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(200, end - start, true, null));
            return userRankVO;
        } catch(Exception e) {
            end = System.currentTimeMillis();
            userRankVO.setBaseVO(BaseVO.buildBaseVo(500, end - start, false, "查询全部用户排名失败"));
            return userRankVO;
        }
    }
}
