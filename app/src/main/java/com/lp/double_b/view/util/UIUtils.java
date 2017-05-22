package com.lp.double_b.view.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.lp.double_b.view.activity.MyApplication;

/**
 * Created by Administrator on 2017/5/20.
 */
public class UIUtils {
    public static final String TAG = "UIUtils";

    /**
     * 得到应用程序的上下文
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }


    /**
     * 得到color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到主线程的handler
     */
    public static Handler getMainThreadHanlder() {
        return MyApplication.getHandler();
    }

    /**
     * dip-->px
     *
     * @param dip
     * @return
     */
    public static int dip2Px(int dip) {
        //1. px / (ppi/160) = dp;
        //2. px / dip = density;
        float density = getResources().getDisplayMetrics().density;
        float densityDpi = getResources().getDisplayMetrics().densityDpi;
        LogUtils.i(TAG, "density:" + density);
        LogUtils.i(TAG, "densityDpi:" + densityDpi);
        int px = (int) (dip * density + .5f);
        return px;
    }

    /**
     * @param px
     * @return
     */
    public static int px2Dip(int px) {
        //1. px / (ppi/160) = dp;
        //2. px / dip = density;
        float density = getResources().getDisplayMetrics().density;
        int dip = (int) (px / density + .5f);
        return dip;
    }

    /**
     * 该方法的作用:sp转换为px（文字大小单位）
     *
     * @param context
     * @param spValue
     * @return
     * @author l00220455
     * @date 2013-3-8
     */
    public static int sp2px(float spValue) {
        float scaledDensity = UIUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    /**
     * 该方法的作用:px转换为sp（文字大小单位）
     *
     * @param pxValue
     * @return
     * @author l00220455
     * @date 2013-3-8
     */
    public static int px2sp(float pxValue) {
        float scaledDensity = UIUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }
}

