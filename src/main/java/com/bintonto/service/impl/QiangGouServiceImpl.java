package com.bintonto.service.impl;

import com.bintonto.Exception.QiangGouCloseException;
import com.bintonto.Exception.QiangGouException;
import com.bintonto.Exception.RepeatQiangGouException;
import com.bintonto.dao.QiangGouDao;
import com.bintonto.dao.SuccessQiangGouDao;
import com.bintonto.dao.cache.RedisDao;
import com.bintonto.dto.Exposer;
import com.bintonto.dto.QiangGouExecution;
import com.bintonto.entity.QiangGou;
import com.bintonto.entity.SuccessQiangGou;
import com.bintonto.enums.QiangGouStatEnum;
import com.bintonto.service.QiangGouService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class QiangGouServiceImpl  implements QiangGouService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service依赖
    @Autowired
    private QiangGouDao qiangGouDao;

    @Autowired
    private SuccessQiangGouDao successQiangGouDao;

    @Autowired
    private RedisDao redisDao;

    private final String slat = "!#@$@!DFASF234dsf";

    //常规方法
    public List<QiangGou> getQiangGouList() {
        return qiangGouDao.queryAll(0, 4);
    }

    public QiangGou getById(long qiangGouId) {
        return qiangGouDao.queryById(qiangGouId);
    }

    /**
     * 输出秒杀地址
     */
    public Exposer exportQiangGouUrl(long qiangGouId) {
        // 优化点：缓存优化
        // 1: 访问redis
        QiangGou qiangGou = redisDao.getQiangGou(qiangGouId);
        if (qiangGou == null) {
            // 2: 访问数据库
            qiangGou = qiangGouDao.queryById(qiangGouId);
            if (qiangGou == null) {
                // 商品不存在
                return new Exposer(false, qiangGouId);
            } else {
                // 3: 放入redis
                redisDao.putQiangGou(qiangGou);
            }
        }

        Date startTime = qiangGou.getStartTime();
        Date endTime = qiangGou.getEndTime();
        //系统当前时间
        Date nowTime = new Date();

        System.out.println("startTime: "+startTime.getTime());
        System.out.println("endTime: "+endTime.getTime());
        System.out.println("nowTime: "+nowTime.getTime());

        // 秒杀未开始
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, qiangGouId,
                    nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        // 返回秒杀地址
        String md5 = getMD5(qiangGouId);

        return new Exposer(true, md5, qiangGouId);
    }

    private String getMD5(long qiangGouId) {
        String base = qiangGouId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格。
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3：不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     */

    @Transactional
    public QiangGouExecution executeQiangGou(long qiangGouId, long userphone, String md5)
            throws QiangGouException, RepeatQiangGouException, QiangGouCloseException {

        // md5错误或者商品ID错误
        if (md5 == null || !md5.equals(getMD5(qiangGouId))) {
            throw new QiangGouException("qiangGou data rewrite");
        }
        //执行秒杀逻辑
        Date nowTime = new Date();

        try {
            //记录购买行为
            int insertCount = successQiangGouDao.insertSuccessQiangGou(qiangGouId, userphone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatQiangGouException("qiangGou repeated");
            } else {
                // 减库存, 热点商品竞争
                int updateCount = qiangGouDao.reduceNumber(qiangGouId, nowTime);
                if (updateCount <= 0) {
                    // 没有更新到记录，秒杀结束, rollback
                    throw new QiangGouCloseException("qiangGou is close");
                } else {
                    //秒杀成功, commit
                    SuccessQiangGou successQiangGou = successQiangGouDao.queryByIdWithQiangGou(qiangGouId, userphone);
                    return new QiangGouExecution(qiangGouId, QiangGouStatEnum.SUCCESS, successQiangGou);
                }
            }
        } catch (QiangGouCloseException e1) {
            throw e1;
        } catch (RepeatQiangGouException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new QiangGouException("qiangGou inner error:"+e.getMessage());
        }
    }


}
