package com.bintonto.entity;

import java.util.Date;


public class SuccessQiangGou {

    private long qiangGouId;

    private long userPhone;

    private short state;

    private Date createTime;

    // 多对一，一个秒杀实体对应多个秒杀成功记录
    private QiangGou qiangGou;

    public long getQiangGouId() {
        return qiangGouId;
    }

    public void setQiangGouId(long qiangGouId) {
        this.qiangGouId = qiangGouId;
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
                "qiangGouId=" + qiangGouId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", qiangGou=" + qiangGou +
                '}';
    }
}