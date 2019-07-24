package dinn.pub_lib.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * 自定义TextView，实现两端对齐
 * Created by Steffen on 2018/9/26.
 */
public class JustifyTextView extends android.support.v7.widget.AppCompatTextView {

    private int mLineY;
    private int mViewWidth;
    private TextPaint paint;

    public JustifyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        // 格式化Text，在Text中的数字、字母前后加空格
        super.setText(setAddSpace(text.toString()), type);
    }

    /**
     * 格式化字符串，在字符串中的数字、字母前面加空格，方便换行
     * @param str 字符串
     * @return
     */
    private String setAddSpace(String str){
        ArrayList<Integer> arrList = new ArrayList<>();
        char[] arrChar = str.toCharArray();
        int temp = -1;
        for(int i=arrChar.length-1; i>0; i--) {
            char tem = arrChar[i];
            if((tem >= 'A' && tem <= 'Z') || (tem >= 'a' && tem <= 'z') || (tem >= '0' && tem <= '9') || tem=='.') {
                if(temp  == -1) {
                    temp = i + 1;
                }
            } else if (tem == ' ') {//空格
                temp = -3;
            } else {
                if(temp != -1) {
                    if(temp != -3) {
//                        arrList.add(temp);
                        arrList.add(i+1);
                    }
                    temp = -1;
                }
            }
        }
        StringBuilder builder = new StringBuilder(str);
        for(int i=0;  i<arrList.size(); i++) {
            builder.insert(arrList.get(i), " ");
        }
        return builder.toString();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        mLineY = (int)getTextSize();
        Layout layout = getLayout();

        // layout.getLayout()在4.4.3出现NullPointerException
        if (layout == null) {
            super.onDraw(canvas);
            return;
        }

        Paint.FontMetrics fm = paint.getFontMetrics();

        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout.getSpacingAdd());
        //解决了最后一行文字间距过大的问题
        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            float width = StaticLayout.getDesiredWidth(text, lineStart,
                    lineEnd, getPaint());
            String line = text.substring(lineStart, lineEnd);

            if(i < layout.getLineCount() - 1) {
                if (needScale(line)) {
                    drawScaledText(canvas, lineStart, line, width);
                } else {
                    canvas.drawText(line, 0, mLineY, paint);
                }
            } else {
                canvas.drawText(line, 0, mLineY, paint);
            }
            mLineY += textHeight;
        }
    }

    private void drawScaledText(Canvas canvas, int lineStart, String line,
                                float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line)) {
            String blanks = "  ";
            canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;

            line = line.substring(3);
        }

        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288
                && line.charAt(1) == 12288) {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            canvas.drawText(substring, x, mLineY, getPaint());
            x += cw;
            i += 2;
        }

        float d = (mViewWidth - lineWidth) / gapCount;
        for (; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if (line == null || line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}