package dinn.pub_lib.utils.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPage（可控制是否允许左右滑动）
 * Created by SteffenDinn on 2018/11/17.
 * Copyright (c) WANZHONG INTERNET GROUP. All rights reserved.
 */
public class CustomScrollViewPager extends ViewPager {
    //是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    // false 禁止ViewPager左右滑动。
    private boolean scrollable = false;

    public CustomScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否允许左右滑动
     * @param scrollable true：允许
     */
    public void setLeftAndRightScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    /**
     * @return 是否否允许左右滑动
     */
    public boolean isLeftAndRightScrollable() {
        return scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable;
    }

}
