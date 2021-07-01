package com.skymobi.game.helper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.skymobi.game.LogUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Author:boshuai.li
 * Time:2021/5/11
 * Description:
 */
public class PackageHelper {

    /*TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    String deviceId = tm.getDeviceId();

        LogUtils.i("hook deviceId111 = " + deviceId);

    //installApp("/sdcard/download/aaaa.apk");

    //removeAppData("com.cmgame.gamehalltv");

    //LogUtils.i("clear success");*/

    public static boolean installApp(String apkPath) {
        LogUtils.i("installApp enter apkPath = " + apkPath);
        DataOutputStream os = null;
        DataInputStream is = null;
        DataInputStream es = null;
        BufferedReader buf = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            is = new DataInputStream(p.getInputStream());
            os.writeBytes("pm install " + apkPath + " \n");
            os.flush();
            os.writeBytes("echo test  \n"); // 回显test 并获得test，确保上面的代码已经执行
            os.flush();
            String result = is.readLine();
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return true;
    }

    public static boolean removeAppData(String packageName) {
        File file = new File("/data/data/" + packageName);
        System.out.println("包名为：" + packageName);
        LogUtils.i("removeAppData enter packageName = " + packageName);
        DataOutputStream os = null;
        DataInputStream is = null;
        DataInputStream es = null;
        BufferedReader buf = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("su");
            //p = Runtime.getRuntime().exec("pm clear " + packageName + " \n");
            os = new DataOutputStream(p.getOutputStream());
            is = new DataInputStream(p.getInputStream());
            os.writeBytes("pm clear " + packageName + " \n");
            os.flush();
            os.writeBytes("echo test  \n");// 回显test 并获得test，确保上面的代码已经执行
            os.flush();
            String result = is.readLine();
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return true;
    }

    /**
     * @param rootNode
     * @param resourceId
     * @return
     */
    private boolean inputTextToEdt(AccessibilityNodeInfo rootNode, String resourceId, String text) {
        List<AccessibilityNodeInfo> userNameList = rootNode.findAccessibilityNodeInfosByViewId(resourceId);
        /*if (userNameList != null && userNameList.size() > 0) {
            LogUtils.i("onAccessibilityEvent account input");
            ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(clip);
            userNameList.get(0).performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            userNameList.get(0).performAction(AccessibilityNodeInfo.ACTION_PASTE);

            return true;
        } else {
            LogUtils.i("onAccessibilityEvent nodeInfoList is null or size = 0");
        }*/
        return false;
    }

    /**
     * 根据resId 点击
     *
     * @param rootNode
     * @param resourceId
     */
    private boolean clickBtnByResId(AccessibilityNodeInfo rootNode, String resourceId) {
        List<AccessibilityNodeInfo> nodeInfoList = rootNode.findAccessibilityNodeInfosByViewId(resourceId);

        if (nodeInfoList != null && nodeInfoList.size() > 0) {
            LogUtils.i("onAccessibilityEvent click resourceId = " + resourceId);
            nodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_FOCUS);
            return nodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            LogUtils.i("onAccessibilityEvent nodeInfoList is null or size = 0");
        }

        return false;
    }

    private void performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            if (nodeInfo.getParent() != null && nodeInfo.getParent().isClickable()) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    //LogUtils.i("onAccessibilityEvent  = " + JsonUtil.toJSON(accessibilityEvent));

        /*if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            AccessibilityNodeInfo rootNode = getRootInActiveWindow(); //获取当前活动窗口的根节点


            //List<AccessibilityNodeInfo> nodeInfoList = rootNode.findAccessibilityNodeInfosByViewId("com.alipay.mobile.payee:id/payee_NextBtn"); //通过控件id来获取某个控件
            if (rootNode != null) {

                // 1. 同意首页协议
                if (!mIsAgreed) {
                    mIsAgreed = clickBtnByResId(rootNode, ResourceHelper.ID_AGREE_BTN);
                    LogUtils.i("onAccessibilityEvent mIsAgreed = " + mIsAgreed);
                }

                // 2. 点击首页登录
                if (!mIsClickedLogin) {
                    mIsClickedLogin = clickBtnByResId(rootNode, ResourceHelper.ID_LOGIN_BTN);
                    LogUtils.i("onAccessibilityEvent mIsClickedLogin = " + mIsClickedLogin);
                } else {
                    LogUtils.i("1. 同意了协议， 2.点击了 登录");
                    mIsAgreed = true;

                    // 3. 输入 手机号码
                    if (!mIsInputAccout) {
                        // TODO 请求服务端下发号码，输入
                        String phone = "15967153155";
                        mIsInputAccout = inputTextToEdt(rootNode, ResourceHelper.ID_USER_EDT, phone);
                    } else {
                        // TODO 4.点击获取验证码，等待服务端相应
                        if (!mIsInputVerifyCode) {
                            String verifyCode = "12345";
                            mIsInputVerifyCode = inputTextToEdt(rootNode, ResourceHelper.ID_VAL_CODE_EDT, verifyCode);
                        } else {
                            // 5. 点击登录按钮，进行登录
                            //clickBtnByResId(rootNode, ResourceHelper.ID_HOME_LOGIN);
                        }
                    }
                }
            }
        }*/
}
