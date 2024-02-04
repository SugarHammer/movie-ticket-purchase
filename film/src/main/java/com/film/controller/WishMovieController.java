package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.film.entity.Movie;
import com.film.entity.WishMovie;
import com.film.service.MovieService;
import com.film.service.WishMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:17
 */
@RestController
public class WishMovieController {
    @Autowired
    private WishMovieService wishMovieService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/findWishMovie")
    public Object selectAll() {
        List<WishMovie> lists = wishMovieService.selectAll();
        JSONObject jsonObject = new JSONObject();
        if (!lists.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", lists);
            jsonObject.put("msg", "获取成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取失败！");
        }
        return jsonObject;
    }

    @PostMapping("/addWishMovie")
    public Object addLike(@RequestBody WishMovie wishMovie) {
        boolean res = wishMovieService.addWishMovie(wishMovie);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            Movie movie = movieService.selectOne(wishMovie.getMovieId());
            movie.setWishNum(movie.getWishNum() + 1);
            movieService.updateMovie(movie);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/cancelWishMovie")
    public Object deleteLike(@RequestParam Integer userId, Integer movieId) {
        boolean res = wishMovieService.deleteWishMovie(userId,movieId);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            Movie movie = movieService.selectOne(movieId);
            movie.setWishNum(movie.getWishNum() - 1);
            movieService.updateMovie(movie);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除失败！");
        }
        return jsonObject;
    }

    @GetMapping("/isWishMovie")
    public Object isWishMovie(HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getParameter("user_id"));
        Integer movieId = Integer.valueOf(request.getParameter("movie_id"));
        WishMovie one = wishMovieService.selectOne(userId,movieId);
        JSONObject jsonObject = new JSONObject();
        if (one != null) {
            jsonObject.put("code", 1);
            jsonObject.put("wishMovie", one);
            jsonObject.put("msg", "获取成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getUserLikeMovie")
    public Object getUserLike(@RequestParam Integer userId){
        List<WishMovie> list = wishMovieService.selectUserLike(userId);
        JSONObject jsonObject = new JSONObject();
        if (!list.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("movies", list);
            jsonObject.put("msg", "获取成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取失败！");
        }
        return jsonObject;
    }

}
