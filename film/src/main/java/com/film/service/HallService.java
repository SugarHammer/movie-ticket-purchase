package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Hall;
import com.film.mapper.HallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;;

/**
 * @author Sora
 * @date 2021/1/20 19:09
 */
@Service
public class HallService {
    @Autowired
    private HallMapper hallMapper;

    public List<Hall> selectAll(Integer id){
        QueryWrapper<Hall> wrapper = new QueryWrapper<>();
        wrapper.eq("cinema_id",id);
        return hallMapper.selectList(wrapper);
    }

    public boolean addHall(Hall hall) {
        return hallMapper.insert(hall) > 0;
    }

    public Page<Hall> selectAll(Page<Hall> page,String query){
        return hallMapper.selectByPage(page, query);
    }

    public boolean updateHall(Hall hall) {
        UpdateWrapper<Hall> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", hall.getId());
        return hallMapper.update(hall, wrapper) > 0;
    }

    public boolean deleteHall(Integer id) {
        return hallMapper.deleteById(id) > 0;
    }

    public Hall selectOne(Integer id) {
        QueryWrapper<Hall> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return hallMapper.selectOne(wrapper);
    }

}
