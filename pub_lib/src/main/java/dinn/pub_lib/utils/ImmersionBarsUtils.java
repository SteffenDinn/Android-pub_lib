package dinn.pub_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

/**
 * 沉浸式工具类
 * Created by Steffen on 2018/9/26.
 * Copyright (c) WANZHONG INTERNET GROUP. All rights reserved.
 *
 * <p/>常用方法:
 * ImmersionBar.with(activity)
 * .transparentStatusBar()  //透明状态栏，不写默认透明色
 * .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
 * .transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
 * .statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色
 * .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
 * .barColor(R.color.colorPrimary)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
 * .statusBarAlpha(0.3f)  //状态栏透明度，不写默认0.0f
 * .navigationBarAlpha(0.4f)  //导航栏透明度，不写默认0.0F
 * .barAlpha(0.3f)  //状态栏和导航栏透明度，不写默认0.0f
 * .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
 * .flymeOSStatusBarFontColor(R.color.btn3)  //修改flyme OS状态栏字体颜色
 * .fullScreen(true)      //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
 * .hideBar(BarHide.FLAG_HIDE_BAR)  //隐藏状态栏或导航栏或两者，不写默认不隐藏
 * .addViewSupportTransformColor(toolbar)  //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
 * .titleBar(view)    //解决状态栏和布局重叠问题，任选其一
 * .titleBarMarginTop(view)     //解决状态栏和布局重叠问题，任选其一
 * .statusBarView(view)  //解决状态栏和布局重叠问题，任选其一
 * .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
 * .supportActionBar(true) //支持ActionBar使用
 * .statusBarColorTransform(R.color.orange)  //状态栏变色后的颜色
 * .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
 * .barColorTransform(R.color.orange)  //状态栏和导航栏变色后的颜色
 * .removeSupportView(toolbar)  //移除指定view支持
 * .removeSupportAllView() //移除全部view支持
 * .navigationBarEnable(true)   //是否可以修改导航栏颜色，默认为true
 * .navigationBarWithKitkatEnable(true)  //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
 * .fixMarginAtBottom(true)   //已过时，当xml里使用android:fitsSystemWindows="true"属性时,解决4.4和emui3.1手机底部有时会出现多余空白的问题，默认为false，非必须
 * .addTag("tag")  //给以上设置的参数打标记
 * .getTag("tag")  //根据tag获得沉浸式参数
 * .reset()  //重置所以沉浸式参数
 * .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
 * .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
 * .setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调
 *     @Override
 *     public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
 *         LogUtils.e(isPopup);  //isPopup为true，软键盘弹出，为false，软键盘关闭
 *     }
 * })
 * .init();  //必须调用方可沉浸式
 */
public class ImmersionBarsUtils {
    private static volatile ImmersionBarsUtils instance;

    private ImmersionBarsUtils(){}
    public static ImmersionBarsUtils getInstance() {
        if (instance == null) {
            synchronized (ImmersionBarsUtils.class) {
                if (instance == null) {
                    instance = new ImmersionBarsUtils();
                }
            }
        }
        return instance;
    }

    public void init(Activity activity) {
        ImmersionBar.with(activity)
                .fitsSystemWindows(true)
                .statusBarColor(android.R.color.white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init();


    }

    public void init(Fragment fragment) {
        ImmersionBar.with(fragment)
                .fitsSystemWindows(true)
                .statusBarColor(android.R.color.white)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .init();
    }

    public void initTop(Activity activity, boolean isDarkFont) {
        ImmersionBar.with(activity)
                .statusBarDarkFont(isDarkFont)
                .init();
    }

    public void initTop(Activity activity, int resColor) {
        ImmersionBar.with(activity)
                .reset()
                .barColor(resColor)
                .init();
    }

    public void initTop(Fragment fragment, boolean isDarkFont) {
        ImmersionBar.with(fragment)
                .reset()
                .statusBarDarkFont(isDarkFont)
                .init();
    }

    /**
     * 设置状态栏高度（默认高度）
     *
     * @param vStatusBar
     */
    public void setStatusBarHeight(Context context, View vStatusBar) {
        if (vStatusBar != null) {
            ViewGroup.LayoutParams lp = vStatusBar.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = getActionBarHeight(context);
            vStatusBar.setLayoutParams(lp);
        }
    }

    /**
     * 设置标题栏高度（默认高度）
     *
     * @param vTitleBar
     */
    public void setTitleBarHeight(Context context, View vTitleBar) {
        if (vTitleBar != null) {
            ViewGroup.LayoutParams lp = vTitleBar.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = getTitleBarHeight(context);
            vTitleBar.setLayoutParams(lp);
        }
    }

    /**
     * 设置View的宽高
     *
     * @param view
     * @param width  0：WRAP_CONTENT, -1:MATCH_PARENT
     * @param height 0：WRAP_CONTENT, -1:MATCH_PARENT
     */
    public void setViewSize(View view, int width, int height) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (width == 0) width = ViewGroup.LayoutParams.WRAP_CONTENT;
            else if (width < 0) width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.width = width;
            if (height == 0) height = ViewGroup.LayoutParams.WRAP_CONTENT;
            else if (height < 0) height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = height;
            view.setLayoutParams(lp);
        }
    }

    /**
     * 设置高度（状态栏+标题栏）
     *
     * @param vTitleBar
     */
    public void setTitleBarHeight2(Context context, View vTitleBar) {
        if (vTitleBar != null) {
            ViewGroup.LayoutParams lp = vTitleBar.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = getActionBarHeight(context) + getTitleBarHeight(context);
            vTitleBar.setLayoutParams(lp);
        }
    }

    public int getActionBarHeight(Context context) {
        int result = 20;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getTitleBarHeight(Context context) {
        int actionBarHeight1 = 44;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            int[] attribute = new int[]{android.R.attr.actionBarSize};
            TypedArray array = context.obtainStyledAttributes(tv.resourceId, attribute);
            actionBarHeight1 = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
            array.recycle();
        }
        return actionBarHeight1;
    }


}
