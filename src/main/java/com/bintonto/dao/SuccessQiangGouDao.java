package com.bintonto.dao;

import com.bintonto.entity.SuccessQiangGou;
import org.apache.ibatis.annotations.Param;

public interface SuccessQiangGouDao {


    /**
     * 插入秒杀成功明细, 可过滤重复
     * @return 返回1 表示插入的行数， 0 表示失败
     */
    int insertSuccessQiangGou(@Param("qiangGouId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀对象实体
     */
    SuccessQiangGou queryByIdWithQiangGou(@Param("qiangGouId") long seckillId, @Param("userPhone") long userPhone);
}