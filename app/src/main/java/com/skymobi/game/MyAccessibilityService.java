package com.skymobi.game;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

import com.skymobi.game.bean.ClickBean;
import com.skymobi.game.bean.ClickEvent;
import com.skymobi.game.helper.FileUtils;
import com.skymobi.game.helper.JsonUtil;
import com.skymobi.game.helper.ToastUtil;
import com.skymobi.game.window.WindowHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:boshuai.li
 * Time:2021/4/25
 * Description:
 */
public class MyAccessibilityService extends AccessibilityService implements View.OnClickListener {

    public static final String PHONENUM = "15967153155";
    public static final String OUT_PATH = "/mnt/sdcard/MiguGameTaskInfo.txt";

    private Button start_record;
    private Button stop_record;

    private boolean mIsRecording = false;

    private static View view;

    private List<ClickEvent> playGameEvents;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtils.i("MyAccessibilityService onServiceConnected");

        WindowHelper.getInstance().initWindow(this);

        view = View.inflate(this, R.layout.activity_input, null);

        start_record = view.findViewById(R.id.start_record);
        stop_record = view.findViewById(R.id.stop_record);

        start_record.setOnClickListener(this);
        stop_record.setOnClickListener(this);

    }

    private static ClickBean clickBean;

    public static void showRecordWindow(String times, String gameName, String gameNameUpper, String provinceName) {
        LogUtils.i("MyAccessibilityService showRecordWindow times = " + times + ", gameName = " + gameName + ", gameNameUpper = " + gameNameUpper
                + ", provinceName = " + provinceName);

        clickBean = new ClickBean();

        clickBean.setProvinceName(provinceName);

        // 2. 验证码登录
        createVerifycodeLoginScript(clickBean);

        // 2. 生成 填写验证码的脚本 -- 服务端下发验证码 交给click插件完成

        // 3. 生成 搜索游戏的脚本 + 点击秒玩的脚本
        clickBean.setGameName(gameName);
        clickBean.setGameFirstUpper(gameNameUpper);

        clickBean.setLoopTimes(Integer.parseInt(times)); // 设置脚本 循环次数

        WindowHelper.getInstance().showView(view);
    }

    private long mLastRecordTime = 0; // 记录间隔时间

    private long mStartRecordTime = 0; // 计算录制总时长

    private long mDownTime = 0; // 按下的时间

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        // 4. 游戏内部的点击脚本 -- 由爱文来生成
        if (mIsRecording) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                mDownTime = System.currentTimeMillis();
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                long currTime = System.currentTimeMillis();

                // 如果 超过 200ms，则认为是长按
                if ((currTime - mDownTime) > 200) {
                    int addActionCount = (int) ((currTime - mDownTime) / 200); // 每隔 200ms 添加一次操作
                    LogUtils.i("MyAccessibilityService 当前是长按操作 addActionCount = " + addActionCount);
                    playGameEvents.add(new ClickEvent(event.getKeyCode(), addActionCount, currTime - mLastRecordTime));
                } else {
                    playGameEvents.add(new ClickEvent(event.getKeyCode(), 1, currTime - mLastRecordTime));
                }
                mLastRecordTime = currTime;
                LogUtils.i("MyAccessibilityService onKeyEvent event = " + event.getKeyCode());
            }
        } else {
            LogUtils.i("MyAccessibilityService onKeyEvent 未开始录制");
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_record) {
            if (mIsRecording) {
                return;
            }
            LogUtils.i("MyAccessibilityService 开始录制...");
            playGameEvents = new ArrayList<>();
            mLastRecordTime = System.currentTimeMillis();
            mStartRecordTime = mLastRecordTime;
            mIsRecording = true;
            start_record.setVisibility(View.GONE);
        } else if (v.getId() == R.id.stop_record) {
            int totalTime = (int) ((System.currentTimeMillis() - mStartRecordTime) / 1000);
            LogUtils.i("MyAccessibilityService 停止录制... 共有 " + playGameEvents.size() + "个操作 , 总耗时：" + totalTime + " 秒");
            ToastUtil.showTextToast(this, "停止录制... 共有 " + playGameEvents.size() + "个操作 , 总耗时：" + totalTime + " 秒");
            clickBean.setPlayGameEvents(playGameEvents);
            // 5. 将脚本写入文件  D:\MiguGameTaskInfo.txt

            String jsonBean = JsonUtil.toJSON(clickBean);

            LogUtils.i("MyAccessibilityService jsonBean = " + jsonBean);

            // TODO FileUtils.writeFileFromString(OUT_PATH, jsonBean, false);
            mIsRecording = false;

            WindowHelper.getInstance().hideView(view);

            onDestroy();
        }
    }

    /**
     * 接收到系统发送AccessibilityEvent时的回调
     *
     * @param accessibilityEvent
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        LogUtils.i("onAccessibilityEvent accessibilityEvent enter");
    }


    /**
     * 服务中断时的回调
     */
    @Override
    public void onInterrupt() {

    }

    /**
     * 生成 验证码登录的脚本
     *
     * @param clickBean
     */
    private static void createVerifycodeLoginScript(ClickBean clickBean) {
        ArrayList<ClickEvent> clickEvents = new ArrayList<>();
        // 1. 一次返回

        clickEvents.add(new ClickEvent(KeyEvent.KEYCODE_BACK, 1));
        // 2. 三次上
        clickEvents.add(new ClickEvent(KeyEvent.KEYCODE_DPAD_UP, 3));

        // 8. 手机号
        addNum(PHONENUM, clickEvents);

        // 6. 下

        clickEvents.add(new ClickEvent(KeyEvent.KEYCODE_DPAD_DOWN, 1));

        // 7. click 获取验证码

        // TODO clickEvents.add(new ClickEvent(KeyEvent.KEYCODE_DPAD_CENTER, 1));

        // 8. 左  到输入验证码的框
        clickEvents.add(new ClickEvent(KeyEvent.KEYCODE_DPAD_LEFT, 1));

        clickBean.setLoginEvents(clickEvents);
    }

    public static void addNum(String phoneNum, ArrayList<ClickEvent> clickEvents) {
        if (phoneNum == null || phoneNum.length() == 0) {
            return;
        }

        for (int i = 0; i < phoneNum.length(); i++) {
            char c = phoneNum.charAt(i);
            int anInt = Integer.parseInt(c + "");
            if (i == 0) { // 切换页面需要延迟
                clickEvents.add(new ClickEvent(anInt + 7, 1, 2 * 1000));
            } else {
                clickEvents.add(new ClickEvent(anInt + 7, 1));
            }
        }
    }
}
