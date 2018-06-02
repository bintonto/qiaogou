package com.bintonto.service.impl;

import com.bintonto.Exception.QiangGouCloseException;
import com.bintonto.Exception.RepeatQiangGouException;
import com.bintonto.dto.Exposer;
import com.bintonto.dto.QiangGouExecution;
import com.bintonto.entity.QiangGou;
import com.bintonto.service.QiangGouService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class QiangGouServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QiangGouService qiangGouService;

    @Test
    public void getQiangGouList() throws Exception {
        List<QiangGou> list = qiangGouService.getQiangGouList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() {
        long id = 1000L;
        QiangGou qiangGou = qiangGouService.getById(id);
        logger.info("qiangGou={}", qiangGou);
    }

    @Test
    public void qiangGouLogic() throws Exception {
        long id = 1001L;
        Exposer exposer = qiangGouService.exportQiangGouUrl(id);

        if (exposer.isExposed()) {
            long userPhone = 15001057953L;
            String md5 = exposer.getMd5();
            try {
                QiangGouExecution qiangGouExecution = qiangGouService.executeQiangGou(id, userPhone, md5);
                logger.info("qiangGouExecution={}", qiangGouExecution);
            } catch (RepeatQiangGouException e) {
                logger.error(e.getMessage());
            } catch (QiangGouCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
    }

//    @Test
//    public void exportQiangGouUrl() throws Exception {
//        long id = 1001L;
//        Exposer exposer = qiangGouService.exportQiangGouUrl(id);
//        logger.info("exposer={}", exposer);
//    }
//
//    @Test
//    public void executeQiangGou() throws Exception {
//        long id = 1004L;
//        long userPhone = 15001057953L;
//        String md5 = "78567ee3d49cdb6fb08ed3ef219a1c6c";
//        try {
//            QiangGouExecution qiangGouExecution = qiangGouService.executeQiangGou(id, userPhone, md5);
//            logger.info("qiangGouExecution={}", qiangGouExecution);
//        } catch (RepeatQiangGouException e) {
//            logger.error(e.getMessage());
//        } catch (QiangGouCloseException e) {
//            logger.error(e.getMessage());
//        }
//    }
}