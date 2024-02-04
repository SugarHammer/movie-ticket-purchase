package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Comment;
import com.film.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:16
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/findComment")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String query = request.getParameter("query").trim();
        Page<Comment> page = new Page<>(pageNum, pageSize);
        IPage<Comment> iPage = commentService.selectAll(page, query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取评论信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取评论信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addComment")
    public Object addComment(@RequestBody Comment comment) {
        boolean res = commentService.addComment(comment);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加评论成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加评论失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateComment")
    public Object updateComment(@RequestBody Comment comment) {
        boolean res = commentService.updateComment(comment);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改评论信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改评论信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteComment")
    public Object deleteComment(@RequestParam Integer id) {
        boolean res = commentService.deleteComment(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除评论成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除评论失败！");
        }
        return jsonObject;
    }

    @GetMapping("/findUserComment")
    public Object getUserComment(HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getParameter("user_id"));
        Integer movieId = Integer.valueOf(request.getParameter("movie_id"));
        Comment comment = commentService.getUserComment(userId, movieId);
        JSONObject jsonObject = new JSONObject();
        if (comment != null) {
            jsonObject.put("code", 1);
            jsonObject.put("commentInfo", comment);
            jsonObject.put("msg", "获取评论成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "暂无评论信息！");
        }
        return jsonObject;
    }

    @GetMapping("/getCommentInfo")
    public Object getAllComment(HttpServletRequest request) {
        Integer movieId = Integer.valueOf(request.getParameter("movie_id"));
        List<Comment> list = commentService.selectAllInfo(movieId);
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("commentInfo", list);
            jsonObject.put("msg", "获取评论成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "暂无评论信息！");
        }
        return jsonObject;
    }

    @GetMapping("/getOneCommentInfo")
    public Object getUserComment(@RequestParam Integer id) {
        Comment comment = commentService.selectOne(id);
        JSONObject jsonObject = new JSONObject();
        if (comment != null) {
            jsonObject.put("code", 1);
            jsonObject.put("info", comment);
            jsonObject.put("msg", "获取评i论成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getWatchedMovie")
    public Object getAllComment(@RequestParam Integer userId) {
        List<Comment> list = commentService.selectWatched(userId);
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("info", list);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "暂无历史信息！");
        }
        return jsonObject;
    }
}
