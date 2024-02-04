package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.film.entity.WishMovie;
import com.film.mapper.WishMovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:14
 */
@Service
public class WishMovieService {
    @Autowired
    private WishMovieMapper wishMovieMapper;

    public List<WishMovie> selectAll() {
        return wishMovieMapper.selectList(null);
    }

    public boolean addWishMovie(WishMovie wishMovie) {
        return wishMovieMapper.insert(wishMovie) > 0;
    }

    public boolean deleteWishMovie(Integer userId,Integer movieId) {
        QueryWrapper<WishMovie> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("movie_id", movieId);
        return wishMovieMapper.delete(wrapper) > 0;
    }

    public WishMovie selectOne(Integer userId, Integer movieId) {
        QueryWrapper<WishMovie> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("movie_id", movieId);
        return wishMovieMapper.selectOne(wrapper);
    }

    public List<WishMovie> selectUserLike(Integer userId){
        return wishMovieMapper.selectUserWishMovie(userId);
    }
}
