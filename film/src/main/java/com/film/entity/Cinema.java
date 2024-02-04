package com.film.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author Sora
 * @date 2020/11/8 14:36
 */
@TableName("cinema")
public class Cinema {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String county;
    private String specifiedAddress;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSpecifiedAddress() {
        return specifiedAddress;
    }

    public void setSpecifiedAddress(String specifiedAddress) {
        this.specifiedAddress = specifiedAddress;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", specifiedAddress='" + specifiedAddress + '\'' +
                '}';
    }
}
