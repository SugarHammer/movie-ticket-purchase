package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.User;
import com.film.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Sora
 * @date 2021/1/20 19:17
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    //验证码
    private StringBuilder stringBuilder = new StringBuilder(4);

    @GetMapping("/findUserInfo")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        String query = request.getParameter("query").trim();
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> iPage = userService.selectAll(page, query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取用户信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取用户信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addUser")
    public Object addUser(@RequestBody User user) {
        boolean res = userService.addUser(user);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加用户成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加用户失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateUser")
    public Object updateInfo(@RequestBody User user) {
        boolean res = userService.updateUser(user);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改用户信息成功! ");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改用户信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteUser")
    public Object deleteUser(@RequestParam Integer id) {
        boolean res = userService.deleteUserById(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除用户成功! ");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除用户失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateAvatar")
    public Object updateAvatar(@RequestParam("file") MultipartFile multipartFile, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (multipartFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String path = "/img/" + fileName;
        try {
            multipartFile.transferTo(dest);
            User user = new User();
            user.setId(id);
            user.setAvatar(path);
            boolean res = userService.updateUser(user);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "上传成功！");
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败！");
            }
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
            return jsonObject;
        }
    }

    @GetMapping("/findOneUserInfo")
    public Object selectOneUser(@RequestParam Integer id) {
        User user = userService.selectOne(id);
        JSONObject jsonObject = new JSONObject();
        if (user != null) {
            jsonObject.put("code", 1);
            jsonObject.put("user", user);
            jsonObject.put("msg", "获取用户信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取用户信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getPhoneCode")
    public Object phoneCode() {
        if (stringBuilder.length() > 0) {
            stringBuilder.delete(0, stringBuilder.length());
        }
        JSONObject jsonObject = new JSONObject();
        String str = "0123456789";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int num = random.nextInt(str.length());
            stringBuilder.append(num);
        }
        jsonObject.put("code", 1);
        jsonObject.put("phoneCode", stringBuilder);
        jsonObject.put("msg", "获取验证码成功！");
        return jsonObject;
    }

    @PostMapping("/passwordLogin")
    public Object passwordLogin(@RequestBody User user) {
        User one = userService.selectOne(user.getUsername());
        JSONObject jsonObject = new JSONObject();
        if (one != null) {
            if (one.getPassword().equals(user.getPassword())) {
                jsonObject.put("code", 1);
                jsonObject.put("userId", one.getId());
                jsonObject.put("msg", "登录成功！");
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "密码错误！");
            }
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "找不到该用户！");
        }
        return jsonObject;
    }

    @PostMapping("/phoneLogin")
    public Object phoneLogin(@RequestBody JSONObject json) {
        String phone = json.getString("phone");
        String phoneCode = json.getString("phoneCode");
        JSONObject jsonObject = new JSONObject();
        if (stringBuilder.toString().equals(phoneCode)) {
            User user = userService.selectOneByPhone(phone);
            if (user != null) {
                jsonObject.put("code", 1);
                jsonObject.put("userId", user.getId());
                jsonObject.put("msg", "登录成功！");
            } else {
                User one = new User();
                one.setUsername(phone);
                one.setPassword("123456");
                one.setPhone(phone);
                boolean res = userService.addUser(one);
                if (res) {
                    jsonObject.put("code", 1);
                    jsonObject.put("userId", one.getId());
                    jsonObject.put("msg", "注册、登录成功! ");
                } else {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "注册失败！");
                }
                return jsonObject;
            }
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "验证码错误！");
        }
        return jsonObject;
    }

    @GetMapping("/getUserList")
    public Object getAllUser(){
        List<User> list = userService.selectAll();
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()){
            jsonObject.put("code",1);
            jsonObject.put("userList",list);
            jsonObject.put("msg","获取成功!");
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","获取失败!");
        }
        return jsonObject;
    }
}
