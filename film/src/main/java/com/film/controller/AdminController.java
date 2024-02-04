package com.film.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.film.entity.Admin;
import com.film.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sora
 * @date 2020/11/8 15:11
 */
@RestController
public class AdminController {
    @Autowired
    public AdminService adminService;

    @PostMapping("/login")
    public Object login(@RequestBody Admin admin) {
        JSONObject jsonObject = new JSONObject();
        boolean res = adminService.login(admin.getUsername(),admin.getPassword());
        if (res) {
            jsonObject.put("code",1);
            jsonObject.put("msg","登录成功！");
        }else {
            jsonObject.put("code",0);
            jsonObject.put("msg","登录失败！");
        }
        return jsonObject;
    }
}
