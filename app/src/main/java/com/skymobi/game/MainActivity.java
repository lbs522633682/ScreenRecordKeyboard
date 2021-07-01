package com.skymobi.game;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.skymobi.game.helper.AccessibilityServiceHelper;
import com.skymobi.game.helper.ToastUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:boshuai.li
 * Time:2021/05/06   20:06
 * Description: MainActivity 屏幕录制工具入口
 */
public class MainActivity extends Activity {

    private EditText et_times;
    private EditText et_game_name;
    private EditText et_first;
    private EditText et_province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (!AccessibilityServiceHelper.isAccessibilitySettingsOn(this, MyAccessibilityService.class)) {
            AccessibilityServiceHelper.startAccessBilitySetting(this);
        }
    }



    private void initView() {
        et_times = findViewById(R.id.et_times);
        et_game_name = findViewById(R.id.et_game_name);
        et_first = findViewById(R.id.et_first);
        et_province = findViewById(R.id.et_province);

        Button btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String times = et_times.getText().toString().trim();
                String gameName = et_game_name.getText().toString().trim();
                String gameNameUpper = et_first.getText().toString().trim();
                String provinceName = et_province.getText().toString().trim();

                if (TextUtils.isEmpty(times)) {
                    ToastUtil.showTextToast(MainActivity.this, "请输入脚本重复次数");
                    return;
                }

                if (TextUtils.isEmpty(gameName)) {
                    ToastUtil.showTextToast(MainActivity.this, "请输入游戏名称-中文");
                    return;
                }

                if (TextUtils.isEmpty(gameNameUpper)) {
                    ToastUtil.showTextToast(MainActivity.this, "请输入游戏名首字母，大写");
                    return;
                }

                if (TextUtils.isEmpty(provinceName)) {
                    ToastUtil.showTextToast(MainActivity.this, "请输入要增量的省份...");
                    return;
                }

                ToastUtil.showTextToast(MainActivity.this, "设置完成，可以开始录制");

                MyAccessibilityService.showRecordWindow(times, gameName, gameNameUpper, provinceName);

                finish();
            }
        });


    }


}