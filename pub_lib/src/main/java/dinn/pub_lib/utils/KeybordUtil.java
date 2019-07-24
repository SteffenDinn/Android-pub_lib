package dinn.pub_lib.utils.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 * Created by SteffenDinn on 2018/9/4.
 * Copyright (c) WANZHONG INTERNET GROUP. All rights reserved.
 */
public class KeybordUtil {
    private KeybordUtil() {
    }

    /**
     * 打开软键盘
     *
     * @param mContext  上下文
     * @param mEditText 输入框
     */
    public static void openKeybord(Context mContext, EditText mEditText) {
        if (mContext == null) return;
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param mContext  上下文
     * @param mEditText 输入框
     */
    public static void closeKeybord(Context mContext, EditText mEditText) {
        if (mContext == null) return;
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private static InputMethodManager inputMethodManager;

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hidKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                if (activity.getCurrentFocus().getWindowToken() != null) {
                    final int flags = 2;
                    inputMethodManager.hideSoftInputFromWindow(
                            activity.getCurrentFocus()
                                    .getWindowToken(), flags);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     *
     * @param context 上下文
     */
    public static void showKeyBoard(Context context) {
        inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.toggleSoftInput(0,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {
        if (activity == null) return false;
        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputmanger != null) {
                return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
            }
        }
        return false;
    }
}
