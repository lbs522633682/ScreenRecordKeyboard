package com.skymobi.game.bean;


import com.skymobi.game.helper.JsonUtil;

import java.util.List;

public class ClickBean {

    private List<ClickEvent> accountLoginEvents; // 账号密码登录 脚本
    private List<ClickEvent> registerEvents; // 账号密码注册 登录 脚本
    private List<ClickEvent> loginEvents; // 验证码登录 脚本
    private List<ClickEvent> inputVerifyEvents; // 输入验证码的脚本
    private List<ClickEvent> playGameEvents; // 玩游戏的脚本
    private int loopTimes; // 游戏脚本循环次数

    private String verifyCode = ""; // 验证码

    private String gameName; // 游戏名称

    private String gameFirstUpper; // 游戏首字母 大写

    private String account; // 账号
    private String pwd; // 密码

    /**
     * taskStatus 的json
     */
    private String runtimeStatus; // 用于记录运行时的状态

    private String provinceName; // 记录刷的省份

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRuntimeStatus() {
        return runtimeStatus;
    }

    public void setRuntimeStatus(String runtimeStatus) {
        this.runtimeStatus = runtimeStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<ClickEvent> getAccountLoginEvents() {
        return accountLoginEvents;
    }

    public void setAccountLoginEvents(List<ClickEvent> accountLoginEvents) {
        this.accountLoginEvents = accountLoginEvents;
    }

    public int getLoopTimes() {
        return loopTimes;
    }

    public void setLoopTimes(int loopTimes) {
        this.loopTimes = loopTimes;
    }

    public List<ClickEvent> getLoginEvents() {
        return loginEvents;
    }

    public void setLoginEvents(List<ClickEvent> loginEvents) {
        this.loginEvents = loginEvents;
    }

    public List<ClickEvent> getInputVerifyEvents() {
        return inputVerifyEvents;
    }

    public void setInputVerifyEvents(List<ClickEvent> inputVerifyEvents) {
        this.inputVerifyEvents = inputVerifyEvents;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameFirstUpper() {
        return gameFirstUpper;
    }

    public void setGameFirstUpper(String gameFirstUpper) {
        this.gameFirstUpper = gameFirstUpper;
    }

    public List<ClickEvent> getPlayGameEvents() {
        return playGameEvents;
    }

    public void setPlayGameEvents(List<ClickEvent> playGameEvents) {
        this.playGameEvents = playGameEvents;
    }

    public List<ClickEvent> getRegisterEvents() {
        return registerEvents;
    }

    public void setRegisterEvents(List<ClickEvent> registerEvents) {
        this.registerEvents = registerEvents;
    }

    @Override
    public String toString() {
        return JsonUtil.toJSON(this);
    }

}
