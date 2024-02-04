package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.film.entity.Admin;
import com.film.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sora
 * @date 2020/11/8 15:10
 */
@Service
public class AdminService {
    @Autowired
    public AdminMapper adminMapper;

    public boolean login(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username)
                .eq("password", password);
        return adminMapper.selectList(wrapper) != null;
    }
}
