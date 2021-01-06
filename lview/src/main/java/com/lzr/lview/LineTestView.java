package com.lzr.lview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.itg.lib_log.L;

public class LineTestView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint sPaint;
    private Paint upaint;
    private Paint cPaint;

    public LineTestView(Context context) {
        this(context, null);
    }

    public LineTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        boolean check;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineTestView);
        check = a.getBoolean(R.styleable.LineTestView_checkColor, false);
        a.recycle();
        sPaint = new Paint();
        sPaint.setAntiAlias(true);
        sPaint.setColor(getResources().getColor(R.color.login_bottom_line));
        sPaint.setStrokeWidth(3);
        upaint = new Paint();
        upaint.setAntiAlias(true);
        upaint.setStrokeWidth(1);
        upaint.setColor(getResources().getColor(R.color.login_bottom_line_normal));

        setSelected(check);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        L.e(getText() + "  " + getLeft() + "  " + getBottom() + "  " + getRight() + "  " + getBottom());
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), cPaint);
    }


    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            cPaint = sPaint;
        } else {
            cPaint = upaint;
        }
        super.setSelected(selected);
    }
}
