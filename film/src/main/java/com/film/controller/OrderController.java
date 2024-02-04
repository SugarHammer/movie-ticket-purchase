package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Order;
import com.film.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

/**
 * @author Sora
 * @date 2021/1/20 19:16
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/findOrder")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String query = request.getParameter("query").trim();
        Page<Order> page = new Page<>(pageNum, pageSize);
        IPage<Order> iPage = orderService.selectAll(page,query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists",iPage);
            jsonObject.put("msg", "获取订单信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取订单信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addOrder")
    public Object addOrder(@RequestBody Order order) {
        StringBuilder stringBuilder = new StringBuilder(6);
        String str = "0123456789";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(str.length());
            stringBuilder.append(num);
        }
        order.setPhoneCode(stringBuilder.toString());
        boolean res = orderService.addOrder(order);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("phoneCode", order.getPhoneCode());
            jsonObject.put("msg", "添加订单成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加订单失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteOrder")
    public Object deleteOrder(@RequestParam  Integer id) {
        boolean res = orderService.deleteOrder(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除订单成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除订单失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findUserOrder")
    public Object selectUserOrder(@RequestParam Integer userId){
        List<Order> lists = orderService.selectUserOrderInfo(userId);
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("orderInfo",lists);
            jsonObject.put("msg", "获取订单信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取订单信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getOrderList")
    public Object selectUserOrder(){
        List<Order> lists = orderService.selectAll();
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("orderList",lists);
            jsonObject.put("msg", "获取订单信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取订单信息失败！");
        }
        return jsonObject;
    }
}
