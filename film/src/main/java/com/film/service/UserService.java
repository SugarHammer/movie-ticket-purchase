package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.User;
import com.film.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:14
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> selectAll() {
        return userMapper.selectList(null);
    }

    public IPage<User> selectAll(Page<User> page, String query) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(query != null, "username", query);
        return userMapper.selectPage(page, queryWrapper);
    }

    public boolean addUser(User user) {
        return userMapper.insert(user) > 0;
    }

    public boolean updateUser(User user) {
//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id", user.getId());
//        return userMapper.update(user, updateWrapper) > 0;
        return userMapper.updateById(user) > 0;
    }

    public boolean deleteUserById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }

    public User selectOne(Integer id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return userMapper.selectOne(wrapper);
    }

    public User selectOneByPhone(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        return userMapper.selectOne(wrapper);
    }

    public User selectOne(String username){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

}
