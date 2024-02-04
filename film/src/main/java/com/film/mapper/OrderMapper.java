package com.film.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Order;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:05
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    int order(Order order);

    List<Order> findUserOrder(Integer userId);

    @Select("select * from `order`")
    List<Order> selectAll();

    IPage<Order> selectAllOrder(Page<Order> page, String query);

    int deleteOrder(Integer id);
}
