package com.film.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Sora
 * @date 2020/11/8 15:01
 */
@TableName("schedule")
public class Schedule {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer movieId;
    private Integer cinemaId;
    private String hallName;
    private String showDate;
    private String showTime;
    private Double price;
    private String seatInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", cinemaId=" + cinemaId +
                ", hallName='" + hallName + '\'' +
                ", showDate='" + showDate + '\'' +
                ", showTime='" + showTime + '\'' +
                ", price=" + price +
                ", seatInfo='" + seatInfo + '\'' +
                '}';
    }
}
