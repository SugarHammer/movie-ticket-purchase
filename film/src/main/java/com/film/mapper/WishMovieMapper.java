package com.film.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.film.entity.WishMovie;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:06
 */
@Repository
public interface WishMovieMapper extends BaseMapper<WishMovie> {
    List<WishMovie> selectUserWishMovie(Integer userId);
}
