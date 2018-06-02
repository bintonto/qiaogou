package com.bintonto.service;

import com.bintonto.Exception.QiangGouCloseException;
import com.bintonto.Exception.QiangGouException;
import com.bintonto.Exception.RepeatQiangGouException;
import com.bintonto.dto.Exposer;
import com.bintonto.dto.QiangGouExecution;
import com.bintonto.entity.QiangGou;

import java.util.List;

public interface QiangGouService {
    /**
     * 查询所有抢购信息
     */
    List<QiangGou> getQiangGouList();

    /**
     * 查询单个抢购记录
     */
    QiangGou getById(long qiangGouId);

    /**
     *抢购开始时输出抢购地址，
     * 否则输出抢购开始时间和结束时间
     */
    Exposer exportQiangGouUrl(long qiangGouId);

    /**
     * by 声明式事务
     * 执行秒杀，成功返回抢购定单，失败返回失败的原因。
     */
    QiangGouExecution executeQiangGou(long qiangGouId, long userphone, String md5)
            throws QiangGouException, RepeatQiangGouException, QiangGouCloseException;




}