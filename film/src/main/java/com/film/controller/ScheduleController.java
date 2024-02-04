package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Schedule;
import com.film.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:17
 */
@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/findMovieSchedule")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String query = request.getParameter("query").trim();
        Page<Schedule> page = new Page<>(pageNum, pageSize);
        IPage<Schedule> iPage = scheduleService.selectAll(page,query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取排片信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取排片信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addMovieSchedule")
    public Object addSchedule(@RequestBody Schedule schedule) {
        boolean res = scheduleService.addSchedule(schedule);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加排片成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加排片失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateMovieSchedule")
    public Object updateSchedule(@RequestBody Schedule schedule) {
        boolean res = scheduleService.updateSchedule(schedule);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改排片信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改排片信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteMovieSchedule")
    public Object deleteSchedule(@RequestParam Integer id) {
        boolean res = scheduleService.deleteSchedule(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除排片成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除排片失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findMovieScheduleList")
    public Object selectOneSchedule(HttpServletRequest request) {
        Integer cinemaId = Integer.valueOf(request.getParameter("cinemaId"));
        String hallName = request.getParameter("hallName");
        String showDate = request.getParameter("showDate");
        Schedule schedule = new Schedule();
        schedule.setCinemaId(cinemaId);
        schedule.setHallName(hallName);
        schedule.setShowDate(showDate);
        List<Schedule> lists = scheduleService.selectScheduleList(schedule);
        JSONObject jsonObject = new JSONObject();
        if (lists != null) {
            jsonObject.put("code", 1);
            jsonObject.put("schedules", lists);
            jsonObject.put("msg", "获取排片信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "暂无排片信息！");
        }
        return jsonObject;
    }

    @GetMapping("/findSchedule")
    public Object selectAll() {
        List<Schedule> lists = scheduleService.selectAll();
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("schedules", lists);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findCinemaSchedule")
    public Object selectCinemaSchedule(@RequestParam Integer cinemaId) {
        List<Schedule> lists = scheduleService.selectCinemaScheduleInfo(cinemaId);
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("schedule", lists);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findCinemaMovieSchedule")
    public Object selectCinemaMovieSchedule(HttpServletRequest request) {
        Integer cinemaId = Integer.valueOf(request.getParameter("cinemaId"));
        Integer movieId = Integer.valueOf(request.getParameter("movieId"));
        List<Schedule> lists = scheduleService.selectCinemaMovieSchedule(cinemaId, movieId);
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("movieSchedules", lists);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findMovieScheduleInfo")
    public Object selectMovieSchedule(@RequestParam Integer movieId) {
        List<Schedule> lists = scheduleService.selectMovieScheduleInfo(movieId);
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("movieSchedule", lists);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findMovieCinemaScheduleInfo")
    public Object selectMovieCinemaSchedule(HttpServletRequest request) {
        Integer movieId = Integer.valueOf(request.getParameter("movieId"));
        String showDate = request.getParameter("showDate");
        List<Schedule> lists = scheduleService.selectMovieCinema(movieId, showDate);
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("movieCinema", lists);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findOneSchedule")
    public Object selectOneScheduleInfo(@RequestParam Integer id) {
        Schedule schedule = scheduleService.selectOneSchedule(id);
        JSONObject jsonObject = new JSONObject();
        if (schedule != null) {
            jsonObject.put("code",1);
            jsonObject.put("schedule",schedule);
            jsonObject.put("msg","获取信息成功！");
        } else {
            jsonObject.put("code",0);
            jsonObject.put("msg","获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/searchMovieByName")
    public Object selectMovieLikeName(@RequestParam String query){
        List<Schedule> list = scheduleService.searchMovie(query);
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()) {
            jsonObject.put("code",1);
            jsonObject.put("movieInfo",list);
            jsonObject.put("msg","获取信息成功！");
        } else {
            jsonObject.put("code",0);
            jsonObject.put("msg","获取信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/searchCinemaByName")
    public Object selectCinemaLikeName(@RequestParam String query){
        List<Schedule> list = scheduleService.searchCinema(query);
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()) {
            jsonObject.put("code",1);
            jsonObject.put("cinemaInfo",list);
            jsonObject.put("msg","获取信息成功！");
        } else {
            jsonObject.put("code",0);
            jsonObject.put("msg","获取信息失败！");
        }
        return jsonObject;
    }
}
