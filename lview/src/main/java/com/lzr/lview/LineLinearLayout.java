package com.lzr.lview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LineLinearLayout extends LinearLayout {
    private boolean drawLine;
    private Paint sPaint;

    public LineLinearLayout(Context context) {
        this(context, null);
    }

    public LineLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sPaint = new Paint();
        sPaint.setAntiAlias(true);
        sPaint.setColor(getResources().getColor(R.color.login_bottom_line));
        sPaint.setStrokeWidth(5);
        setWillNotDraw(false);
    }

    public void drawLine(boolean draw) {
        drawLine = draw;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawLine) {
            int top = getMeasuredHeight() + getPaddingBottom() + getPaddingTop()-5;
            canvas.drawLine(0, top, getRight(), top, sPaint);
        }
    }
}
