package com.example.demo.service.impl;

import com.example.demo.entity.Good;
import com.example.demo.mapper.GoodMapper;
import com.example.demo.repository.GoodRepository;
import com.example.demo.service.GoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodRepository goodRepository;

    @Autowired
    private GoodMapper goodMapper;

    private Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

    @Override
    public void addGood(Good good) {
        try{
            goodMapper.add(good);
            logger.info("商品信息插入数据库成功");
        } catch(Exception e) {
            logger.error("商品信息插入数据库失败");
            throw new RuntimeException("商品信息插入数据库失败");
        }
    }

    @Override
    @Transactional
    public void deleteGood(int id) {
        goodRepository.deleteGood(id);
        logger.info("商品从缓存中删除");
        goodMapper.deleteById(id);
        logger.info("商品从数据库中删除");
    }

    @Override
    @Transactional
    public void updateGood(int id, Good updatedGood) {
        Good good = goodMapper.queryById(id);
        if(good == null) {
            throw new RuntimeException("商品未找到");
        }
        good.setName(updatedGood.getName());
        good.setPrice(updatedGood.getPrice());
        good.setStock(updatedGood.getStock());
        good.setDesc(updatedGood.getDesc());
        good.setUrl(updatedGood.getUrl());
        goodMapper.update(good);
        logger.info("商品信息在数据库中更新成功");
        goodRepository.deleteGood(id);
        logger.info("商品信息在缓存中删除成功");
    }

    @Override
    @Transactional
    public Good getGood(int id) {
        Good good = goodRepository.searchGood(id);
        if(good != null) {
            logger.info("从缓存中读取商品信息");
            return good;
        }
        good = goodMapper.queryById(id);
        if (good != null) {
            goodRepository.addGood(id, good);
            logger.info("从数据库中获取商品信息，并插入缓存");
            return good;
        } else {
            logger.warn("商品信息不存在");
            throw new RuntimeException("商品不存在");
        }
    }

}
