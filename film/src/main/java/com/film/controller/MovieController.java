package com.film.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.film.entity.Movie;
import com.film.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Sora
 * @date 2021/1/20 19:17
 */
@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/findMovie")
    public Object selectAllByPage(HttpServletRequest request) {
        int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
        int pageNum = Integer.parseInt(request.getParameter("pageNum").trim());
        String query = request.getParameter("query").trim();
        Page<Movie> page = new Page<>(pageNum, pageSize);
        IPage<Movie> iPage = movieService.selectAll(page, query);
        JSONObject jsonObject = new JSONObject();
        if (iPage != null) {
            jsonObject.put("code", 1);
            jsonObject.put("lists", iPage);
            jsonObject.put("msg", "获取影片信息成功!");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影片信息失败!");
        }
        return jsonObject;
    }

    @PostMapping("/addMovie")
    public Object addMovie(@RequestBody Movie movie) {
        boolean res = movieService.addMovie(movie);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加影片成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加影片失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateMovie")
    public Object updateMovie(@RequestBody Movie movie) {
        boolean res = movieService.updateMovie(movie);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改影片信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改影片信息失败！");
        }
        return jsonObject;
    }

    @DeleteMapping("/deleteMovie")
    public Object deleteMovie(@RequestParam Integer id) {
        boolean res = movieService.deleteMovieById(id);
        JSONObject jsonObject = new JSONObject();
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除影片成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除影片失败！");
        }
        return jsonObject;
    }

    @PostMapping("/updateMoviePoster")
    public Object updateAvatar(@RequestParam("file") MultipartFile multipartFile, @RequestParam("id") Integer id) {
        JSONObject jsonObject = new JSONObject();
        if (multipartFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "poster";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String path = "/poster/" + fileName;
        try {
            multipartFile.transferTo(dest);
            Movie movie = new Movie();
            movie.setId(id);
            movie.setPoster(path);
            boolean res = movieService.updateMovie(movie);
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

    @GetMapping("/findOneMovie")
    public Object selectOneMovie(@RequestParam Integer id) {
        Movie movie = movieService.selectOne(id);
        JSONObject jsonObject = new JSONObject();
        if (movie != null) {
            jsonObject.put("code", 1);
            jsonObject.put("movie", movie);
            jsonObject.put("msg", "获取影片信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取影片信息失败！");
        }
        return jsonObject;
    }

    @GetMapping("/getMovieList")
    public Object selectAllMovie() {
        JSONObject jsonObject = new JSONObject();
        List<Movie> movies = movieService.selectAll();
        if (!movies.isEmpty()) {
            jsonObject.put("code", 1);
            jsonObject.put("movieList", movies);
            jsonObject.put("msg", "获取信息成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取信息失败！");
        }
        return jsonObject;
    }
}
