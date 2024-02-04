package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Cinema;
import com.film.mapper.CinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 18:59
 */
@Service
public class CinemaService {
    @Autowired
    private CinemaMapper cinemaMapper;

    public List<Cinema> selectAllCinema(){
        return cinemaMapper.selectList(null);
    }

    public List<Cinema> selectAll() {
        return cinemaMapper.selectAllCinema();
    }

    public IPage<Cinema> selectAll(Page<Cinema> page, String query) {
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(query != null, "name", query);
        return cinemaMapper.selectPage(page, queryWrapper);
    }

    public boolean addCinema(Cinema cinema) {
        return cinemaMapper.insert(cinema) > 0;
    }

    public boolean updateCinema(Cinema cinema) {
        UpdateWrapper<Cinema> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", cinema.getId());
        return cinemaMapper.update(cinema, wrapper) > 0;
    }

    public boolean deleteCinema(Integer id) {
        return cinemaMapper.deleteById(id) > 0;
    }

    public Cinema selectOne(Integer id) {
        QueryWrapper<Cinema> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return cinemaMapper.selectOne(wrapper);
    }
}
