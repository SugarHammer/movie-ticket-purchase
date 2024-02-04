package com.film.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:05
 */
@Repository
public interface ScheduleMapper extends BaseMapper<Schedule> {

    Page<Schedule> selectByPage(Page<Schedule> page,String query);

    List<Schedule> selectAllInfo();

    List<Schedule> selectCinemaSchedule(Integer cinemaId);

    List<Schedule> selectMovieSchedule(Integer movieId);

    List<Schedule> selectCinemaMovie(Integer cinemaId,Integer movieId);

    List<Schedule> selectMovieCinema(Integer movieId,String showDate);

    List<Schedule> selectMovieByName(String query);

    List<Schedule> selectCinemaByName(String query);
}
