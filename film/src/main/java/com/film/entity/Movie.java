package com.film.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Sora
 * @date 2020/11/8 14:48
 */
@TableName("movie")
public class Movie {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String poster;
    private String director;
    private String actor;
    private String movieLong;
    private String language;
    private String intro;
    private String type;
    private String publicDate;
    private Integer wishNum;
    private Double score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMovieLong() {
        return movieLong;
    }

    public void setMovieLong(String movieLong) {
        this.movieLong = movieLong;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public Integer getWishNum() {
        return wishNum;
    }

    public void setWishNum(Integer wishNum) {
        this.wishNum = wishNum;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", poster='" + poster + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", movieLong='" + movieLong + '\'' +
                ", language='" + language + '\'' +
                ", intro='" + intro + '\'' +
                ", type='" + type + '\'' +
                ", publicDate='" + publicDate + '\'' +
                ", wishNum=" + wishNum +
                ", score=" + score +
                '}';
    }
}
