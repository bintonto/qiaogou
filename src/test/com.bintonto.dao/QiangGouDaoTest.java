package com.bintonto.dao;

import com.bintonto.entity.QiangGou;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * junit启动时加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class QiangGouDaoTest {

    // 注入 seckillDao实现类
    @Autowired
    private QiangGouDao qiangGouDao;

    @Test
    public void reduceNumber() throws Exception  {
        long id = 1000L;
        Date killTime = new Date();
        int updateCount = qiangGouDao.reduceNumber(id, killTime);
        System.out.println("updateCount=" + updateCount);
    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        QiangGou qiangGou = qiangGouDao.queryById(id);
        System.out.println(qiangGou.getName());
        System.out.println(qiangGou);
    }

    @Test
    public void queryAll() {
        List<QiangGou> qiangGous = qiangGouDao.queryAll(0, 100);
        for (QiangGou qiangGou : qiangGous) {
            System.out.println(qiangGou);
        }
    }


}