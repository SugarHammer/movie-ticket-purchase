package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Hall;
import com.film.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:16
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @GetMapping("/findHall")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String query = request.getParameter("query");
        Page<Hall> page = new Page<>(pageNum, pageSize);
        Page<Hall> iPage = hallService.selectAll(page,query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取影厅信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影厅信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addHall")
    public Object addHall(@RequestBody Hall hall) {
        boolean res = hallService.addHall(hall);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加影厅成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加影厅失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateHall")
    public Object updateHall(@RequestBody Hall hall) {
        boolean res = hallService.updateHall(hall);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改影厅信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改影厅信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteHall")
    public Object deleteHall(@RequestParam Integer id) {
        boolean res = hallService.deleteHall(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除影厅成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除影厅失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findOneHall")
    public Object selectOneHall(@RequestParam Integer id) {
        Hall hall = hallService.selectOne(id);
        JSONObject jsonObject = new JSONObject();
        if (hall != null) {
            jsonObject.put("code", 1);
            jsonObject.put("hall", hall);
            jsonObject.put("msg", "获取影厅信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影厅信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findHallList")
    public Object selectHallList(@RequestParam Integer id) {
        List<Hall> halls = hallService.selectAll(id);
        JSONObject jsonObject = new JSONObject();
        if (!halls.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("halls", halls);
            jsonObject.put("msg", "获取影厅信息成功");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影厅信息失败");
        }
        return jsonObject;
    }

}
