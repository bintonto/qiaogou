package com.bintonto.dao;

import com.bintonto.entity.SuccessQiangGou;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessQiangGouDaoTest {

    @Autowired
    private SuccessQiangGouDao successQiangGouDao;

    @Test
    public void insertSuccessQiangGou() {
        long id = 1001L;
        long userphone = 15001057955L;
        int insertCount = successQiangGouDao.insertSuccessQiangGou(id, userphone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithQiangGou() {
        long id = 1001L;
        long phone = 15001057955L;
        SuccessQiangGou successQiangGou = successQiangGouDao.queryByIdWithQiangGou(id, phone);
        System.out.println(successQiangGou);
        System.out.println(successQiangGou.getQiangGou());
    }
}