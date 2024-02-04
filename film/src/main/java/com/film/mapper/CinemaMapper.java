package com.film.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.film.entity.Cinema;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 18:58
 */
@Repository
public interface CinemaMapper extends BaseMapper<Cinema> {
    List<Cinema> selectAllCinema();
}
