package com.bintonto.enums;

public enum QiangGouStatEnum {
    SUCCESS(1, "抢购成功"),
    END(0, "抢购结束"),
    REPEAT_QiangGou(-1, "重复抢购"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;

    private String stateInfo;

    QiangGouStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static QiangGouStatEnum stateOf(int index) {
        for (QiangGouStatEnum state: values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
