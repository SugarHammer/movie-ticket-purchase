package com.film.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Hall;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * @author Sora
 * @date 2021/1/20 19:04
 */
@Repository
public interface HallMapper extends BaseMapper<Hall> {

    Page<Hall> selectByPage(Page<Hall> page,String query);
}
