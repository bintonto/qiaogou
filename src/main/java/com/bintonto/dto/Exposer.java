package com.bintonto.dto;

public class Exposer {

    private boolean exposed;

    private String md5;

    private long qiangGouId;

    private long now;

    private long start;

    private long end;

    public Exposer(boolean exposed, long qiangGouId) {
        this.exposed = exposed;
        this.qiangGouId = qiangGouId;
    }

    public Exposer(boolean exposed, String md5, long qiangGouId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.qiangGouId = qiangGouId;
    }

    public Exposer(boolean exposed, long qiangGouId, long now, long start, long end) {
        this.exposed = exposed;
        this.qiangGouId = qiangGouId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getQiangGouId() {
        return qiangGouId;
    }

    public void setQiangGouId(long qiangGouId) {
        this.qiangGouId = qiangGouId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", qiangGouId=" + qiangGouId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
