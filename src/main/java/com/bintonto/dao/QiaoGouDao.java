package com.bintonto.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface QiaoGouDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 返回1 表示更新行数， 0 表示失败
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);


}
