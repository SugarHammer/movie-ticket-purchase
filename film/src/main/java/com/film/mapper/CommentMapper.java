package com.film.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:04
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    Page<Comment> selectAllByPage(Page<Comment> page,String query);

    List<Comment> selectAll(Integer movieId);

    List<Comment> selectWatchMovie(Integer userId);
}
