package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Cinema;
import com.film.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Sora
 * @date 2021/1/20 19:16
 */
@RestController
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/findCinema")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        String query = request.getParameter("query").trim();
        Page<Cinema> page = new Page<>(pageNum, pageSize);
        IPage<Cinema> iPage = cinemaService.selectAll(page, query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取影院信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影院信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addCinema")
    public Object addCinema(@RequestBody Cinema cinema) {
        boolean res = cinemaService.addCinema(cinema);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加影院成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加影院失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateCinema")
    public Object updateCinema(@RequestBody Cinema cinema) {
        boolean res = cinemaService.updateCinema(cinema);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改影院信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改影院信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteCinema")
    public Object deleteCinema(@RequestParam Integer id) {
        boolean res = cinemaService.deleteCinema(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除影院成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除影院失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findOneCinema")
    public Object selectOneCinema(@RequestParam Integer id) {
        Cinema cinema = cinemaService.selectOne(id);
        JSONObject jsonObject = new JSONObject();
        if (cinema != null) {
            jsonObject.put("code", 1);
            jsonObject.put("cinema", cinema);
            jsonObject.put("msg", "获取影院信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影院信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findCinemaList")
    public Object selectCinemaList() {
        List<Cinema> cinemas = cinemaService.selectAll();
        JSONObject jsonObject = new JSONObject();
        if (!cinemas.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("cinemas", cinemas);
            jsonObject.put("msg", "获取影院信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影院信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getCinemaList")
    public Object selectAllList() {
        List<Cinema> cinemas = cinemaService.selectAllCinema();
        JSONObject jsonObject = new JSONObject();
        if (!cinemas.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("cinemaList", cinemas);
            jsonObject.put("msg", "获取影院信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影院信息失败！");
        }
        return jsonObject;
    }
}
