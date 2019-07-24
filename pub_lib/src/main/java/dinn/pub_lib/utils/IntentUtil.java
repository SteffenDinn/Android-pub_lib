package dinn.pub_lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Intent 跳转
 * Created by Steffen on 2017/11/16.
 */
public class IntentUtil {
    private IntentUtil() {
    }

    /**************************************************   跳转  ****************************************************/

    /**
     * 普通的方式打开一个Activiy
     *
     * @param context
     * @param gotoClass 需要打开的Activity
     */
    public static void gotoActivity(Context context, Class<?> gotoClass) {
        gotoActivity(context, gotoClass, null);
    }

    /**
     * * 打开一个Activiy, 可传参
     *
     * @param context
     * @param gotoClass 需要打开的Activity
     * @param b         携带的参数
     */
    public static void gotoActivity(Context context, Class<?> gotoClass, Bundle b) {
        Intent intent = new Intent();
        if (b != null) {
            intent.putExtras(b);
        }
        intent.setClass(context, gotoClass);
        context.startActivity(intent);
        enterAnim(context);
    }

    /**
     * 用Result的方式跳转到指定页面，不携带数据
     *
     * @param context
     * @param gotoClass   要跳转的Activity
     * @param requestCode 页面跳转请求码
     */
    public static void gotoActivityForResult(Context context,
                                             Class<?> gotoClass, int requestCode) {
        gotoActivityForResult(context, gotoClass, null, requestCode);
    }

    /**
     * 用Result的方式跳转到指定页面，携带数据
     *
     * @param context
     * @param gotoClass   要跳转的Activity
     * @param b           携带的参数
     * @param requestCode 页面跳转请求码
     */
    public static void gotoActivityForResult(Context context,
                                             Class<?> gotoClass, Bundle b, int requestCode) {
        Intent intent = new Intent();
        if (b != null) {
            intent.putExtras(b);
        }
        intent.setClass(context, gotoClass);
        ((Activity) context).startActivityForResult(intent, requestCode);
        enterAnim(context);
    }

    /**************************************************   返回  ****************************************************/

    /**
     * 返回上一页面
     *
     * @param activity
     */
    public static void backPreviousActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            exitAnim(activity);
        }
    }

    /**
     * 带参数的返回上一Activity
     *
     * @param activity
     * @param result
     */
    public static void backActivityForResult(Activity activity, int result) {
        backActivityForResult(activity, null, result);
    }

    /**
     * 带参数的返回上一Activity
     *
     * @param activity
     * @param b
     * @param result   <li/>返回：Bundle b = data.getExtras();
     */
    public static void backActivityForResult(Activity activity, Bundle b, int result) {
        Intent intent = new Intent();
        if (b != null) {
            intent.putExtras(b);
        }
        activity.setResult(result, intent);
        activity.finish();
        exitAnim(activity);
    }

    /**
     * 进入动画
     * @param context
     */
    public static void enterAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.in_right_to_left, R.anim.push_no_animation);
    }

    /**
     * 推出动画
     * @param context
     */
    public static void exitAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.push_no_animation, R.anim.out_left_to_right);
    }

    public static void call(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
