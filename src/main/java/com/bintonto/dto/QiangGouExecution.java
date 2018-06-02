package com.bintonto.dto;

import com.bintonto.entity.SuccessQiangGou;
import com.bintonto.enums.QiangGouStatEnum;

public class QiangGouExecution {

    private long qiangGouId;

    private int state;

    private String stateInfo;

    private SuccessQiangGou successQiangGou;

    public QiangGouExecution(long qiangGouId, QiangGouStatEnum statEnum, SuccessQiangGou successQiangGou) {
        this.qiangGouId = qiangGouId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successQiangGou = successQiangGou;
    }

    public QiangGouExecution(long qiangGouId, QiangGouStatEnum statEnum) {
        this.qiangGouId = qiangGouId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public long getQiangGouId() {
        return qiangGouId;
    }

    public void setQiangGouId(long qiangGouId) {
        this.qiangGouId = qiangGouId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessQiangGou getSuccessQiangGou() {
        return successQiangGou;
    }

    public void setSuccessQiangGou(SuccessQiangGou successQiangGou) {
        this.successQiangGou = successQiangGou;
    }

    @Override
    public String toString() {
        return "QiangGouExecution{" +
                "qiangGouId=" + qiangGouId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successQiangGou=" + successQiangGou +
                '}';
    }
}
