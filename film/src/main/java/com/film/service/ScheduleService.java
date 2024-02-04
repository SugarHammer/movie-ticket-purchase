package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Schedule;
import com.film.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:13
 */
@Service
public class ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<Schedule> selectAll() {
        return scheduleMapper.selectAllInfo();
    }

    public IPage<Schedule> selectAll(Page<Schedule> page,String query) {
        return scheduleMapper.selectByPage(page,query);
    }

    public boolean addSchedule(Schedule schedule) {
        return scheduleMapper.insert(schedule) > 0;
    }

    public boolean updateSchedule(Schedule schedule) {
        UpdateWrapper<Schedule> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", schedule.getId());
        return scheduleMapper.update(schedule, wrapper) > 0;
    }

    public boolean deleteSchedule(Integer id) {
        return scheduleMapper.deleteById(id) > 0;
    }


    public List<Schedule> selectScheduleList(Schedule schedule) {
        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        wrapper.eq("cinema_id", schedule.getCinemaId())
                .eq("hall_name", schedule.getHallName())
                .eq("show_date", schedule.getShowDate());
        return scheduleMapper.selectList(wrapper);
    }

    public List<Schedule> selectCinemaScheduleInfo(Integer cinemaId) {
        return scheduleMapper.selectCinemaSchedule(cinemaId);
    }

    public List<Schedule> selectCinemaMovieSchedule(Integer cinemaId,Integer movieId) {
        return scheduleMapper.selectCinemaMovie(cinemaId,movieId);
    }

    public List<Schedule> selectMovieScheduleInfo(Integer movieId){
        return scheduleMapper.selectMovieSchedule(movieId);
    }

    public List<Schedule> selectMovieCinema(Integer movieId,String showDate){
        return scheduleMapper.selectMovieCinema(movieId,showDate);
    }

    public Schedule selectOneSchedule(Integer id){
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return scheduleMapper.selectOne(queryWrapper);
    }

    public List<Schedule> searchMovie(String query){
        return scheduleMapper.selectMovieByName(query);
    }

    public List<Schedule> searchCinema(String query){
        return scheduleMapper.selectCinemaByName(query);
    }
}
