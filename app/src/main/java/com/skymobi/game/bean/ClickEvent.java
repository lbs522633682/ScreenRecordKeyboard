package com.skymobi.game.bean;

/**
 * Author:boshuai.li
 * Time:2021/05/06   20:06
 * Description: 每次的点击事件
 */
public class ClickEvent {
    /**
     * 需要执行的按键
     */
    private int keyCode = -1;
    /**
     * 需要执行的次数
     */
    private int times = -1;

    /**
     * 执行时需要延迟的时间
     * 单位：毫秒
     */
    private long delayTime;

    public ClickEvent() {
    }


    public ClickEvent(int keyCode, int times) {
        this.keyCode = keyCode;
        this.times = times;
    }

    public ClickEvent(int keyCode, int times, long delayTime) {
        this.keyCode = keyCode;
        this.times = times;
        this.delayTime = delayTime;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "ClickEvent{" +
                "keyCode=" + keyCode +
                ", times=" + times +
                ", delayTime=" + delayTime +
                '}';
    }
}
