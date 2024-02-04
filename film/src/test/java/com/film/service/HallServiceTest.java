package com.film.service;

import com.film.entity.Hall;
import com.film.mapper.HallMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HallServiceTest {

    @Autowired
    public HallService hallService;

    @Test
    public void add(){
        Hall hall = new Hall();
        hall.setCinemaId(1);
        hall.setName("test");
        boolean res = hallService.addHall(hall);
        System.out.println(res);
        System.out.println(hall.getId());
    }

}