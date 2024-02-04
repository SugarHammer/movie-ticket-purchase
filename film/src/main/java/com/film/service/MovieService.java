package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Movie;
import com.film.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;;


/**
 * @author Sora
 * @date 2021/1/20 19:10
 */
@Service
public class MovieService {
    @Autowired
    private MovieMapper movieMapper;

    public List<Movie> selectAll(){
        return movieMapper.selectList(null);
    }

    public IPage<Movie> selectAll(Page<Movie> page, String query) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(query != null, "name", query);
        return movieMapper.selectPage(page, queryWrapper);
    }

    public boolean addMovie(Movie movie) {
        return movieMapper.insert(movie) > 0;
    }

    public boolean updateMovie(Movie movie){
        UpdateWrapper<Movie> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",movie.getId());
        return movieMapper.update(movie,updateWrapper) > 0;
    }

    public boolean deleteMovieById(Integer id) {
        return movieMapper.deleteById(id) > 0;
    }

    public Movie selectOne(Integer id){
        QueryWrapper<Movie> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return movieMapper.selectOne(wrapper);
    }
}
