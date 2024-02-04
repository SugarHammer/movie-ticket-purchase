package com.film.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Sora
 * @date 2020/11/8 14:47
 */
@TableName("hall")
public class Hall {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer cinemaId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", cinemaId=" + cinemaId +
                ", name='" + name + '\'' +
                '}';
    }
}
