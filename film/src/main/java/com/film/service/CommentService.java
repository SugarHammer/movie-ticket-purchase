package com.film.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Comment;
import com.film.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:08
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public IPage<Comment> selectAll(Page<Comment> page, String query) {
        return commentMapper.selectAllByPage(page, query);
    }

    public boolean addComment(Comment comment) {
        return commentMapper.insert(comment) > 0;
    }

    public boolean updateComment(Comment comment) {
        return commentMapper.updateById(comment) > 0;
    }

    public boolean deleteComment(Integer id) {
        return commentMapper.deleteById(id) > 0;
    }

    public Comment getUserComment(Integer userId, Integer movieId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .eq("movie_id", movieId);
        return commentMapper.selectOne(wrapper);
    }

    public List<Comment> selectAllInfo(Integer movieId){
        return commentMapper.selectAll(movieId);
    }

    public Comment selectOne(Integer id){
        return commentMapper.selectById(id);
    }

    public List<Comment> selectWatched(Integer userId){
        return commentMapper.selectWatchMovie(userId);
    }
}
