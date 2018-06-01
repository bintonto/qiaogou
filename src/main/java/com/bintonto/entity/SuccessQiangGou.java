package com.bintonto.entity;

import java.util.Date;


public class SuccessQiangGou {

    private long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    // 多对一，一个秒杀实体对应多个秒杀成功记录
    private QiangGou qiangGou;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public QiangGou getQiangGou() {
        return qiangGou;
    }

    public void setQiangGou(QiangGou qiangGou) {
        this.qiangGou = qiangGou;
    }

    @Override
    public String toString() {
        return "SuccessQiangGou{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", qiangGou=" + qiangGou +
                '}';
    }
}