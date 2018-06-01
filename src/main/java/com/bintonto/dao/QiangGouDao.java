package com.bintonto.dao;

import com.bintonto.entity.QiangGou;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface QiangGouDao {
    /**
     * 减库存
     * @return 返回1 表示更新行数， 0 表示失败
     */
    int reduceNumber(@Param("qiangGouId") long qiangGouId, @Param("killTime") Date killTime);

    /**
     * 查询单个秒杀对象
     */
    QiangGou queryById(long qiangGouId);

    /**
     * 根据偏移查询多个对象
     * 多个参数需要告诉mybatis
     */
    List<QiangGou> queryAll(@Param("offset") int offset, @Param("limit") int limit);



}
