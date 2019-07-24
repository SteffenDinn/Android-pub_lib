package dinn.pub_lib.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * 设置最大行数TextView，以省略号结尾
 * Created by Steffen on 2017/12/21.
 */
public class MaxLineTextView extends android.support.v7.widget.AppCompatTextView {
    public MaxLineTextView(Context context) {
        super(context);
    }

    public MaxLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaxLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private class OnGlobalLayoutListenerMonitor implements ViewTreeObserver.OnGlobalLayoutListener{
        @Override
        public void onGlobalLayout() {
            //一般用完之后，立即移除该监听
            MaxLineTextView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            TextPaint paint = MaxLineTextView.this.getPaint();

            int paddingLeft = MaxLineTextView.this.getPaddingLeft();
            int paddingRight = MaxLineTextView.this.getPaddingRight();

            //给省略号留的长度（但是，因为字符占位问题，获取的这个长度，要比省略号的三个点的长度大一些）
            float moreText = MaxLineTextView.this.getTextSize() * 3;

            //乘2，是代表2行的意思，减去moreText，是给省略号预留一点位置
            float availableTextWidth = (MaxLineTextView.this.getWidth() - paddingLeft - paddingRight) * 2 - moreText;
            /**
             * TextUtils中public static CharSequence ellipsize(CharSequence text,TextPaint p,float avail, TruncateAt where)说明
             *
             * Returns the original（原始） text if it fits（适合、符合） in the specified（指定） width
             * given the properties（性质） of the specified（指定） Paint,
             * or, if it does not fit, a truncated（缩短了的，被删截的）
             * copy with ellipsis（省略、省略符号） character added at the specified（指定） edge（边缘） or center.
             */
            CharSequence ellipsizeStr = TextUtils.ellipsize(MaxLineTextView.this.getText().toString(), paint, availableTextWidth, TextUtils.TruncateAt.END);

            MaxLineTextView.this.setText(ellipsizeStr);
        }
    }
}
