package dinn.pub_lib.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * 控件背景颜色设置工具类
 * Created by SteffenDinn on 2019/3/22.
 */
public class GradientDrawableUtils {
    private GradientDrawableUtils() {
    }

    /**
     * 设置背景颜色（小于api21）
     *
     * @param view     View
     * @param color    颜色值0xffffffff
     * @param radiusDp 圆角
     */
    public static void setBackgroundColorLessLOLLIPOP(Context context, View view, int color, int radiusDp) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundColor(context, view, color, radiusDp);
        }
    }

    /**
     * 设置背景颜色（小于api21）
     *
     * @param context  上下文
     * @param view     View
     * @param resColor 资源文件中的颜色值R.color.white
     * @param radiusDp 圆角
     */
    public static void setBackgroundResColorLessLOLLIPOP(Context context, View view, int resColor, int radiusDp) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundResColor(context, view, resColor, radiusDp);
        }
    }

    /**
     * 设置背景颜色
     *
     * @param view     View
     * @param color    颜色值0xFF000000
     * @param radiusDp 圆角
     */
    public static void setBackgroundColor(Context context, View view, int color, int radiusDp) {
        GradientDrawable gd;
        if (view.getBackground() != null && view.getBackground() instanceof GradientDrawable) {
            gd = (GradientDrawable) view.getBackground();
        } else {
            gd = new GradientDrawable();
        }
        gd.setColor(color);
        if (radiusDp >= 0)
            gd.setCornerRadius(DimenUtil.dip2px(context, radiusDp));
        view.setBackground(gd);
    }

    /**
     * 设置背景颜色（小于api21）
     *
     * @param context  上下文
     * @param view     View
     * @param resColor 资源文件中的颜色值 R.color.white
     * @param radiusDp 圆角
     */
    public static void setBackgroundResColor(Context context, View view, int resColor, int radiusDp) {
        GradientDrawable gd;
        if (view.getBackground() != null && view.getBackground() instanceof GradientDrawable) {
            gd = (GradientDrawable) view.getBackground();
        } else {
            gd = new GradientDrawable();
        }
        gd.setColor(context.getResources().getColor(resColor));
        if (radiusDp >= 0)
            gd.setCornerRadius(DimenUtil.dip2px(context, radiusDp));
        view.setBackground(gd);
    }

    /**
     * 设置渐变背景颜色
     *
     * @param view        View
     * @param orientation 颜色渐变方向
     * @param colors      颜色数组 new int[]{0xFF000000, 0xFF000000, 0xFF000000}
     * @param radiusDp
     */
    public static void setBackgroundColors(Context context, View view, GradientDrawable.Orientation orientation, @ColorInt int[] colors, int radiusDp) {
        GradientDrawable gd = new GradientDrawable(orientation, colors);
        if (radiusDp >= 0)
            gd.setCornerRadius(DimenUtil.dip2px(context, radiusDp));
        view.setBackground(gd);
    }

    /**
     * 设置线框背景
     *
     * @param view     View
     * @param color    颜色值0xFF000000
     * @param radiusDp 圆角
     */
    public static void setBackgroundStrokeColor(Context context, View view, int color, int radiusDp) {
        GradientDrawable gd = new GradientDrawable();
        if (radiusDp >= 0)
            gd.setCornerRadius(DimenUtil.dip2px(context, radiusDp));
        gd.setStroke(DimenUtil.dip2px(context, 1), color);
        view.setBackground(gd);
    }

    /**
     * 设置线框背景
     *
     * @param view      View
     * @param bgColor   背景颜色值0xFF000000
     * @param lineColor 线框颜色值0xFF000000
     * @param radiusDp  圆角
     */
    public static void setBackgroundLineColor(Context context, View view, int bgColor, int lineColor, int radiusDp) {
        GradientDrawable gd = new GradientDrawable();
        if (radiusDp >= 0)
            gd.setCornerRadius(DimenUtil.dip2px(context, radiusDp));
        gd.setStroke(DimenUtil.dip2px(context, 1), lineColor);
        gd.setColor(bgColor);
        view.setBackground(gd);
    }

}
