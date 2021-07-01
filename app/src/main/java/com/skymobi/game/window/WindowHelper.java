package com.skymobi.game.window;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * Author:boshuai.li
 * Time:2021/4/14
 * Description: WindowManager 工具类
 */
public class WindowHelper {

    private static WindowHelper mInstance;
    private WindowManager mWindowManager;
    private Context mContext;
    private WindowManager.LayoutParams layoutParams;

    private WindowHelper() {

    }

    public static WindowHelper getInstance() {
        if (mInstance == null) {
            synchronized (WindowHelper.class) {
                if (mInstance == null) {
                    mInstance = new WindowHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化Window
     *
     * @param context
     */
    public void initWindow(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mContext = context;

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, //Must be at least 1x1
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                //Don't know if this is a safe default
                PixelFormat.TRANSLUCENT);

        //Don't set the preview visibility to GONE or INVISIBLE
        //mWindowManager.addView(mMonitorView, params);


        // 设置标志位
        /*layoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                        | PixelFormat.TRANSLUCENT*/
        ;


        // 设置格式 系统选择支持半透明的格式
        //layoutParams.format = PixelFormat.TRANSPARENT;
        // 设置位置
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
    }


    /**
     * 获取view
     *
     * @param layoutId
     */
    public View getView(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    /**
     * 显示窗口
     *
     * @param view
     */
    public void showView(View view) {
        if (view != null) {
            if (view.getParent() == null) { // 需要判断 这个view 有没有父布局
                mWindowManager.addView(view, layoutParams);
            }
        }
    }

    /**
     * 隐藏窗口
     *
     * @param view
     */
    public void hideView(View view) {
        if (view != null) {
            if (view.getParent() != null) { // 需要判断 这个view 有没有父布局
                mWindowManager.removeView(view);
            }
        }
    }
}
