package dinn.pub_lib.utils.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 首页横向RecyclerView
 *
 * @author Steffen.Dinn
 */
public class HorizontalRecyclerView extends RecyclerView {

    private float nfX = 0f;
    private float nfY = 0f;

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyclerView(Context context) {
        super(context);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nfX = x;
                nfY = y;
                break;
            case MotionEvent.ACTION_UP:
                nfX = 0;
                nfY = 0;
                getParent().requestDisallowInterceptTouchEvent(false); // 让父类不拦截触摸事件就可以了。
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - nfX) < Math.abs(y - nfY)) {
                    getParent().requestDisallowInterceptTouchEvent(false); // 让父类不拦截触摸事件就可以了。
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true); // 让父类不拦截触摸事件就可以了。
                }
        }
        return super.dispatchTouchEvent(e);
    }


}
