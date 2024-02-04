package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Order;
import com.film.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:11
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    public IPage<Order> selectAll(Page<Order> page, String query) {
        return orderMapper.selectAllOrder(page, query);
    }

    public boolean addOrder(Order order) {
        return orderMapper.order(order) > 0;
    }

    public boolean deleteOrder(Integer id) {
        return orderMapper.deleteOrder(id) > 0;
    }

    public List<Order> selectUserOrderInfo(Integer userId){
        return orderMapper.findUserOrder(userId);
    }
}

