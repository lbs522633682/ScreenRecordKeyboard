package com.skymobi.game.bean;


import com.skymobi.game.helper.JsonUtil;

/**
 * Author:boshuai.li
 * Time:2021/05/06   20:06
 * Description: TastStatus 任务运行时状态
 */
public class TaskStatus {
    private boolean isVip; // 是否是vip
    private boolean isWaitVerifyCode; // 是否需要等待验证码
    private int remainTime;// 剩余可玩时间 单位：分钟

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public boolean isWaitVerifyCode() {
        return isWaitVerifyCode;
    }

    public void setWaitVerifyCode(boolean waitVerifyCode) {
        isWaitVerifyCode = waitVerifyCode;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    @Override
    public String toString() {
        return JsonUtil.toJSON(this);
    }
}
