package com.mycp.jclft.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mycp.jclft.MyApplication;

import java.lang.reflect.Method;

/**
 * Created by 17073822 on 2017/7/25.
 * <p>
 * You may think you know what the following code does.
 * But you don't. Trust me.
 * Fiddle with it, and you'll spend many a sleepless
 * night cursing the moment you thought you'd be clever
 * enough to "optimize" the code below.
 * Now close this file and go play with something else.
 */

public class DimenUtils {
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;

    public static int dip2Px(int dip) {
        return (int) (dip * getDensity() + 0.5f);
    }

    public static int sp2px(float spValue) {
        float fontScale = MyApplication.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static float getDensity() {
        return MyApplication.mContext.getResources().getDisplayMetrics().density;

    }

    public static int getScreenWidth() {
        if (SCREEN_WIDTH == 0) {
            WindowManager systemService = (WindowManager) MyApplication.mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            systemService.getDefaultDisplay().getMetrics(dm);
            SCREEN_WIDTH = dm.widthPixels;
        }
        return SCREEN_WIDTH;
    }

    /**
     * 获取屏幕的高
     *
     * @return 屏幕的高
     */
    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == 0) {
            WindowManager systemService = (WindowManager) MyApplication.mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            systemService.getDefaultDisplay().getMetrics(dm);
            SCREEN_HEIGHT = dm.heightPixels;
        }
        return SCREEN_HEIGHT;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    public static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!isEMUI3_1()) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public static void cleanTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (!isEMUI3_1()) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.LTGRAY);
            }
        }
    }

    public static boolean isEMUI3_1() {
        return ("EmotionUI_3.1".equals(getEmuiVersion()));
    }

    /**
     * @return 只要返回不是""，则是EMUI版本
     */
    private static String getEmuiVersion() {
        String emuiVerion = "";
        Class<?>[] clsArray = new Class<?>[]{String.class};
        Object[] objArray = new Object[]{"ro.build.version.emui"};
        try {
            Class<?> SystemPropertiesClass = Class
                    .forName("android.os.SystemProperties");
            Method get = SystemPropertiesClass.getDeclaredMethod("get",
                    clsArray);
            String version = (String) get.invoke(SystemPropertiesClass,
                    objArray);
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (Exception e) {
        }
        return emuiVerion;
    }

}
